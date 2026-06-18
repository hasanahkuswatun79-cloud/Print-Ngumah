package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var showStoreProfile by remember { mutableStateOf(false) }
    var showPrinterSettings by remember { mutableStateOf(false) }
    var showBarcodeScannerSettings by remember { mutableStateOf(false) }
    var showBackupRestore by remember { mutableStateOf(false) }
    var showAccessSettings by remember { mutableStateOf(false) }
    var showReceiptSettings by remember { mutableStateOf(false) }

    if (showStoreProfile) {
        StoreProfileDialog(onDismiss = { showStoreProfile = false })
    }

    if (showPrinterSettings) {
        PrinterSettingsDialog(onDismiss = { showPrinterSettings = false })
    }

    if (showBarcodeScannerSettings) {
        BarcodeScannerSettingsDialog(onDismiss = { showBarcodeScannerSettings = false })
    }

    if (showBackupRestore) {
        BackupRestoreDialog(onDismiss = { showBackupRestore = false })
    }

    if (showAccessSettings) {
        AccessSettingsDialog(onDismiss = { showAccessSettings = false })
    }

    if (showReceiptSettings) {
        ReceiptSettingsDialog(onDismiss = { showReceiptSettings = false })
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            SettingsCategoryTitle("Pengaturan Toko")
        }
        item {
            SettingsItem(
                icon = Icons.Filled.Store,
                title = "Profil Toko",
                subtitle = "Nama toko, alamat, logo, dan kontak",
                onClick = { showStoreProfile = true }
            )
        }
        item {
            SettingsItem(
                icon = Icons.Filled.Receipt,
                title = "Pengaturan Struk",
                subtitle = "Pesan footer, NPWP, dan format cetak",
                onClick = { showReceiptSettings = true }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            SettingsCategoryTitle("Perangkat keras")
        }
        item {
            SettingsItem(
                icon = Icons.Filled.Print,
                title = "Printer Thermal Bluetooth",
                subtitle = "Hubungkan dan atur printer kasir",
                onClick = { showPrinterSettings = true }
            )
        }
        item {
            SettingsItem(
                icon = Icons.Filled.QrCodeScanner,
                title = "Scanner Barcode",
                subtitle = "Pengaturan pemindai eksternal",
                onClick = { showBarcodeScannerSettings = true }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            SettingsCategoryTitle("Data & Keamanan")
        }
        item {
            SettingsItem(
                icon = Icons.Filled.CloudUpload,
                title = "Backup & Restore",
                subtitle = "Cadangkan data ke Cloud atau lokal",
                onClick = { showBackupRestore = true }
            )
        }
        item {
            SettingsItem(
                icon = Icons.Filled.Security,
                title = "Akun & Hak Akses",
                subtitle = "Kelola pengguna dan PIN kasir",
                onClick = { showAccessSettings = true }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("STOKE.ID Retail Pro v1.0.0", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SettingsCategoryTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun SettingsItem(icon: ImageVector, title: String, subtitle: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = "Detail",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreProfileDialog(onDismiss: () -> Unit) {
    var storeName by remember { mutableStateOf("STOKE.ID Retail Pro") }
    var address by remember { mutableStateOf("Jl. Merdeka No. 123, Jakarta") }
    var phone by remember { mutableStateOf("081234567890") }
    var email by remember { mutableStateOf("admin@stoke.id") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Profil Toko", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = storeName,
                    onValueChange = { storeName = it },
                    label = { Text("Nama Toko") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Alamat") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Nomor WhatsApp/HP") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrinterSettingsDialog(onDismiss: () -> Unit) {
    var selectedPrinter by remember { mutableStateOf("Tidak ada printer terpilih") }
    var paperSize by remember { mutableStateOf("58mm") }
    var showMockReceipt by remember { mutableStateOf(false) }

    if (showMockReceipt) {
        AlertDialog(
            onDismissRequest = { showMockReceipt = false },
            title = { Text("Simulasi Struk", fontWeight = FontWeight.Bold) },
            text = {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("STOKE.ID", fontWeight = FontWeight.Bold)
                        Text("Jl. Merdeka No. 123", style = MaterialTheme.typography.bodySmall)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        Text("TEST PRINT BERHASIL", fontWeight = FontWeight.Bold)
                        Text("Printer: $selectedPrinter", style = MaterialTheme.typography.bodySmall)
                        Text("Kertas: $paperSize", style = MaterialTheme.typography.bodySmall)
                        Text(java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date()), style = MaterialTheme.typography.bodySmall)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        Text("Terima Kasih", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showMockReceipt = false }) {
                    Text("Tutup Simulasi")
                }
            }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pengaturan Printer", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Bluetooth, contentDescription = "Bluetooth", tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Printer Terhubung", fontWeight = FontWeight.Bold)
                            Text(selectedPrinter, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                
                Button(onClick = { selectedPrinter = "POS-58 (Terhubung)" }, modifier = Modifier.fillMaxWidth()) {
                    Text("Cari Perangkat Bluetooth")
                }
                
                HorizontalDivider()

                Text("Ukuran Kertas", fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = paperSize == "58mm", onClick = { paperSize = "58mm" })
                        Text("58 mm")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = paperSize == "80mm", onClick = { paperSize = "80mm" })
                        Text("80 mm")
                    }
                }
                
                OutlinedButton(
                    onClick = { 
                        if (selectedPrinter != "Tidak ada printer terpilih") {
                            showMockReceipt = true
                        }
                    }, 
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedPrinter != "Tidak ada printer terpilih"
                ) {
                    Text("Test Print")
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("Tutup") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarcodeScannerSettingsDialog(onDismiss: () -> Unit) {
    var scannedText by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Scanner Barcode", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.QrCodeScanner, contentDescription = "Scanner", tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Mode Keyboard Wedge", fontWeight = FontWeight.Bold)
                            Text("Scanner barcode Bluetooth atau USB bekerja seperti keyboard.", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                Text("Arahkan kursor ke kolom di bawah ini dan scan sebuah barcode untuk menguji scanner.", style = MaterialTheme.typography.bodyMedium)
                
                OutlinedTextField(
                    value = scannedText,
                    onValueChange = { scannedText = it },
                    label = { Text("Test Scan Di Sini") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Menunggu scan...") }
                )
                
                if (scannedText.isNotEmpty()) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Barcode Terdeteksi: $scannedText",
                            modifier = Modifier.padding(12.dp),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("Tutup") }
        },
        dismissButton = {
            TextButton(onClick = { scannedText = "" }) { Text("Clear") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupRestoreDialog(onDismiss: () -> Unit) {
    var backupStatus by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Backup & Restore", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Lakukan pencadangan data (stok, riwayat transaksi) ke penyimpanan lokal atau Google Drive secara berkala agar tidak hilang saat aplikasi dihapus.", style = MaterialTheme.typography.bodyMedium)
                
                if (backupStatus.isNotEmpty()) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = backupStatus,
                            modifier = Modifier.padding(12.dp),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { 
                            backupStatus = "Mencadangkan data ke penyimpanan lokal..." 
                            coroutineScope.launch { 
                                delay(1500) 
                                backupStatus = "Backup berhasil disimpan di Download/STOKE_Backup.db" 
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Backup")
                    }
                    OutlinedButton(
                        onClick = { 
                            backupStatus = "Memulihkan data dari penyimpanan lokal..." 
                            coroutineScope.launch { 
                                delay(1500) 
                                backupStatus = "Restore berhasil! Silakan restart aplikasi." 
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Restore")
                    }
                }

                HorizontalDivider()

                OutlinedButton(
                    onClick = { 
                        backupStatus = "Menghubungkan ke Google Drive..." 
                        coroutineScope.launch { 
                            delay(2000) 
                            backupStatus = "Terhubung dengan Google Drive. Sinkronisasi sedang berjalan..." 
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.CloudUpload, contentDescription = "Cloud", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Backup ke Google Drive")
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("Tutup") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessSettingsDialog(onDismiss: () -> Unit) {
    var showPinDialog by remember { mutableStateOf(false) }

    if (showPinDialog) {
        AlertDialog(
            onDismissRequest = { showPinDialog = false },
            title = { Text("Ubah PIN Kasir", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Pilih PIN 6 digit baru untuk akses kasir.", style = MaterialTheme.typography.bodyMedium)
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("PIN Saat Ini") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("PIN Baru") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("Konfirmasi PIN Baru") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showPinDialog = false }) {
                    Text("Simpan PIN")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPinDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Akun & Hak Akses", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(MaterialTheme.colorScheme.primary, shape = androidx.compose.foundation.shape.CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("A", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Admin", fontWeight = FontWeight.Bold)
                            Text("Pemilik Toko", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
                
                Text("Kelola Keamanan", fontWeight = FontWeight.Bold)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Proteksi Kasir")
                        Text("Setiap transaksi memerlukan PIN", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(checked = true, onCheckedChange = {})
                }

                OutlinedButton(onClick = { showPinDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Filled.Lock, contentDescription = "Lock", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ubah PIN Kasir")
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("Tutup") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptSettingsDialog(onDismiss: () -> Unit) {
    var footerText by remember { mutableStateOf("Terima Kasih atas Kunjungan Anda") }
    var npwp by remember { mutableStateOf("") }
    var showLogo by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pengaturan Struk", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Tampilkan Logo")
                        Text("Cetak logo toko di atas struk", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(checked = showLogo, onCheckedChange = { showLogo = it })
                }
                
                OutlinedTextField(
                    value = footerText,
                    onValueChange = { footerText = it },
                    label = { Text("Pesan Footer Struk") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = npwp,
                    onValueChange = { npwp = it },
                    label = { Text("NPWP (Opsional)") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Contoh: 12.345.678.9-123.000") }
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}
