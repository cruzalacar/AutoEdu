package sheridan.jawedzak.autoedu.bottomNavbar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_search.*
import sheridan.jawedzak.autoedu.R
import sheridan.jawedzak.autoedu.dashLightSymbols.DataAdapter
import sheridan.jawedzak.autoedu.dashLightSymbols.DatabaseModel
import sheridan.jawedzak.autoedu.dashLightSymbols.OnSymbolClickListener
import sheridan.jawedzak.autoedu.dashLightSymbols.SymbolDetail


class SearchFragment : Fragment(), OnSymbolClickListener {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
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

    //Search View filter
    private fun searchResult(query: String) {
        val q = query.toLowerCase()
        val updatedList: MutableList<DatabaseModel> = ArrayList()
        for (modelContacts in list) {
            if (modelContacts.name.toLowerCase().contains(q)) {
                updatedList.add(modelContacts)
            }
        }
        adapter = DataAdapter(updatedList as ArrayList<DatabaseModel>, this@SearchFragment)
        recyclerview.adapter = adapter
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
                    adapter = DataAdapter(list, this@SearchFragment)
                    recyclerview.adapter = adapter
                    recyclerview.layoutManager = LinearLayoutManager(activity)

                    //search bar listener
                    search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            search_bar.clearFocus()
                            for (x in list) {
                                //activate when name/symbol is found
                                if (x.name == p0) {
                                    Toast.makeText(
                                        activity,
                                        "Symbol Found",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            //otherwise, code not found
                            return false
                        }

                        //boolean used to test name found
                        override fun onQueryTextChange(p0: String?): Boolean {
                            if (p0 != null) {
                                searchResult(p0)
                            }
                            return true
                        }
                    })
                }

            }
        })
    }
}