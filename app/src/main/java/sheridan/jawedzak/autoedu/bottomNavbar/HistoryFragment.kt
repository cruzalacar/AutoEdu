package sheridan.jawedzak.autoedu.bottomNavbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_history.*
import sheridan.jawedzak.autoedu.R
import sheridan.jawedzak.autoedu.dashLightSymbols.OnSymbolClickListener

class HistoryFragment : Fragment(), OnSymbolClickListener {

    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_submit.setOnClickListener{

            val editname = name.text.toString()
            val editemail = email.text.toString()
            val editfeedback = feedback.text.toString()

            reference = FirebaseDatabase.getInstance().getReference("Users")
            val User = User(editname, editemail, editfeedback)
            reference.child(editname).setValue(User).addOnSuccessListener {

                //clear fields
                name.text.clear()
                email.text.clear()
                feedback.text.clear()

                //show success message
                Toast.makeText(
                    activity,
                    "Submitted",
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener{
                Toast.makeText(
                    activity,
                    "could not submit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onSymbolItemClicked(position: Int) {
    }

}