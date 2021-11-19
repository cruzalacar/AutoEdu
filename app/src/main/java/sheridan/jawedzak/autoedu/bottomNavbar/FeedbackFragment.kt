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

        //submit button listener and error checking for missing input
        btn_submit.setOnClickListener{

            //empty input - tell user to enter name
            if (TextUtils.isEmpty(name.text.toString())){
                name.setError("Please enter your name")
                return@setOnClickListener
            }
            //empty input - tell user to enter email
            else if (TextUtils.isEmpty(email.text.toString())){
                email.setError("Please enter your email")
                return@setOnClickListener
            }
            //empty input - tell user to enter feedback
            else if (TextUtils.isEmpty(feedback.text.toString())) {
                feedback.setError("Please enter your feedback!")
                return@setOnClickListener
            }
            //empty input - tell user text should be minimum 10 letters long
            else if (feedback.text.toString().length < 10){
                feedback.setError("minimum 10 letters required!")
                return@setOnClickListener
            }
            else {
                //retrieve strings/user info from database
                reference = FirebaseDatabase.getInstance().getReference("Users")
                //get user's name, email, and feedback
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
                    ).show() //show toast message

                    //error message and could not submit
                }.addOnFailureListener{
                    Toast.makeText(
                            activity,
                            "could not submit",
                            Toast.LENGTH_SHORT
                    ).show() //show toast message
                }
            }
        }
    }

    //symbol clicked
    override fun onSymbolItemClicked(position: Int) {
    }
}
