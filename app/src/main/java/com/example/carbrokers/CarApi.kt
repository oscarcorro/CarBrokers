package com.example.carbrokers
//importación de retrofit para hacer las llamadas a http
import retrofit2.Call
import retrofit2.http.GET

//interfaz que indica como se van a comunicar con la API
interface CarApi{
    @GET("api/cars") //indica que se va a hacer un GET a la API
    fun getCars(): Call<carResponse> //llamada que devuelve carResponse
}