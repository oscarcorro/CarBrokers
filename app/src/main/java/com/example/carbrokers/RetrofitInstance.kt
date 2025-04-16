package com.example.carbrokers
//importación de retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//objeto para configurar retrofit
object RetrofitInstance{
    private const val BASE_URL = "https://myfakeapi.com/" //url base de la api

    //servicio retrofit, inicializandolo una vez con 'by lazy'
    val api: CarApi by lazy {
        //creamos instancia de retrrofit
        Retrofit.Builder()
            .baseUrl(BASE_URL) //indicamos la base de la URL
            .addConverterFactory(GsonConverterFactory.create()) //usa gson para convertir
            .build() //terminamos la construcción
            .create(CarApi::class.java) //interfaz que va a usar para las llamadas
    }
}