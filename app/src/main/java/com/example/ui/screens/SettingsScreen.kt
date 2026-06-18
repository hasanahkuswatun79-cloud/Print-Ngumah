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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var showStoreProfile by remember { mutableStateOf(false) }
    var showPrinterSettings by remember { mutableStateOf(false) }

    if (showStoreProfile) {
        StoreProfileDialog(onDismiss = { showStoreProfile = false })
    }

    if (showPrinterSettings) {
        PrinterSettingsDialog(onDismiss = { showPrinterSettings = false })
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
                subtitle = "Pesan footer, NPWP, dan format cetak"
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
                subtitle = "Pengaturan pemindai eksternal"
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
                subtitle = "Cadangkan data ke Cloud atau lokal"
            )
        }
        item {
            SettingsItem(
                icon = Icons.Filled.Security,
                title = "Akun & Hak Akses",
                subtitle = "Kelola pengguna dan PIN kasir"
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
                
                Divider()

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
                
                OutlinedButton(onClick = { /* Test print */ }, modifier = Modifier.fillMaxWidth()) {
                    Text("Test Print")
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("Tutup") }
        }
    )
}
