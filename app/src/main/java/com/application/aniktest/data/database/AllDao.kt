package com.application.aniktest.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.application.aniktest.data.entities.Customer
import com.application.aniktest.data.entities.CustomerFoodCrossRef
import com.application.aniktest.data.entities.CustomerWithFoods
import com.application.aniktest.data.entities.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface AllDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: Food)

    @Query("SELECT * FROM Customer")
    suspend fun getAllCustomers():List<Customer>

    @Query("SELECT * FROM Food")
    suspend fun getAllFoods():List<Food>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouritFood(customerFoodCrossRef: CustomerFoodCrossRef)

    @Transaction
    @Query("SELECT * FROM CustomerwithFood")
    fun getCustomerWithFoods(): List<CustomerFoodCrossRef>

}