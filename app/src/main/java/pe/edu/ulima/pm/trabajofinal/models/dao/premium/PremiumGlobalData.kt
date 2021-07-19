package pe.edu.ulima.pm.trabajofinal.models.dao.premium

data class PremiumGlobalData(
    val ID: String,
    val Message: String,
    val Countries: ArrayList<PremiumSingleCountryData>,
    var Date: String
)
