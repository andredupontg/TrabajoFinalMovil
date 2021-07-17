package pe.edu.ulima.pm.trabajofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pe.edu.ulima.pm.trabajofinal.fragments.GlobalInfoFragment
import pe.edu.ulima.pm.trabajofinal.fragments.SynchronizeFragment
import pe.edu.ulima.pm.trabajofinal.models.dao.CountriesService
import pe.edu.ulima.pm.trabajofinal.models.dao.GlobalData
import pe.edu.ulima.pm.trabajofinal.models.dao.SingleCountryData
import pe.edu.ulima.pm.trabajofinal.objects.CountriesList
import pe.edu.ulima.pm.trabajofinal.objects.FirstTime
import pe.edu.ulima.pm.trabajofinal.objects.GlobalDataInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Listo para trabajar

class MainActivity : AppCompatActivity() {

    private var toolbar: androidx.appcompat.widget.Toolbar? = null
    private var fragments: ArrayList<Fragment> = ArrayList()
    private var globalData: GlobalData? = null //Datos globales de covid + lista de paises con info completa
    private var countriesDataList: ArrayList<SingleCountryData> = ArrayList() //Lista de paises con info completa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.add(SynchronizeFragment())
        fragments.add(GlobalInfoFragment())

        searchGlobalData()
        searchCountries()

        //Seteando el toolbar
        toolbar = findViewById(R.id.tbaMain)
        setSupportActionBar(toolbar)

        toolbar!!.setNavigationOnClickListener{
            Log.i("MainActivity","Click en el icono de navegacion")
        }

        //Asignando al fragment SynchronizeFragment
        val ft = supportFragmentManager.beginTransaction()
        if (FirstTime.isFirstTime == 1) {
            ft.replace(R.id.flaMain, fragments[0])
            FirstTime.isFirstTime = 2
        }
        else {
            ft.replace(R.id.flaMain, fragments[1])
        }
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemview = item.itemId
        when (itemview) {
            //Click en el icono Home
            R.id.mnuHome -> Log.i("MainActivity", "Click en Home")

            //Click en el icono de lista de paises
            R.id.mnuCountriesList -> {
                val intent = Intent(this, CountriesListActivity::class.java)
                startActivity(intent)
            }
            //Click en el icono de ranking de paises
            R.id.mnuCountriesRanking -> {
                val intent = Intent(this, CountriesRankActivity::class.java)
                startActivity(intent)
            }
        }
        return false
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.covid19api.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun searchCountries() /*ArrayList<CountryData>*/ {
        lifecycleScope.launch {
            val call = getRetrofit().create(CountriesService::class.java).getCountries("/countries")

            if (call.isSuccessful) {
                CountriesList.list = call.body()
                Log.i("MainActivity", CountriesList.list.toString())

            } else {
                Log.i("CountriesInfoFragment", "ArrayList vacio")
            }
        }
        Log.i("After lifecycleScope",CountriesList.list.toString())
    }

    private fun searchGlobalData() {
        lifecycleScope.launch {
            val call = getRetrofit().create(CountriesService::class.java).getGlobalData("/summary")

            if (call.isSuccessful) {
                globalData = call.body()
                GlobalDataInfo.globalData = call.body()
                Log.i("globalData", call.body().toString())
                Log.i("globalData", GlobalDataInfo.globalData.toString())

                setCountriesData(GlobalDataInfo.globalData!!)
                GlobalDataInfo.countriesData = countriesDataList
                Log.i("countriesData", GlobalDataInfo.countriesData.toString())

            }else {
                Log.i("MainActivity", "ArrayList vacio")
            }
        }
    }

    private fun setCountriesData(globalData: GlobalData) {
        for (i in globalData.Countries) {
            countriesDataList.add(i)
        }
    }

}