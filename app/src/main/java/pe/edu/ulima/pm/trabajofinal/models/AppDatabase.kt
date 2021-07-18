package pe.edu.ulima.pm.trabajofinal.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.ulima.pm.trabajofinal.models.persistence.dao.CountryDAO
import pe.edu.ulima.pm.trabajofinal.models.persistence.dao.DateDAO
import pe.edu.ulima.pm.trabajofinal.models.persistence.dao.GlobalDAO
import pe.edu.ulima.pm.trabajofinal.models.persistence.entities.CountryEntity
import pe.edu.ulima.pm.trabajofinal.models.persistence.entities.DateEntity
import pe.edu.ulima.pm.trabajofinal.models.persistence.entities.GlobalEntity

@Database(entities = arrayOf(CountryEntity::class, GlobalEntity::class, DateEntity::class), version = 1)
abstract class AppDatabase: RoomDatabase () {

    abstract val countryDAO: CountryDAO
    abstract val globalDAO: GlobalDAO
    abstract val dateDAO: DateDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        //Instancia unica de la BD
        fun getInstance(context: Context): AppDatabase {
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "CovidInfo_db")
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}
