package sheridan.jawedzak.autoedu.bottomNavbar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import sheridan.jawedzak.autoedu.CameraActivity
import sheridan.jawedzak.autoedu.R
import sheridan.jawedzak.autoedu.dashLightSymbols.DatabaseModel


class HomeFragment : Fragment() {

    var list = ArrayList<DatabaseModel>()

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            database = FirebaseDatabase.getInstance()
            reference = database.getReference("Symbols")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ic_camera.setOnClickListener{
            val intent = Intent(activity, CameraActivity::class.java)
            startActivity(intent)
        }

    }

    //recycler view to display top 10 indicators
    private fun getCommonIndicators() {

    }

    //recycler view to display top 5 tutorials
    private fun getCommonTutorials() {

    }


}