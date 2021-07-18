package pe.edu.ulima.pm.trabajofinal.models.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DateEntity (
    @PrimaryKey(autoGenerate = true) val ID: Int,
    @ColumnInfo(name = "Date") val Date: String
)