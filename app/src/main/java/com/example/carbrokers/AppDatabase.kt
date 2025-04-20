package com.example.carbrokers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//simulación de la base de datos en room
@Database(entities = [FavoriteCar::class], version=1) //definimos las tablas y la versión de la BD
abstract class AppDatabase : RoomDatabase() {
    //generar el DAO para favoritos
    abstract fun favoriteCarDat(): FavoriteCarDao
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        //función que devuelve la bbdd y si no existe la crea
        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "carbrokers_db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}