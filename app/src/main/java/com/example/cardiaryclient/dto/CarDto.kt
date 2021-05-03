package com.example.cardiaryclient.dto

data class CarDto(
    val brand: Brand,
    val id: Int,
    val mileage: Int,
    val model: Model,
    val photoUrl: String,
    val vin: String,
    val year: Int
)