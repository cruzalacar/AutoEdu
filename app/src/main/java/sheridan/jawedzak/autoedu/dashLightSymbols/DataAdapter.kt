package sheridan.jawedzak.autoedu.dashLightSymbols

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import sheridan.jawedzak.autoedu.R

class DataAdapter(var list:ArrayList<DatabaseModel>): RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        var name = itemView.findViewById<TextView>(R.id.name)
        //var solution = itemView.findViewById<TextView>(R.id.solution)
        var icon = itemView.findViewById<ImageView>(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        //holder.solution.text = list[position].solution

        Picasso.get().load(list[position].icon).into(holder.icon)

    }

    override fun getItemCount(): Int {
        return list.size
    }

}