package pe.edu.ulima.pm.trabajofinal.models.dao


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

//https://api.covid19api.com

interface CountriesService {

    @GET
    suspend fun getCountries(@Url url: String): Response<ArrayList<CountryData>>

    @GET
    suspend fun getGlobalData(@Url url: String): Response<GlobalData>
}