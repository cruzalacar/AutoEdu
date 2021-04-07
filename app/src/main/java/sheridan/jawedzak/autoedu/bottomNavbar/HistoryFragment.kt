package sheridan.jawedzak.autoedu.bottomNavbar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_search.*
import sheridan.jawedzak.autoedu.R
import sheridan.jawedzak.autoedu.dashLightSymbols.DataAdapter
import sheridan.jawedzak.autoedu.dashLightSymbols.DatabaseModel


class HistoryFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_history, container, false)
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
                    adapter = DataAdapter(list)
                    recyclerview.adapter = adapter
                    recyclerview.layoutManager = LinearLayoutManager(activity)
                }

            }
        })
    }
}