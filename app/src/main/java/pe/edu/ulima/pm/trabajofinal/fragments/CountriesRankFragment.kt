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
import pe.edu.ulima.pm.trabajofinal.models.Country
import pe.edu.ulima.pm.trabajofinal.objects.SingleCountryStats

class CountriesRankFragment: Fragment(), OnCountryRankItemClickListener {

    private var rviCompetitions: RecyclerView? = null
    private var countriesListTest = listOf<Country>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_countries_rank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCountriesTest()

        //Llamando al RV del fragment
        rviCompetitions = view.findViewById(R.id.rviCountriesRank)

        val countriesRVAdapter = CountriesRankRVAdapter(ArrayList(countriesListTest), this, view.context)
        rviCompetitions!!.adapter = countriesRVAdapter

    }

    override fun onClick(country: Country) {
        //Actualizando el Singleton con la info del pais seleccionado
        SingleCountryStats.country = country

        //Abrir SingleCountryActivity
        val intent = Intent(context, SingleCountryActivity::class.java)
        startActivity(intent)
    }

    private fun setCountriesTest() {
        val ci= "Country Info"
        countriesListTest = listOf(
            Country("Australia", ci),
            Country("United States of America", ci),
            Country("Peru", ci),
            Country("Brazil", ci),
            Country("Italy", ci),
            Country("Thailand", ci),
            Country("China", ci),
            Country("Argentina", ci),
            Country("Turkey", ci),
            Country("Germany", ci),
            Country("Netherlands", ci),
            Country("Spain", "Country info"),
            Country("Russia", "Country info"),
            Country("South Africa", "Country info"),
            Country("Trinidad and Tobago", "Country info"),
            Country("Paraguay", "Country info"),
            Country("Venezuela", "Country info")
        )
    }
}