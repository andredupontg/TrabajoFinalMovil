package pe.edu.ulima.pm.trabajofinal.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.trabajofinal.R
import pe.edu.ulima.pm.trabajofinal.models.Country

interface OnCountryInfoItemClickListener {
    fun onClick(country: Country)
}

class CountriesInfoRVAdapter: RecyclerView.Adapter<CountriesInfoRVAdapter.ViewHolder> {

    class ViewHolder: RecyclerView.ViewHolder {
        var tviCountryName: TextView? = null
        var tviCountryInfo: TextView? = null

        constructor(view: View) : super(view) {
            tviCountryName = view.findViewById(R.id.tviCountryName)
            tviCountryInfo = view.findViewById(R.id.tviCountryInfo)
        }
    }

    private var countries: ArrayList<Country>? = null
    private var listener: OnCountryInfoItemClickListener? = null
    private var context: Context? = null

    constructor(countries : ArrayList<Country>,
                listener: OnCountryInfoItemClickListener,
                context: Context) : super() {
        this.countries = countries
        this.listener = listener
        this.context = context }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_country_info, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries!![position]

        //Se muestran los datos de la competicion en el RecyclerView
        holder.tviCountryName!!.text = country.name
        holder.tviCountryInfo!!.text = country.info.toString()

        holder.itemView.setOnClickListener {
            listener!!.onClick(countries!![position])
        }
    }

    override fun getItemCount(): Int {
        return countries!!.size
    }
}