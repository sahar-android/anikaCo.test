package com.application.aniktest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.aniktest.data.entities.Customer
import com.application.aniktest.data.entities.CustomerFoodCrossRef
import com.application.aniktest.data.entities.CustomerWithFoods
import com.application.aniktest.data.entities.Food
import com.application.aniktest.presentation.RoomState
import com.application.aniktest.repository.ResturantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResturantViewModel @Inject constructor(private val resturantRepository: ResturantRepository) :
    ViewModel() {

    private  val TAG = "ResturantViewModel31243"

    private val _customers= MutableStateFlow<RoomState>(RoomState.START)
    val customers: StateFlow<RoomState>
        get() = _customers

    private val _foods= MutableStateFlow<RoomState>(RoomState.START)
    val foods: StateFlow<RoomState>
        get() = _foods

    private val _foodsOfCustomer= MutableStateFlow<RoomState>(RoomState.START)
    val foodsOfCustomer: StateFlow<RoomState>
        get() = _foodsOfCustomer

        fun insertCustomer(customer: Customer){
            viewModelScope.launch(Dispatchers.IO) {
                try {

                    resturantRepository.insertCustomer(customer)

                }catch (e:Exception){
                    Log.i(TAG, "insertCustomer: ${e.message}")
                }
            }
        }

    fun insertFood(food:Food){
            viewModelScope.launch(Dispatchers.IO) {
                try {

                    resturantRepository.insertFood(food)

                }catch (e:Exception){
                    Log.i(TAG, "insertCustomer: ${e.message}")
                }
            }
        }

    fun insertCustomerWithFood(customerFoodCrossRef: CustomerFoodCrossRef){
        viewModelScope.launch(Dispatchers.IO) {
            try {

                resturantRepository.insertCustomerWithFoods(customerFoodCrossRef)

            }catch (e:Exception){
                Log.i(TAG, "insertCustomerWithFood: ${e.message}")
            }
        }
    }

     fun getCustomers() {
        _customers.value=RoomState.LOADING

        viewModelScope.launch(Dispatchers.IO) {
            try {
                resturantRepository.getAllCustumers().collect{
                    when(it){
                        is RoomState.SUCCESSGETCus ->{
                            _customers.value=RoomState.SUCCESSGETCus(it.customers)

                        }
                        is RoomState.ERROR ->{
                            _customers.value=RoomState.ERROR(it.error)

                        }

                        else -> {
                            _customers.value=RoomState.ERROR("no success no error!")

                        }
                    }
                }

            }catch (e:Exception){
                _customers.value=RoomState.ERROR(e.message!!)

            }

        }
    }


    fun getFoods() {
        _foods.value=RoomState.LOADING

        viewModelScope.launch(Dispatchers.IO) {
            try {
                resturantRepository.getAllFoods().collect{
                    when(it){
                        is RoomState.SUCCESSGETFOODS ->{
                            _foods.value=RoomState.SUCCESSGETFOODS(it.foods)

                        }
                        is RoomState.ERROR ->{
                            _foods.value=RoomState.ERROR(it.error)

                        }

                        else -> {
                            _foods.value=RoomState.ERROR("no success no error!")

                        }
                    }
                }

            }catch (e:Exception){
                _foods.value=RoomState.ERROR(e.message!!)

            }

        }
    }

    fun getFoodsOfCustomer() {
        _foodsOfCustomer.value=RoomState.LOADING

        viewModelScope.launch(Dispatchers.IO) {
            try {
                resturantRepository.getCustomerWithFoods().collect{
                    when(it){
                        is RoomState.SUCCESSGETCUSFOOD ->{
                            _foodsOfCustomer.value=RoomState.SUCCESSGETCUSFOOD(it.foods)

                        }
                        is RoomState.ERROR ->{
                            _foodsOfCustomer.value=RoomState.ERROR(it.error)

                        }

                        else -> {
                            _foodsOfCustomer.value=RoomState.ERROR("no success no error!")

                        }
                    }
                }

            }catch (e:Exception){
                _foodsOfCustomer.value= RoomState.ERROR(e.message!!)

            }

        }
    }


}


