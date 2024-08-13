package com.application.aniktest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.application.aniktest.data.entities.CustomerFoodCrossRef
import com.application.aniktest.data.entities.Customer
import com.application.aniktest.data.entities.Food


@Database(
    entities = [Customer::class, Food::class, CustomerFoodCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class ResturantDatabase : RoomDatabase() {
   abstract val allDao:AllDao


}