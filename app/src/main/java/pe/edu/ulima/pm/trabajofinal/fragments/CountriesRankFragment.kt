package pe.edu.ulima.pm.trabajofinal.fragments

import android.content.Intent
import android.os.Bundle
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
import pe.edu.ulima.pm.trabajofinal.objects.GlobalDataInfo
import pe.edu.ulima.pm.trabajofinal.objects.SingleCountryStats

class CountriesRankFragment: Fragment(), OnCountryRankItemClickListener {

    private var rviCompetitions: RecyclerView? = null
    private var countriesListTest = listOf<CountryData>()
    private var orderedCountries: ArrayList<SingleCountryData> = ArrayList()

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

        val countriesRVAdapter = CountriesRankRVAdapter(GlobalDataInfo.globalData!!.Countries, this, view.context)
        rviCompetitions!!.adapter = countriesRVAdapter

    }

    private fun orderCountriesByTotalCases() {

        val countries = GlobalDataInfo.globalData!!.Countries
        val cases: ArrayList<Long> = ArrayList()

        for (i in 0..countries.size) {
            cases.add(countries[i].TotalConfirmed) // [1000,20000,500,40000...]
        }

        cases.sort()

        for (i in 0..countries.size) {
            for (j in 0..countries.size) {
                if (countries[j].TotalConfirmed == cases[i])
                    orderedCountries.add(countries[j])
            }
        }
    }

    override fun onClick(country: SingleCountryData) {

        //Actualizando el Singleton con la info del pais seleccionado
        SingleCountryStats.country = country

        //Abrir SingleCountryActivity
        val intent = Intent(context, SingleCountryActivity::class.java)
        startActivity(intent)
    }
}