package pe.edu.ulima.pm.trabajofinal.models.dao

data class SingleCountryData(
    val ID: String,
    val Country: String,
    val CountryCode: String,
    val Slug: String,
    val NewConfirmed: String,
    val TotalConfirmed: Long,
    val Lon: Long,
    val NewDeaths: Long,
    val TotalDeaths: Long,
    val NewRecovered: Long,
    val TotalRecovered: Long,
    val Date: String
)
