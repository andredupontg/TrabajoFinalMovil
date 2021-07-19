package pe.edu.ulima.pm.trabajofinal.models.dao.premium

// Data class para poder acceder a la data de la api con la info de un país como parametro
data class PremiumSingleCountryData(
    val ID: String,
    val CountryISO: String,
    val Country: String,
    val Continent: String,
    val Date: String,
    val TotalCases: Long,
    val NewCases: Long,
    val TotalDeaths: Long,
    val NewDeaths: Long,
    val TotalCasesPerMillion: Double,
    val NewCasesPerMillion: Double,
    val TotalDeathsPerMillion: Double,
    val NewDeathsPerMillion: Double,
    val StringencyIndex: Long,
    val DailyIncidenceConfirmedCases: Double,
    val DailyIncidenceConfirmedDeaths: Double,
    val DailyIncidenceConfirmedCasesPerMillion: Double,
    val DailyIncidenceConfirmedDeathsPerMillion: Double,
    val IncidenceRiskConfirmedPerHundredThousand: Double,
    val IncidenceRiskDeathsPerHundredThousand: Double,
    val IncidenceRiskNewConfirmedPerHundredThousand: Double,
    val IncidenceRiskNewDeathsPerHundredThousand: Double,
    val CaseFatalityRatio: Double
)
