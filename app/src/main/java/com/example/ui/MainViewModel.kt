package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.StokeRepository
import com.example.data.Product
import com.example.data.Transaction
import com.example.data.TransactionItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CartItem(val product: Product, val qty: Int)

data class StokeUiState(
    val products: List<Product> = emptyList(),
    val cart: List<CartItem> = emptyList(),
    val revenue: Double = 0.0,
    val transactionCount: Int = 0,
    val searchQuery: String = ""
)

class MainViewModel(private val repository: StokeRepository) : ViewModel() {

    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<StokeUiState> = combine(
        repository.allProducts,
        _cart,
        repository.totalRevenue.map { it ?: 0.0 },
        repository.transactionCount,
        _searchQuery
    ) { products, cart, rev, count, query ->
        StokeUiState(
            products = if (query.isBlank()) products else products.filter {
                it.name.contains(query, ignoreCase = true) || it.barcode.contains(query, ignoreCase = true)
            },
            cart = cart,
            revenue = rev,
            transactionCount = count,
            searchQuery = query
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), StokeUiState())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            repository.insertProduct(product)
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }

    fun addToCart(product: Product) {
        val currentCart = _cart.value.toMutableList()
        val existingIndex = currentCart.indexOfFirst { it.product.id == product.id }
        if (existingIndex != -1) {
            val item = currentCart[existingIndex]
            if (item.qty < product.stock) {
                currentCart[existingIndex] = item.copy(qty = item.qty + 1)
            }
        } else {
            if (product.stock > 0) {
                currentCart.add(CartItem(product, 1))
            }
        }
        _cart.value = currentCart
    }

    fun removeFromCart(product: Product) {
        val currentCart = _cart.value.toMutableList()
        val existingIndex = currentCart.indexOfFirst { it.product.id == product.id }
        if (existingIndex != -1) {
            val item = currentCart[existingIndex]
            if (item.qty > 1) {
                currentCart[existingIndex] = item.copy(qty = item.qty - 1)
            } else {
                currentCart.removeAt(existingIndex)
            }
        }
        _cart.value = currentCart
    }
    
    fun clearCart() {
        _cart.value = emptyList()
    }

    fun processCheckout(paymentMethod: String) {
        val currentCart = _cart.value
        if (currentCart.isEmpty()) return

        viewModelScope.launch {
            val total = currentCart.sumOf { it.product.sellPrice * it.qty }
            val tx = Transaction(totalAmount = total, paymentMethod = paymentMethod)
            val items = currentCart.map {
                TransactionItem(
                    transactionId = 0, // Assigned in DAO
                    productId = it.product.id,
                    qty = it.qty,
                    unitPrice = it.product.sellPrice
                )
            }
            repository.processSale(tx, items)
            _cart.value = emptyList()
        }
    }
}

class MainViewModelFactory(private val repository: StokeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
