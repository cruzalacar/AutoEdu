package sheridan.jawedzak.autoedu.dashLightSymbols

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import sheridan.jawedzak.autoedu.R

class HomeSymbolAdapter(var list:ArrayList<DatabaseModel>, private val onSymbolClickListener: OnSymbolClickListener): RecyclerView.Adapter<HomeSymbolAdapter.ViewHolder>() {

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        //initialize variables
        var name = itemView.findViewById<TextView>(R.id.name)
        var icon = itemView.findViewById<ImageView>(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //view holder for home symbol layout
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.symbol_layout_home, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //list of names, icons, and position
        holder.name.text = list[position].name
        Picasso.get().load(list[position].icon).into(holder.icon)

        //retrieve symbols
        holder.itemView.setOnClickListener{
            onSymbolClickListener.onSymbolItemClicked(position)
        }
    }

    //retrieve list of items
    override fun getItemCount(): Int {
        return list.size
    }
}