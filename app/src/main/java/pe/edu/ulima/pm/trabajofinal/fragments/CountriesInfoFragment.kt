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
import pe.edu.ulima.pm.trabajofinal.models.dao.premium.PremiumSingleCountryData
import pe.edu.ulima.pm.trabajofinal.objects.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountriesInfoFragment: Fragment(), OnCountryInfoItemClickListener {

    private var title: TextView? = null
    private var rviCompetitions: RecyclerView? = null

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

        Log.i("CountriesInfoFragment", CountriesList.toString())
        val countriesRVAdapter = CountriesInfoRVAdapter(PremiumGlobalDataInfo.premiumCountriesData!!, this, view.context)
        rviCompetitions!!.adapter = countriesRVAdapter

    }

    override fun onClick(country: PremiumSingleCountryData) {
        //Actualizando el Singleton con la info del pais seleccionado
        PremiumSingleCountryStats.country = country

        //Abrir SingleCountryActivity
        val intent = Intent(context, SingleCountryActivity::class.java)
        startActivity(intent)
    }

}