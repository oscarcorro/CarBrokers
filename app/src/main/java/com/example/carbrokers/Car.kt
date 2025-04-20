package com.example.carbrokers

import java.io.Serializable

//Clase para cada coche, utilizando la API: https://myfakeapi.com/api/cars/
class Car (
    val id: Int,
    val car: String,
    val car_model: String,
    val car_color: String,
    val car_model_year: Int,
    val price: String,
    var img: String
) : Serializable