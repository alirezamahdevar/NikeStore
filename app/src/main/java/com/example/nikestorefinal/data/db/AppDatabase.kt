package com.example.nikestorefinal.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nikestorefinal.data.Product
import com.example.nikestorefinal.data.repo.source.ProductLocalDataSource

@Database(entities = [Product::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductLocalDataSource

}