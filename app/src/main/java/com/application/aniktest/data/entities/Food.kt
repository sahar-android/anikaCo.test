package com.application.aniktest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Food")
data class Food(
    @PrimaryKey(autoGenerate = true) val foodId:Int=0,
    val foodName:String
)
