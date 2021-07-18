package pe.edu.ulima.pm.trabajofinal.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import pe.edu.ulima.pm.trabajofinal.R
import pe.edu.ulima.pm.trabajofinal.objects.FirstTime.isFirstTime
import pe.edu.ulima.pm.trabajofinal.objects.GlobalDataInfo
import pe.edu.ulima.pm.trabajofinal.objects.PremiumSingleCountryStats
import pe.edu.ulima.pm.trabajofinal.objects.SingleCountryStats

class SingleCountryPiechartFragment: Fragment() {

    private lateinit var tviDateCountry: TextView
    private lateinit var tviTotalConfirmedCountry: TextView
    private lateinit var tviTotalDeathsCountry: TextView
    private lateinit var tviTotalRecoveredCountry: TextView
    private lateinit var tviTotalActiveCasesCountry: TextView

    private var sc = PremiumSingleCountryStats.country //Singleton que contiene stats del pais seleccionado

    //Para configurar el PieChart
    private var dataList: ArrayList<PieEntry> = ArrayList()
    private var colors: ArrayList<Int> = ArrayList()
    private lateinit var pchSingleCountry: PieChart

    object Test {
        var isFirstTime = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single_country_piechart, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pchSingleCountry = view.findViewById(R.id.pchSingleCountry)

        setPieChart()

        //Llamando a los TextView de fragment_global_info
        tviDateCountry = view.findViewById(R.id.tviDateCountry)
        tviTotalConfirmedCountry = view.findViewById(R.id.tviTotalConfirmedCountry)
        tviTotalDeathsCountry = view.findViewById(R.id.tviTotalDeathsCountry)
        tviTotalRecoveredCountry = view.findViewById(R.id.tviTotalRecoveredCountry)
        tviTotalActiveCasesCountry = view.findViewById(R.id.tviTotalActiveCasesCountry)

        // Seteando los TextView
        tviDateCountry.text = "Last updated: ${sc!!.Date}"
        tviTotalConfirmedCountry.text = "Total cases: ${sc!!.TotalCases}"
        tviTotalDeathsCountry.text = "Total deaths: ${sc!!.TotalDeaths}"
        tviTotalRecoveredCountry.text = "Total cases per million: ${sc!!.TotalCasesPerMillion}"
        tviTotalActiveCasesCountry.text = "Total deaths per million: ${sc!!.TotalDeathsPerMillion}"

    }

    private fun setPieChart() {

        val pieDataSet = PieDataSet(getList(), "")
        val pieData = PieData(pieDataSet)

        colors.add(ColorTemplate.MATERIAL_COLORS[1])
        colors.add(ColorTemplate.MATERIAL_COLORS[2])

        pieDataSet.colors = colors
        pieData.setValueTextSize(10f)
        //pieData.setValueFormatter(PercentFormatter(pchSingleCountry))
        pchSingleCountry.animateXY(2000,2000)
        pchSingleCountry.data = pieData
        pchSingleCountry.description.text=""
        //pchSingleCountry.setUsePercentValues(true)
        pchSingleCountry.setCenterTextSize(50f)
        pchSingleCountry.invalidate()
    }

    private fun getList() : ArrayList<PieEntry>{

        if (Test.isFirstTime == 1) {
            dataList.add(0, PieEntry(sc!!.TotalCasesPerMillion.toFloat(), "Cases/million"))
            dataList.add(1, PieEntry(sc!!.TotalDeathsPerMillion.toFloat(), "Deaths/Million"))
            //dataList.add(2, PieEntry(sc!!.TotalDeaths.toFloat(),"Total Deaths"))
        }
        Test.isFirstTime = 0

        return dataList
    }
}