package pe.edu.ulima.pm.trabajofinal.models.dao

data class GlobalData(
    val ID: String,
    val Message: String,
    var Global: Global,
    val Countries: ArrayList<SingleCountryData>
)
