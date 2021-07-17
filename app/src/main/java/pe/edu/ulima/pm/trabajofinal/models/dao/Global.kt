package pe.edu.ulima.pm.trabajofinal.models.dao

data class Global(
    var NewConfirmed: Long,
    var TotalConfirmed: Long,
    var NewDeaths: Long,
    var TotalDeaths: Long,
    var NewRecovered: Long,
    var TotalRecovered: Long,
    var Date: String
)
