package pe.edu.ulima.pm.trabajofinal.managers

import android.content.Context
import android.util.Log
import pe.edu.ulima.pm.trabajofinal.models.dao.CountriesService
import pe.edu.ulima.pm.trabajofinal.models.dao.CountryData
import retrofit2.Callback
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

interface OnGetCountriesDone {
    fun onSuccess(countries: ArrayList<CountryData>)
    fun onError(msg: String)
}

class CountriesManager {

    companion object {
        private var instance: CountriesManager? = null

        fun getInstance(): CountriesManager {
            if (instance == null) {
                instance = CountriesManager()
            }
            return instance!!
        }
    }

    /*fun getCountriesRetrofit(callback: OnGetCountriesDone, context: Context) {

        val retrofit =ConnectionManager.getInstance().getRetrofit()

        val devicesService = retrofit.create<CountriesService>()
        devicesService.getCountries().enqueue(object : Callback<ArrayList<CountryData>> {
            override fun onResponse(
                call: Call<ArrayList<CountryData>>,
                response: Response<ArrayList<CountryData>>
            ) {
                if (response.body() != null) {
                    callback.onSuccess(response.body()!!)
                }

                else {
                    callback.onError("ArrayList nulo")
                }
            }

            override fun onFailure(call: Call<ArrayList<CountryData>>, t: Throwable) {
                callback.onError(t.message!!)
            }
        })
    }*/

    /*
    fun getCountries() : ArrayList<CountryData> {

        var countriesList: ArrayList<CountryData>? = null
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.covid19api.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val devicesService = retrofit.create<CountriesService>()
        devicesService.getCountries().enqueue(object  : Callback<ArrayList<CountryData>> {
            override fun onResponse(
                call: Call<ArrayList<CountryData>>,
                response: Response<ArrayList<CountryData>>
            ) {
                if (response.body() == null){
                    Log.i("CountriesManager", "ArrayList vacio")
                } else {
                    countriesList = response.body()!!
                }
            }

            override fun onFailure(call: Call<ArrayList<CountryData>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return countriesList!!
    }*/
}