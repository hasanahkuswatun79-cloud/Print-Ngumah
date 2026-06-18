package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PosScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    
    // We split screen into Cart (top/bottom) and Products
    Column(modifier = Modifier.fillMaxSize()) {
        // Search
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            label = { Text("Cari Barang") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            singleLine = true
        )
        
        // Products List
        LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
            items(uiState.products) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clickable { viewModel.addToCart(product) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(product.name, fontWeight = FontWeight.Bold)
                            Text("Stok: ${product.stock}", style = MaterialTheme.typography.bodySmall)
                        }
                        Text("Rp${product.sellPrice}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        
        // Cart Summary
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                val totalQty = uiState.cart.sumOf { it.qty }
                val totalAmount = uiState.cart.sumOf { it.qty * it.product.sellPrice }
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total Item: $totalQty", fontWeight = FontWeight.Bold)
                    Text("Total: Rp$totalAmount", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { viewModel.processCheckout("Tunai") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.cart.isNotEmpty()
                ) {
                    Text("Bayar Sekarang (Tunai)")
                }
            }
        }
    }
}
