package pe.edu.ulima.pm.trabajofinal.models.persistence.dao

import androidx.room.*
import pe.edu.ulima.pm.trabajofinal.models.persistence.entities.DateEntity

@Dao
interface DateDAO {

    @Query("SELECT * FROM DateEntity")
    suspend fun getAllDates(): List<DateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDate(date: DateEntity)

    //Query para buscar un pais especifico
    @Transaction
    @Query("SELECT * FROM DateEntity WHERE ID = :ID")
    suspend fun getSingleTeam(ID: String): DateEntity
}