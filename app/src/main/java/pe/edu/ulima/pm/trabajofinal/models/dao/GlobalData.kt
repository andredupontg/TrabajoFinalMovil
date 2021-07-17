package pe.edu.ulima.pm.trabajofinal.models.dao

data class GlobalData(
    val ID: String,
    val Message: String,
    val Global: Global,
    val Countries: ArrayList<SingleCountryData>
)
