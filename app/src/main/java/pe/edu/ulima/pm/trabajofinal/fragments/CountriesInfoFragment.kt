package pe.edu.ulima.pm.trabajofinal.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.trabajofinal.R
import pe.edu.ulima.pm.trabajofinal.SingleCountryActivity
import pe.edu.ulima.pm.trabajofinal.adapters.CountriesInfoRVAdapter
import pe.edu.ulima.pm.trabajofinal.adapters.OnCountryInfoItemClickListener
import pe.edu.ulima.pm.trabajofinal.models.dao.CountryData
import pe.edu.ulima.pm.trabajofinal.models.dao.SingleCountryData
import pe.edu.ulima.pm.trabajofinal.objects.CountriesList
import pe.edu.ulima.pm.trabajofinal.objects.GlobalDataInfo
import pe.edu.ulima.pm.trabajofinal.objects.SingleCountryStats
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountriesInfoFragment: Fragment(), OnCountryInfoItemClickListener {

    private var title: TextView? = null

    private var rviCompetitions: RecyclerView? = null
    private var countriesListTest = listOf<CountryData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_countries_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCountryDataList()

        //Llamando al RV del fragment
        rviCompetitions = view.findViewById(R.id.rviCountriesInfo)

        Log.i("CountriesInfoFragment", CountriesList.toString())
        val countriesRVAdapter = CountriesInfoRVAdapter(GlobalDataInfo.countriesData!!, this, view.context)
        rviCompetitions!!.adapter = countriesRVAdapter

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.covid19api.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun setCountryDataList() {
        val ci= "Country Info"
        countriesListTest = listOf(
            CountryData("Australia", ci,""),
            CountryData("United States of America", ci,""),
            CountryData("Peru", ci,""),
            CountryData("Brazil", ci,""),
            CountryData("Italy", ci,""),
            CountryData("Thailand", ci,""),
            CountryData("China", ci,""),
            CountryData("Argentina", ci,""),
            CountryData("Turkey", ci,""),
            CountryData("Germany", ci,""),
            CountryData("Netherlands", ci,""),
            CountryData("Spain", "Country info",""),
            CountryData("Russia", "Country info",""),
            CountryData("South Africa", "Country info",""),
            CountryData("Trinidad and Tobago", "Country info",""),
            CountryData("Paraguay", "Country info",""),
            CountryData("Venezuela", "Country info","")
        )
    }

    override fun onClick(country: SingleCountryData) {

        //Actualizando el Singleton con la info del pais seleccionado
        SingleCountryStats.country = country

        //Abrir SingleCountryActivity
        val intent = Intent(context, SingleCountryActivity::class.java)
        startActivity(intent)
    }

}