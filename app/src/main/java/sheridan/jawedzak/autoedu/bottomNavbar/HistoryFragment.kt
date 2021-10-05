package sheridan.jawedzak.autoedu.bottomNavbar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_history.*
import sheridan.jawedzak.autoedu.R
import sheridan.jawedzak.autoedu.dashLightSymbols.DataAdapter
import sheridan.jawedzak.autoedu.dashLightSymbols.DatabaseModel
import sheridan.jawedzak.autoedu.dashLightSymbols.OnSymbolClickListener
import sheridan.jawedzak.autoedu.dashLightSymbols.SymbolDetail


class HistoryFragment : Fragment(), OnSymbolClickListener {

    //array list database
    var list = ArrayList<DatabaseModel>()

    //initialize variables
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var adapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialize database and symbols
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Symbols")

        //retrieve data
        getData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onSymbolItemClicked(position: Int) {
        //retrieving symbol information
        var intent = Intent(activity, SymbolDetail::class.java)
        intent.putExtra("name", list[position].name)
        intent.putExtra("trigger", list[position].trigger)
        intent.putExtra("description", list[position].description)
        intent.putExtra("solution", list[position].solution)
        intent.putExtra("icon", list[position].icon)
        startActivity(intent)
    }

    private fun getData(){
        //pulling data from firebase
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("cancel", p0.toString())
            }

            //calling recycler view to access symbols
            override fun onDataChange(p0: DataSnapshot) {
                list.clear()
                for (data in p0.children) {
                    var model = data.getValue(DatabaseModel::class.java)
                    list.add(model as DatabaseModel)
                }

                //list of symbols
                if (list.size > 0) {
                    adapter = DataAdapter(list, this@HistoryFragment)
                    history_recyclerview.adapter = adapter
                    history_recyclerview.layoutManager = LinearLayoutManager(activity)
                }
            }
        })
    }
}