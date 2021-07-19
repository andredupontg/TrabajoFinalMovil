package pe.edu.ulima.pm.trabajofinal.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import pe.edu.ulima.pm.trabajofinal.R
import pe.edu.ulima.pm.trabajofinal.models.dao.CountryHistoricalData
import pe.edu.ulima.pm.trabajofinal.objects.InternetConnection
import pe.edu.ulima.pm.trabajofinal.objects.SingleCountryHistoricalStats

class SingleCountryGraphFragment: Fragment() {

    private var tviChartInfo: TextView? = null
    private var lineChart: LineChart? = null
    lateinit var countryList : ArrayList<Entry>
    private val limitedList: ArrayList<CountryHistoricalData> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single_country_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tviChartInfo = view.findViewById(R.id.tviChartInfo)

        if (InternetConnection.isConnected) {
            getLastTenItems()

            lineChart = view.findViewById(R.id.lineChart)
            countryList = ArrayList()

            val lineDataSet = LineDataSet(getList(), "Total cases")
            lineDataSet.setDrawFilled(true)
            val lineData = LineData(lineDataSet)

            lineChart!!.data = lineData
            lineChart!!.animateXY(1000, 1000)
            lineChart!!.isDragEnabled = true
            lineChart!!.setScaleEnabled(true)
            lineChart!!.setDrawGridBackground(false)
            lineChart!!.description.isEnabled = false
            lineChart!!.invalidate()
        }
        else {
            tviChartInfo!!.text = "Internet connection is needed to show this graph."
        }
    }

    private fun getList(): ArrayList<Entry>{
        for (i in 1..limitedList.size) {
            countryList.add(Entry(i.toFloat(), limitedList[i-1].Confirmed.toFloat()))
        }
        return  countryList
    }
    private fun getLastTenItems() {
        val list = SingleCountryHistoricalStats.countryHistoricalData
        Log.i("SingleCountryGraph", SingleCountryHistoricalStats.countryHistoricalData.toString())

        for (i in 0..list!!.size-1) {
            if (i >= list.size-20) {
                limitedList.add(list[i])
            }
        }
    }
}