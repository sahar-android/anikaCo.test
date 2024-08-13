package com.application.aniktest.presentation

import com.application.aniktest.data.entities.Customer
import com.application.aniktest.data.entities.CustomerFoodCrossRef
import com.application.aniktest.data.entities.Food


//i used this class for showing process of fetching data from database in ui
sealed class RoomState{
    object START:RoomState()
    object LOADING:RoomState()
    data class SUCCESSGETCus(val customers: List<Customer>):RoomState()
    data class SUCCESSGETFOODS(val foods:List<Food>):RoomState()
    data class SUCCESSGETCUSFOOD(val foods: List<CustomerFoodCrossRef>):RoomState()
    data class ERROR(val error:String):RoomState()
}
