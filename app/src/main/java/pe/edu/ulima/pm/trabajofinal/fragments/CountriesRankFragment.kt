package pe.edu.ulima.pm.trabajofinal.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.trabajofinal.R
import pe.edu.ulima.pm.trabajofinal.SingleCountryActivity
import pe.edu.ulima.pm.trabajofinal.adapters.CountriesRankRVAdapter
import pe.edu.ulima.pm.trabajofinal.adapters.OnCountryRankItemClickListener
import pe.edu.ulima.pm.trabajofinal.models.dao.CountryData
import pe.edu.ulima.pm.trabajofinal.models.dao.SingleCountryData
import pe.edu.ulima.pm.trabajofinal.models.dao.premium.PremiumSingleCountryData
import pe.edu.ulima.pm.trabajofinal.objects.GlobalDataInfo
import pe.edu.ulima.pm.trabajofinal.objects.PremiumGlobalDataInfo
import pe.edu.ulima.pm.trabajofinal.objects.PremiumSingleCountryStats
import pe.edu.ulima.pm.trabajofinal.objects.SingleCountryStats

class CountriesRankFragment: Fragment(), OnCountryRankItemClickListener {

    private var rviCompetitions: RecyclerView? = null
    private var orderedCountries: ArrayList<PremiumSingleCountryData> = ArrayList()

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

    private fun orderCountriesByTotalCases() {

        val countries = PremiumGlobalDataInfo.premiumGlobalData!!.Countries
        val cases: ArrayList<Int> = ArrayList()

        for (i in 1..countries.size) {
            cases.add(countries[i-1].TotalCases.toInt()) // [1000,20000,500,40000...]
        }

        cases.sort()
        Log.i("Cases", cases.toString())

        for (i in 1..countries.size) {
            for (j in 1..countries.size) {
                if (countries[j-1].TotalCases.toInt() == cases[i-1] && countries[j-1].TotalCases.toInt() != 0)
                    orderedCountries.add(countries[j-1])
            }
        }
    }

    override fun onClick(country: PremiumSingleCountryData) {

        //Actualizando el Singleton con la info del pais seleccionado
        PremiumSingleCountryStats.country = country

        //Abrir SingleCountryActivity
        val intent = Intent(context, SingleCountryActivity::class.java)
        startActivity(intent)
    }
}