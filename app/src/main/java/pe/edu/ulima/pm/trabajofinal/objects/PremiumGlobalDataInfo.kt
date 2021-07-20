package pe.edu.ulima.pm.trabajofinal.objects

import pe.edu.ulima.pm.trabajofinal.models.dao.PremiumGlobalData
import pe.edu.ulima.pm.trabajofinal.models.dao.PremiumSingleCountryData

object PremiumGlobalDataInfo {

    var premiumGlobalData: PremiumGlobalData? = null
    var premiumCountriesData: ArrayList<PremiumSingleCountryData>? = null
}