package sheridan.jawedzak.autoedu.bottomNavbar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_account.*
import sheridan.jawedzak.autoedu.R
import sheridan.jawedzak.autoedu.dashLightSymbols.OnSymbolClickListener
import sheridan.jawedzak.autoedu.userProfile.LoginActivity

class ProfileFragment : Fragment(), OnSymbolClickListener {

    //get data info from firebase
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get firebase data
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.getReference("Profile")

        //retrieve data
        loadProfile()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //log out button
        btnlogout.setOnClickListener {
            auth.signOut()
            //user logs out and goes back to login activity
            val myIntent = Intent(activity, LoginActivity::class.java)
            //open activity
            startActivity(myIntent)
        }
    }

    private fun loadProfile() {

        //get current user info from database
        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //get user's first and last nice
                lblName.text = snapshot.child("firstname").value.toString() + ", " + snapshot.child("lastname").value.toString()
                //get user;s email
                lblEmail.text = user?.email
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onSymbolItemClicked(position: Int) {
    }

}