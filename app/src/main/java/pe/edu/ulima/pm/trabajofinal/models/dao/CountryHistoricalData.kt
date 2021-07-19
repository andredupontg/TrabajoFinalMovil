package pe.edu.ulima.pm.trabajofinal.models.dao

data class CountryHistoricalData(
    val ID: String,
    val Country: String,
    val CountryCode: String,
    val Province: String,
    val City: String,
    val CityCode: String,
    val Lat: String,
    val Lon: String,
    val Confirmed: Long,
    val Deaths: Long,
    val Recovered: Long,
    val Active: Long,
    val Date: String
)
