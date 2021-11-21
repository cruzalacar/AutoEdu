package sheridan.jawedzak.autoedu.dashLightSymbols

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import sheridan.jawedzak.autoedu.R

class TutorialSymbolAdapter(var list:ArrayList<DatabaseModel>, private val onSymbolClickListener: OnSymbolClickListener): RecyclerView.Adapter<TutorialSymbolAdapter.ViewHolder>() {

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        //initialize variables
        var name = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //view holder for home symbol layout
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tutorial_layout_home, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //list of names, icons, and position
        var label = "${list[position].name} tutorial"
        holder.name.text = label

        //retrieve symbols
        holder.itemView.setOnClickListener{
            onSymbolClickListener.onShowTutorial(position)
        }
    }

    //retrieve list of items
    override fun getItemCount(): Int {
        return list.size
    }
}