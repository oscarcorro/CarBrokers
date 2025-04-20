package com.example.carbrokers
import androidx.room.*

//Interfaz con las funciones para insertar, borrar y gestionar coches favoritos
//funciones suspend: funciones asíncronas
//DAO: Data Access Object
@Dao
interface FavoriteCarDao {

    //Insertar coche en la BBDD, si ya existe uno con este id, lo reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(car: FavoriteCar)

    //Borrar coche de favoritos de la base de datos
    @Delete
    suspend fun deleteFavorite(car: FavoriteCar)

    //Obtener lista con todos los coches favoritos
    @Query("SELECT * FROM favorite_cars")
    suspend fun getAllFavorites(): List<FavoriteCar>

    //Buscar coche por id en favoritos
    @Query("SELECT * FROM favorite_cars WHERE id = :carId LIMIT 1")
    suspend fun getFavoriteById(carId: Int): FavoriteCar?
}