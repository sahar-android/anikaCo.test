package com.application.aniktest.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CustomerWithFoods(
    @Embedded val customer: Customer,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "foodId",
        associateBy = Junction(CustomerFoodCrossRef::class)
    )
    val foods:List<Food>
)
