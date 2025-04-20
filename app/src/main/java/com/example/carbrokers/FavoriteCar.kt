package com.example.carbrokers

import androidx.room.Entity
import androidx.room.PrimaryKey

//coche favorito guardado en la bbdd
@Entity(tableName = "favorite_cars")
class FavoriteCar(
    @PrimaryKey val id: Int, //id único de cada coche
    val car: String,
    val car_model: String,
    val car_color: String,
    val car_model_year: Int,
    val price: String,
    val img: String
)