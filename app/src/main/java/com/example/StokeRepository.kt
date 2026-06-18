package com.example

import com.example.data.*
import kotlinx.coroutines.flow.Flow

class StokeRepository(private val stokeDao: StokeDao) {
    val allProducts: Flow<List<Product>> = stokeDao.getAllProducts()
    val allTransactions: Flow<List<Transaction>> = stokeDao.getAllTransactions()
    val totalRevenue: Flow<Double?> = stokeDao.getTotalRevenue()
    val transactionCount: Flow<Int> = stokeDao.getTotalTransactionCount()

    suspend fun insertProduct(product: Product) {
        stokeDao.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        stokeDao.updateProduct(product)
    }
    
    suspend fun deleteProduct(product: Product) {
        stokeDao.deleteProduct(product)
    }

    suspend fun processSale(transaction: Transaction, items: List<TransactionItem>) {
        stokeDao.processSale(transaction, items)
    }
}
