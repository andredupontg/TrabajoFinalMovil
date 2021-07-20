package pe.edu.ulima.pm.trabajofinal.models.dao

// Data class para poder hacer el ranking
data class PremiumGlobalData(
    val ID: String,
    val Message: String,
    val Countries: ArrayList<PremiumSingleCountryData>,
    var Date: String
)
