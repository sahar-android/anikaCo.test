package com.application.aniktest.repository

import android.util.Log
import com.application.aniktest.data.database.AllDao
import com.application.aniktest.data.entities.Customer
import com.application.aniktest.data.entities.CustomerFoodCrossRef
import com.application.aniktest.data.entities.CustomerWithFoods
import com.application.aniktest.data.entities.Food
import com.application.aniktest.presentation.RoomState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResturantRepositoryImpl @Inject constructor(private val allDao: AllDao):ResturantRepository {
    private  val TAG = "ResturantRepositoryImpl1234"
    override suspend fun insertCustomer(customer: Customer) {
        withContext(Dispatchers.IO){
            allDao.insertCustomer(customer)
        }
    }

    override suspend fun insertFood(food: Food) {
        withContext(Dispatchers.IO){
            allDao.insertFood(food)
        }
    }

    override suspend fun getAllCustumers(): Flow<RoomState> {
        return flow {
            try {
                emit(RoomState.SUCCESSGETCus(allDao.getAllCustomers()))

            }catch (e:Exception){
                Log.i(TAG, "getAllCustumers: ")
            }
        }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getAllFoods(): Flow<RoomState> {
        return flow {
            try {
                emit(RoomState.SUCCESSGETFOODS(allDao.getAllFoods()))

            }catch (e:Exception){
                Log.i(TAG, "getAllfoods: ")
            }
        }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getCustomerWithFoods(): Flow<RoomState> {
        return flow {
            try {
                emit(RoomState.SUCCESSGETCUSFOOD(allDao.getCustomerWithFoods()))

            }catch (e:Exception){
                Log.i(TAG, "getCustomerWithFoods: ${e.message}")
            }
        }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun insertCustomerWithFoods(customerFoodCrossRef: CustomerFoodCrossRef) {
        withContext(Dispatchers.IO){
            allDao.insertFavouritFood(customerFoodCrossRef)
        }
    }
}