package pe.edu.ulima.pm.trabajofinal

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
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
import pe.edu.ulima.pm.trabajofinal.models.AppDatabase
import pe.edu.ulima.pm.trabajofinal.models.dao.*
import pe.edu.ulima.pm.trabajofinal.models.dao.premium.PremiumGlobalData
import pe.edu.ulima.pm.trabajofinal.models.dao.premium.PremiumSingleCountryData
import pe.edu.ulima.pm.trabajofinal.models.persistence.dao.CountryDAO
import pe.edu.ulima.pm.trabajofinal.models.persistence.dao.DateDAO
import pe.edu.ulima.pm.trabajofinal.models.persistence.dao.GlobalDAO
import pe.edu.ulima.pm.trabajofinal.models.persistence.entities.CountryEntity
import pe.edu.ulima.pm.trabajofinal.models.persistence.entities.DateEntity
import pe.edu.ulima.pm.trabajofinal.models.persistence.entities.GlobalEntity
import pe.edu.ulima.pm.trabajofinal.objects.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Listo para trabajar

class MainActivity : AppCompatActivity() {

    private var toolbar: androidx.appcompat.widget.Toolbar? = null
    private var fragments: ArrayList<Fragment> = ArrayList()
    private var globalData: GlobalData? = null //Datos globales de covid + lista de paises con info completa
    private var countriesDataList: ArrayList<SingleCountryData> = ArrayList() //Lista de paises con info completa
    private var premiumCountriesDataList: ArrayList<PremiumSingleCountryData> = ArrayList() //Lista de paises con info premium completa

    //Para instanciar los DAO
    private var countryDAO: CountryDAO? = null
    private var globalDAO: GlobalDAO? = null
    private var dateDAO: DateDAO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.add(SynchronizeFragment())
        fragments.add(GlobalInfoFragment())

        //Llamando a los DAO de las 3 entidades
        countryDAO = AppDatabase.getInstance(this).countryDAO
        globalDAO = AppDatabase.getInstance(this).globalDAO
        dateDAO = AppDatabase.getInstance(this).dateDAO

        //Si hay conexion a internet, se recibe data actualizada del API
        if (verifyAvailableNetwork(this)) {
            searchGlobalData()
            searchPremiumGlobalData()

        //Si no hay internet, se carga data de Room
        } else {
            lifecycleScope.launch {
                InternetConnection.isConnected = false
                getDataFromRoom()
            }
        }

        lifecycleScope.launch {
            var data: List<CountryEntity>? = null
            data = countryDAO!!.getAllCountries()
            Log.i("MainActivity", data.toString())
        }

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

    // Cargar activity de acurdo a la opcion seleccionada
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

    // Configurar la conexion al API
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.covid19api.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    // Obtener datos de https://api.covid19api.com/summary
    private fun searchGlobalData() {

            lifecycleScope.launch {
                val call =
                    getRetrofit().create(CovidAPIService::class.java).getGlobalData("/summary")

                // Si hay conexion con el API
                if (call.isSuccessful) {

                    globalData = call.body()
                    GlobalDataInfo.globalData = call.body()
                    Log.i("globalData", GlobalDataInfo.globalData.toString())

                    setCountriesData(GlobalDataInfo.globalData!!)
                    GlobalDataInfo.countriesData = countriesDataList
                    Log.i("countriesData", GlobalDataInfo.countriesData.toString())

                } else {
                    Log.i("MainActivity", "No se pudo conectar al API")
                }
            }
    }

    // Obtener datos de https://api.covid19api.com/premium/summary
    private fun searchPremiumGlobalData() {

        lifecycleScope.launch {
            val call = getRetrofit().create(CovidAPIService::class.java).getPremiumGlobalData("/premium/summary")

            // Si se devuelve data
            if (call.isSuccessful) {
                PremiumGlobalDataInfo.premiumGlobalData = call.body()

                setPremiumCountriesData(PremiumGlobalDataInfo.premiumGlobalData!!)
                PremiumGlobalDataInfo.premiumCountriesData = premiumCountriesDataList

                //Insertar los paises en la BD
                PremiumGlobalDataInfo.premiumCountriesData!!.forEach {
                    val country = setCountryEntity(it)
                    countryDAO!!.insertCountry(country)
                }

                //Insertar los datos globales en la BD
                val global = setGlobalEntity(GlobalDataInfo.globalData!!.Global)
                globalDAO!!.insertGlobal(global)

                //Insertar la fecha de actualizacion en la BD
                val date = DateEntity(0,PremiumGlobalDataInfo.premiumGlobalData!!.Date)
                dateDAO!!.insertDate(date)

            }else {
                Log.i("MainActivity", "No se pudo conectar al API")
            }
        }
    }

    private fun setCountriesData(globalData: GlobalData) {
        for (i in globalData.Countries) {
            countriesDataList.add(i)
        }
    }

    private fun setPremiumCountriesData(premiumGlobalData: PremiumGlobalData) {
        for (i in premiumGlobalData.Countries) {
            premiumCountriesDataList.add(i)
        }
    }

