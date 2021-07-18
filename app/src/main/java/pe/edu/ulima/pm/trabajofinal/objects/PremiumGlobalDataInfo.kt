package pe.edu.ulima.pm.trabajofinal.objects

import pe.edu.ulima.pm.trabajofinal.models.dao.premium.PremiumGlobalData
import pe.edu.ulima.pm.trabajofinal.models.dao.premium.PremiumSingleCountryData

object PremiumGlobalDataInfo {

    var premiumGlobalData: PremiumGlobalData? = null
    var premiumCountriesData: ArrayList<PremiumSingleCountryData>? = null
}