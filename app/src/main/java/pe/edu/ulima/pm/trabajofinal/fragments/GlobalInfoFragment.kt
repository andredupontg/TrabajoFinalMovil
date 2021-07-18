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
import com.github.mikephil.charting.utils.ColorTemplate
import pe.edu.ulima.pm.trabajofinal.R
import pe.edu.ulima.pm.trabajofinal.objects.GlobalDataInfo

class GlobalInfoFragment: Fragment() {

    private lateinit var tviDateGlobal: TextView
    private lateinit var tviTotalConfirmedGlobal: TextView
    private lateinit var tviTotalDeathsGlobal: TextView
    private lateinit var tviTotalRecoveredGlobal: TextView
    private lateinit var tviTotalActiveCasesGlobal: TextView

    private var gd = GlobalDataInfo.globalData?.Global
    private val totalActiveCasesGlobal = 5000 /*gd.TotalConfirmed - gd.TotalDeaths - gd.TotalRecovered*/

    //Para configurar el PieChart
    private var dataList: ArrayList<PieEntry> = ArrayList()
    private var colors: ArrayList<Int> = ArrayList()
    private lateinit var pchGlobalData: PieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_global_info_piechart, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pchGlobalData = view.findViewById(R.id.pchGlobalData)

        setPieChart()

        //Llamando a los TextView de fragment_global_info
        tviDateGlobal = view.findViewById(R.id.tviDateGlobal)
        tviTotalConfirmedGlobal = view.findViewById(R.id.tviTotalConfirmedGlobal)
        tviTotalDeathsGlobal = view.findViewById(R.id.tviTotalDeathsGlobal)
        tviTotalRecoveredGlobal = view.findViewById(R.id.tviTotalRecoveredGlobal)
        tviTotalActiveCasesGlobal = view.findViewById(R.id.tviTotalActiveCasesGlobal)

        // Seteando los TextView
        tviDateGlobal.text = "Last updated: ${gd?.Date}"
        tviTotalConfirmedGlobal.text = "Total confirmed cases: ${gd?.TotalConfirmed}"
        tviTotalDeathsGlobal.text = "Total deaths: ${gd?.TotalDeaths}"
        tviTotalRecoveredGlobal.text = "Total recovered: ${gd?.TotalRecovered}"
        tviTotalActiveCasesGlobal.text = "Total active cases: $totalActiveCasesGlobal"
    }

    private fun setPieChart() {

        val pieDataSet = PieDataSet(getList(), "")
        val pieData = PieData(pieDataSet)

        for (i in ColorTemplate.COLORFUL_COLORS) {
            colors.add(i)
        }

        for (i in ColorTemplate.COLORFUL_COLORS) {
            colors.add(i)
        }

        pieDataSet.colors = colors
        pchGlobalData.animateXY(2000,2000)
        pchGlobalData.data = pieData

    }

    private fun getList() : ArrayList<PieEntry>{

        dataList.add(0, PieEntry(10000f,"Recovered"))
        dataList.add(1, PieEntry(2000f,"Active"))
        dataList.add(2, PieEntry(1000f,"Deaths"))

        return dataList
    }
}

/*dataList.add(0, PieEntry(gd?.TotalRecovered!!.toFloat(),"Total Recovered"))
dataList.add(1, PieEntry(totalActiveCasesGlobal.toFloat(),"Total Active"))
dataList.add(2, PieEntry(gd?.TotalDeaths!!.toFloat(),"Total Deaths"))*/