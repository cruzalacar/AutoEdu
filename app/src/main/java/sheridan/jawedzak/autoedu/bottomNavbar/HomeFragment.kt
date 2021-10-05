package sheridan.jawedzak.autoedu.bottomNavbar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import sheridan.jawedzak.autoedu.CameraActivity
import sheridan.jawedzak.autoedu.R
import sheridan.jawedzak.autoedu.dashLightSymbols.*


class HomeFragment : Fragment(), OnSymbolClickListener {

    //array list database
    var list = ArrayList<DatabaseModel>()

    //initialize variable
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var adapter: HomeSymbolAdapter

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Activate camera button when clicked
        ic_camera.setOnClickListener{
            val intent = Intent(activity, CameraActivity::class.java)
            startActivity(intent)
        }
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
                    adapter = HomeSymbolAdapter(list, this@HomeFragment)
                    home_recyclerview.adapter = adapter
                    home_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                }
            }
        })
    }

    //recycler view to display top 10 indicators
    private fun getCommonIndicators() {

    }

    //recycler view to display top 5 tutorials
    private fun getCommonTutorials() {

    }


}