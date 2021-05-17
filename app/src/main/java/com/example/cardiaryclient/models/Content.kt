package com.example.cardiaryclient.models

data class Content(
    val brand: Brand,
    val client: Client,
    val equipment: Equipment,
    val id: Int,
    val mileage: Int,
    val model: Model,
    val ours: Boolean,
    val photoUrl: String,
    val price: Int,
    val used: Boolean,
    val vin: String,
    val year: Int
)