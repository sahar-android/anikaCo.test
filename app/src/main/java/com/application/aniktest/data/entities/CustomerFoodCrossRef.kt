package com.application.aniktest.data.entities

import androidx.room.Entity

@Entity(tableName = "CustomerwithFood",primaryKeys = ["customerId","foodId"])
data class CustomerFoodCrossRef(
  val customerId:Int,
    val foodId:Int
)
