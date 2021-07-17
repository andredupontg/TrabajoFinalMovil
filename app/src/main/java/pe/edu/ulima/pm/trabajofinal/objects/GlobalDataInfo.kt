package pe.edu.ulima.pm.trabajofinal.objects

import pe.edu.ulima.pm.trabajofinal.models.dao.Global
import pe.edu.ulima.pm.trabajofinal.models.dao.GlobalData
import pe.edu.ulima.pm.trabajofinal.models.dao.SingleCountryData

object GlobalDataInfo {
    var globalData: GlobalData? = null
    var countriesData: ArrayList<SingleCountryData>? = null
    var globalStats: Global? = null
}