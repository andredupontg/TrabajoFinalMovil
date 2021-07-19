package pe.edu.ulima.pm.trabajofinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import pe.edu.ulima.pm.trabajofinal.fragments.CountriesInfoFragment
import pe.edu.ulima.pm.trabajofinal.fragments.SingleCountryPiechartFragment

class CountriesListActivity: AppCompatActivity() {

    private var toolbar: androidx.appcompat.widget.Toolbar? = null

    private var fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries_list)

        //Seteando el toolbar
        toolbar = findViewById(R.id.tbaMain)
        setSupportActionBar(toolbar)

        toolbar!!.setNavigationOnClickListener{
            Log.i("MainActivity","Click en el icono de navegacion")
        }

        fragments.add(CountriesInfoFragment())
        fragments.add(SingleCountryPiechartFragment())

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flaCountriesList, fragments[0])
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
                val intent = Intent(this, CountriesRankActivity::class.java)
                startActivity(intent)
            }
        }
        return false
    }
}