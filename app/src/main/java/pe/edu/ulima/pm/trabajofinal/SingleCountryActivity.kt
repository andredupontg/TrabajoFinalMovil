package pe.edu.ulima.pm.trabajofinal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import pe.edu.ulima.pm.trabajofinal.fragments.SingleCountryGraphFragment
import pe.edu.ulima.pm.trabajofinal.fragments.SingleCountryPiechartFragment
import pe.edu.ulima.pm.trabajofinal.objects.SingleCountryStats

class SingleCountryActivity: AppCompatActivity() {

    private var tviTitulo: TextView? = null

    private var toolbar: androidx.appcompat.widget.Toolbar? = null
    private var fragments: ArrayList<Fragment> = ArrayList()
    private var dlaSingleCountry: DrawerLayout? = null
    private var nviSingleCountry: NavigationView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_country)

        tviTitulo = findViewById(R.id.tviSingleCountryTitle)
        tviTitulo!!.text = "Stats for ${SingleCountryStats.country!!.name}"

        fragments.add(SingleCountryPiechartFragment())
        fragments.add(SingleCountryGraphFragment())

        //Seteando el toolbar
        toolbar = findViewById(R.id.tbaMain)
        setSupportActionBar(toolbar)

        toolbar!!.setNavigationOnClickListener{
            Log.i("MainActivity","Click en el icono de navegacion")
        }

        //Seteando el NVI
        nviSingleCountry =findViewById(R.id.nviSingleCountry)
        dlaSingleCountry =findViewById(R.id.dlaSingleCountry)

        //Se configura un listener en la barra de navegacion para que cambie de Fragment segun se solicite
        nviSingleCountry!!.setNavigationItemSelectedListener { item: MenuItem ->
            item.isChecked = true
            val ft = supportFragmentManager.beginTransaction()

            if (item.itemId == R.id.mnuPiechart) {
                // Abrir SingleCountryPiechartFragment
                ft.replace(R.id.flaSingleCountry, fragments[0])
                tviTitulo!!.text = "Stats for ${SingleCountryStats.country!!.name}"

            }else if (item.itemId == R.id.mnuGraph) {
                // Abrir SingleCountryGraphFragment
                ft.replace(R.id.flaSingleCountry, fragments[1])
                tviTitulo!!.text = "Progress graph for ${SingleCountryStats.country!!.name}"
            }

            ft.addToBackStack(null)
            ft.commit()
            dlaSingleCountry!!.closeDrawers()
            true
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaSingleCountry, fragments[0])
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
            R.id.mnuHome -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            //Click en el icono de lista de paises
            R.id.mnuCountriesList -> {
                val intent = Intent(this, CountriesListActivity::class.java)
                startActivity(intent)
            }
            //Click en el icono de ranking de paises
            R.id.mnuCountriesRanking -> {
                TODO()
            }
        }
        return false
    }
}