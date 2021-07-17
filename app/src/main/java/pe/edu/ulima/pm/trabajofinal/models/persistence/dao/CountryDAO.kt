package pe.edu.ulima.pm.trabajofinal.models.persistence.dao

import androidx.room.*
import pe.edu.ulima.pm.trabajofinal.models.persistence.entities.CountryEntity

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