    private fun setCountryEntity( country: PremiumSingleCountryData): CountryEntity {

        val ce = CountryEntity("", "","","","",0,0,0,0,0.0,0.0,0.0,0.0,0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0)
        ce.ID = country.ID
        ce.CountryISO = country.CountryISO
        ce.Country = country.Country
        ce.Continent = country.Continent
        ce.Date = country.Date
        ce.TotalCases = country.TotalCases
        ce.NewCases = country.NewCases
        ce.TotalDeaths = country.TotalDeaths
        ce.TotalCasesPerMillion = country.TotalCasesPerMillion
        ce.NewCasesPerMillion = country.NewCasesPerMillion
        ce.TotalDeathsPerMillion = country.TotalDeathsPerMillion
        ce.NewDeathsPerMillion = country.NewDeathsPerMillion
        ce.StringencyIndex = country.StringencyIndex
        ce.DailyIncidenceConfirmedCases = country.DailyIncidenceConfirmedCases
        ce.DailyIncidenceConfirmedDeaths = country.DailyIncidenceConfirmedDeaths
        ce.DailyIncidenceConfirmedCasesPerMillion = country.DailyIncidenceConfirmedCasesPerMillion
        ce.DailyIncidenceConfirmedDeathsPerMillion = country.DailyIncidenceConfirmedDeathsPerMillion
        ce.IncidenceRiskConfirmedPerHundredThousand = country.IncidenceRiskConfirmedPerHundredThousand
        ce.IncidenceRiskDeathsPerHundredThousand = country.IncidenceRiskDeathsPerHundredThousand
        ce.IncidenceRiskNewConfirmedPerHundredThousand = country.IncidenceRiskNewConfirmedPerHundredThousand
        ce.IncidenceRiskNewDeathsPerHundredThousand = country.IncidenceRiskNewDeathsPerHundredThousand
        ce.CaseFatalityRatio = country.CaseFatalityRatio

        return ce
    }

    private fun setGlobalEntity(global: Global): GlobalEntity {
        val ge = GlobalEntity(0,0,0,0,0,0,0,"")
        ge.NewConfirmed = global.NewConfirmed
        ge.TotalConfirmed = global.TotalConfirmed
        ge.NewDeaths = global.NewDeaths
        ge.TotalDeaths = global.TotalDeaths
        ge.NewRecovered = global.NewRecovered
        ge.TotalRecovered = global.TotalRecovered
        ge.Date = global.Date

        return ge
    }

    // Obtener data de room y guardarla en Singletons
    private suspend fun getDataFromRoom () {

        val countries: ArrayList<PremiumSingleCountryData> = ArrayList()
        val global = Global(0,0,0,0,0,0,"")
        val date = Date(0,"")

        val countryEntity = ArrayList(countryDAO!!.getAllCountries())
        for (i in 1..countryEntity.size) {
            countries.add(PremiumSingleCountryData(
                countryEntity[i-1].ID,
                countryEntity[i-1].CountryISO,
                countryEntity[i-1].Country,
                countryEntity[i-1].Continent,
                countryEntity[i-1].Date,
                countryEntity[i-1].TotalCases,
                countryEntity[i-1].NewCases,
                countryEntity[i-1].TotalDeaths,
                countryEntity[i-1].NewDeaths,
                countryEntity[i-1].TotalCasesPerMillion,
                countryEntity[i-1].NewCasesPerMillion,
                countryEntity[i-1].TotalDeathsPerMillion,
                countryEntity[i-1].NewDeathsPerMillion,
                countryEntity[i-1].StringencyIndex,
                countryEntity[i-1].DailyIncidenceConfirmedCases,
                countryEntity[i-1].DailyIncidenceConfirmedDeaths,
                countryEntity[i-1].DailyIncidenceConfirmedCasesPerMillion,
                countryEntity[i-1].DailyIncidenceConfirmedDeathsPerMillion,
                countryEntity[i-1].IncidenceRiskConfirmedPerHundredThousand,
                countryEntity[i-1].IncidenceRiskDeathsPerHundredThousand,
                countryEntity[i-1].IncidenceRiskNewConfirmedPerHundredThousand,
                countryEntity[i-1].IncidenceRiskNewDeathsPerHundredThousand,
                countryEntity[i-1].CaseFatalityRatio,
            ))
        }

        val globalEntity = ArrayList(globalDAO!!.getAllGlobal())
        val g = globalEntity[globalEntity.size-1]

        global.TotalRecovered = g.TotalRecovered
        global.Date = g.Date
        global.TotalDeaths = g.TotalDeaths
        global.TotalConfirmed = g.TotalConfirmed
        global.NewConfirmed = g.NewConfirmed
        global.NewDeaths = g.NewDeaths
        global.NewRecovered = g.NewRecovered

        Log.i("Mainnn", global.toString())

        val dateEntity = ArrayList(dateDAO!!.getAllDates())
        val d = dateEntity[dateEntity.size-1]

        date.ID = d.ID
        date.Date = d.Date

        // Anadir resultados a los Singleton
        PremiumGlobalDataInfo.premiumCountriesData = countries
        Log.i("asda", PremiumGlobalDataInfo.premiumCountriesData.toString())
        GlobalDataInfo.globalData?.Global = global
        PremiumGlobalDataInfo.premiumGlobalData?.Date= date.Date
    }

    //Para verificar la conexion a internet
    private fun verifyAvailableNetwork(activity:AppCompatActivity):Boolean{
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

}