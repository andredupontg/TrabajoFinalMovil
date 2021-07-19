package pe.edu.ulima.pm.trabajofinal.models.dao

// Data class para poder realizar la busqueda de pais por slug como parametro ejemplo South Africa, slug = south-africa
data class CountryData(val Country: String, val Slug: String, val ISO2: String)
