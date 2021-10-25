package sheridan.jawedzak.autoedu.bottomNavbar

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.fragment_feedback.*
import sheridan.jawedzak.autoedu.R
import sheridan.jawedzak.autoedu.dashLightSymbols.OnSymbolClickListener
import sheridan.jawedzak.autoedu.feedback.UserFeedback

class FeedbackFragment : Fragment(), OnSymbolClickListener {

    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_submit.setOnClickListener{

            if (TextUtils.isEmpty(name.text.toString())){
                name.setError("Please enter your name")
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(email.text.toString())){
                email.setError("Please enter your email")
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(feedback.text.toString())) {
                feedback.setError("Please enter your feedback!")
                return@setOnClickListener
            }
            else if (feedback.text.toString().length < 10){
                feedback.setError("minimum 10 letters required!")
                return@setOnClickListener
            }
            else {
                reference = FirebaseDatabase.getInstance().getReference("Users")
                val User = UserFeedback(name.text.toString(), email.text.toString(), feedback.text.toString())
                reference.child(name.text.toString()).setValue(User).addOnSuccessListener {

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
    }

    override fun onSymbolItemClicked(position: Int) {
    }
}
