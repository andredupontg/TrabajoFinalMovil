package pe.edu.ulima.pm.trabajofinal.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pe.edu.ulima.pm.trabajofinal.R
import pe.edu.ulima.pm.trabajofinal.SingleCountryActivity
import pe.edu.ulima.pm.trabajofinal.adapters.CountriesInfoRVAdapter
import pe.edu.ulima.pm.trabajofinal.adapters.OnCountryInfoItemClickListener
import pe.edu.ulima.pm.trabajofinal.models.dao.CovidAPIService
import pe.edu.ulima.pm.trabajofinal.models.dao.PremiumSingleCountryData
import pe.edu.ulima.pm.trabajofinal.objects.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountriesInfoFragment: Fragment(), OnCountryInfoItemClickListener {

    private var rviCompetitions: RecyclerView? = null
    private var countryName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_countries_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Llamando al RV del fragment
        rviCompetitions = view.findViewById(R.id.rviCountriesInfo)

        removeEmptyCountries()

        val countriesRVAdapter = CountriesInfoRVAdapter(removeEmptyCountries(), this, view.context)
        rviCompetitions!!.adapter = countriesRVAdapter

    }

    // Devuelve una lista sin paises con 0 casos
    private fun removeEmptyCountries(): ArrayList<PremiumSingleCountryData> {
        val countries = PremiumGlobalDataInfo.premiumCountriesData
        for (i in 1..190) {
            if (countries!![i-1].TotalCases.toInt() == 0) {
                countries.removeAt(i-1)
            }
        }
        return countries!!
    }

    //Para llamar a la direccion base del retrofit
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.covid19api.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    // Se solicita la informacion historica del pais seleccionado
    private fun searchSingleCountryHistoricalData() {

        lifecycleScope.launch {
            val call = getRetrofit().create(CovidAPIService::class.java).getCountryHistoricalStats("/country/$countryName")

            if (call.isSuccessful) {
                SingleCountryHistoricalStats.countryHistoricalData = call.body()
                startActivity(Intent(context,SingleCountryActivity::class.java))
            }
        }
    }

    // Para obtener el nombre del pais en minusculas y sin espacios
    private fun getCountrySlug(countryName: String): String {
        return countryName.replace(" ", "-").lowercase()
    }

    //Cuando se hace click en un pais
    override fun onClick(country: PremiumSingleCountryData) {

        //Actualizando el Singleton con la info del pais seleccionado
        PremiumSingleCountryStats.country = country
        countryName = getCountrySlug(country.Country)
        if (InternetConnection.isConnected) {
            searchSingleCountryHistoricalData()
        } else {
            startActivity(Intent(context,SingleCountryActivity::class.java))
        }
    }
}