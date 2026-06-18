package com.example

import android.app.Application
import com.example.data.AppDatabase

class StokeApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { StokeRepository(database.stokeDao()) }
}
