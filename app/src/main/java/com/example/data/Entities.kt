package com.example.data

import androidx.room.*
import androidx.room.Transaction as RoomTransaction
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val barcode: String,
    val name: String,
    val category: String,
    val buyPrice: Double,
    val sellPrice: Double,
    val stock: Int,
    val minStock: Int,
    val photoUri: String? = null
)

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val totalAmount: Double,
    val paymentMethod: String
)

@Entity(tableName = "transaction_items", foreignKeys = [
    ForeignKey(entity = Transaction::class, parentColumns = ["id"], childColumns = ["transactionId"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Product::class, parentColumns = ["id"], childColumns = ["productId"], onDelete = ForeignKey.RESTRICT)
], indices = [Index("transactionId"), Index("productId")])
data class TransactionItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val transactionId: Int,
    val productId: Int,
    val qty: Int,
    val unitPrice: Double
)

@Dao
interface StokeDao {
    // Products
    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getAllProducts(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun getProductById(id: Int): Product?

    // Transactions
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT SUM(totalAmount) FROM transactions")
    fun getTotalRevenue(): Flow<Double?>
    
    @Query("SELECT COUNT(id) FROM transactions")
    fun getTotalTransactionCount(): Flow<Int>

    @Insert
    suspend fun insertTransaction(transaction: Transaction): Long

    @Insert
    suspend fun insertTransactionItems(items: List<TransactionItem>)
    
    @RoomTransaction
    suspend fun processSale(transaction: Transaction, items: List<TransactionItem>) {
        val txId = insertTransaction(transaction)
        val itemsWithTxId = items.map { it.copy(transactionId = txId.toInt()) }
        insertTransactionItems(itemsWithTxId)
        
        // Update stock
        items.forEach { item ->
            val product = getProductById(item.productId)
            if (product != null) {
                updateProduct(product.copy(stock = product.stock - item.qty))
            }
        }
    }
}
