package pe.edu.ulima.pm.trabajofinal.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pe.edu.ulima.pm.trabajofinal.R
import pe.edu.ulima.pm.trabajofinal.SingleCountryActivity
import pe.edu.ulima.pm.trabajofinal.adapters.CountriesRankRVAdapter
import pe.edu.ulima.pm.trabajofinal.adapters.OnCountryRankItemClickListener
import pe.edu.ulima.pm.trabajofinal.models.dao.CovidAPIService
import pe.edu.ulima.pm.trabajofinal.models.dao.premium.PremiumSingleCountryData
import pe.edu.ulima.pm.trabajofinal.objects.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountriesRankFragment: Fragment(), OnCountryRankItemClickListener {

    private var rviCompetitions: RecyclerView? = null
    private var orderedCountries: ArrayList<PremiumSingleCountryData> = ArrayList()
    private var countryName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_countries_rank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderCountriesByTotalCases()

        //Llamando al RV del fragment
        rviCompetitions = view.findViewById(R.id.rviCountriesRank)

        val countriesRVAdapter = CountriesRankRVAdapter(orderedCountries, this, view.context)
        rviCompetitions!!.adapter = countriesRVAdapter

    }

    // Se ordena la lista de paises de acuerdo a los casos por millon
    private fun orderCountriesByTotalCases() {

        val countries = PremiumGlobalDataInfo.premiumCountriesData!!
        val cases: ArrayList<Double> = ArrayList()

        for (i in 1..countries.size) {
            cases.add(countries[i-1].TotalCasesPerMillion)
        }

        cases.sort()
        Log.i("Cases", cases.toString())

        //Se excluye a los paises con 0 casos
        for (i in 1..countries.size) {
            for (j in 1..countries.size) {
                if (countries[j-1].TotalCasesPerMillion == cases[i-1] && countries[j-1].TotalCases.toInt() != 0)
                    orderedCountries.add(countries[j-1])
            }
        }
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