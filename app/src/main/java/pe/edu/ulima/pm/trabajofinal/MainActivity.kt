package pe.edu.ulima.pm.trabajofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import pe.edu.ulima.pm.trabajofinal.fragments.CountriesInfoFragment
import pe.edu.ulima.pm.trabajofinal.fragments.GlobalInfoFragment
import pe.edu.ulima.pm.trabajofinal.fragments.SynchronizeFragment
import pe.edu.ulima.pm.trabajofinal.objects.FirstTime


// Listo para trabajar

class MainActivity : AppCompatActivity() {

    private var toolbar: androidx.appcompat.widget.Toolbar? = null

    private var fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.add(SynchronizeFragment())
        fragments.add(GlobalInfoFragment())

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
}