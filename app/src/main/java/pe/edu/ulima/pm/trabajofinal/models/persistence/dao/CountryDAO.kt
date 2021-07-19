package pe.edu.ulima.pm.trabajofinal.models.persistence.dao

import androidx.room.*
import pe.edu.ulima.pm.trabajofinal.models.persistence.entities.CountryEntity

// Aqui es donde vamos a interactuar con nuestro entidad País para poder luego agregarlos a la bd y mostrarlos en el recycler Vi
@Dao
interface CountryDAO {

    @Query("SELECT * FROM CountryEntity")
    suspend fun getAllCountries(): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: CountryEntity)

    //Query para buscar un pais especifico
    @Transaction
    @Query("SELECT * FROM CountryEntity WHERE Country = :Country")
    suspend fun getSingleTeam(Country: String): CountryEntity
}