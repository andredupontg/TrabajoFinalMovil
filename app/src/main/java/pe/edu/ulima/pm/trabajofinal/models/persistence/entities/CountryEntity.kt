package pe.edu.ulima.pm.trabajofinal.models.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryEntity (
    @PrimaryKey(autoGenerate = false) val Country: String,
    @ColumnInfo(name = "Slug") val Slug: String,
    @ColumnInfo(name = "ISO2") val ISO2: String
)