package com.application.aniktest.repository


import com.application.aniktest.data.entities.Customer
import com.application.aniktest.data.entities.CustomerFoodCrossRef
import com.application.aniktest.data.entities.CustomerWithFoods
import com.application.aniktest.data.entities.Food
import com.application.aniktest.presentation.RoomState
import kotlinx.coroutines.flow.Flow

//for simplify codes and making it easier to manage, test, and modify
interface ResturantRepository {
    suspend fun insertCustomer(customer: Customer)
    suspend fun insertFood(food: Food)
    suspend fun getAllCustumers(): Flow<RoomState>
    suspend fun getAllFoods():Flow<RoomState>
    suspend fun getCustomerWithFoods():Flow<RoomState>
    suspend fun insertCustomerWithFoods(customerFoodCrossRef: CustomerFoodCrossRef)


}