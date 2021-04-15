package sheridan.jawedzak.autoedu.bottomNavbar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_search.*
import sheridan.jawedzak.autoedu.R
import sheridan.jawedzak.autoedu.chatBot.MessagingAdapter
import sheridan.jawedzak.autoedu.dashLightSymbols.DataAdapter
import sheridan.jawedzak.autoedu.dashLightSymbols.DatabaseModel
import sheridan.jawedzak.autoedu.dashLightSymbols.OnSymbolClickListener
import sheridan.jawedzak.autoedu.dashLightSymbols.SymbolDetail


class SearchFragment : Fragment(), OnSymbolClickListener {

    var list = ArrayList<DatabaseModel>()

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var adapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Symbols")


        getData()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onSymbolItemClicked(position: Int) {
        //Toast.makeText(this, list[position].name, Toast.LENGTH_LONG).show()

        var intent = Intent(activity, SymbolDetail::class.java)
        intent.putExtra("name", list[position].name)
        intent.putExtra("trigger", list[position].trigger)
        intent.putExtra("description", list[position].description)
        intent.putExtra("solution", list[position].solution)
        intent.putExtra("icon", list[position].icon)
        startActivity(intent)
    }


     private fun getData(){

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("cancel", p0.toString())
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                    var model = data.getValue(DatabaseModel::class.java)
                    list.add(model as DatabaseModel)
                }
                if (list.size > 0) {
                    adapter = DataAdapter(list, this@SearchFragment)
                    recyclerview.adapter = adapter
                    recyclerview.layoutManager = LinearLayoutManager(activity)


                    search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            search_bar.clearFocus()
                            for (x in list) {
                                if (x.name == p0) {
                                    Toast.makeText(
                                        activity,
                                        "Found",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
//                                    else {
//                                        Toast.makeText(
//                                            applicationContext,
//                                            list.get(0).toString(),
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
                            }
                            return false
                        }

                        override fun onQueryTextChange(p0: String?): Boolean {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "QUERY",
//                                    Toast.LENGTH_SHORT
//                                ).show()

                            return false
                        }
                    })
                }

            }
        })
    }
}