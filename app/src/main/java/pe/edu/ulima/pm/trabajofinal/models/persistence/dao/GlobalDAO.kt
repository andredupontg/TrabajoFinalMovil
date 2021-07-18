package pe.edu.ulima.pm.trabajofinal.models.persistence.dao

import android.provider.Settings
import androidx.room.*
import pe.edu.ulima.pm.trabajofinal.models.persistence.entities.GlobalEntity

@Dao
interface GlobalDAO {

    @Query("SELECT * FROM GlobalEntity")
    suspend fun getAllGlobal(): List<GlobalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobal(global: GlobalEntity)

    //Query para buscar un global especifico
    @Transaction
    @Query("SELECT * FROM GlobalEntity WHERE ID = :ID")
    suspend fun getSingleTeam(ID: String): GlobalEntity
}