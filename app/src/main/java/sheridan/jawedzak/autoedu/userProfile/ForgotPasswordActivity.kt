package sheridan.jawedzak.autoedu.userProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.emailInput
import kotlinx.android.synthetic.main.fragment_feedback.*
import sheridan.jawedzak.autoedu.R

class ForgotPasswordActivity : AppCompatActivity() {

    //firebase authentication
    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        //reset password
        resetPassword()
    }

    //reset password method
    private fun resetPassword() {
        //forgot password button on click listener
        pswdResetButton.setOnClickListener {

            //empty info - tell user input first name
            if (TextUtils.isEmpty(emailInput.text.toString())) {
                emailInput.setError("Please enter email")
                emailInput.requestFocus()
                return@setOnClickListener
            }
            else if (Patterns.EMAIL_ADDRESS.matcher(emailInput.toString()).matches()) {
                emailInput.setError("Please enter valid email")
                emailInput.requestFocus()
            }
        }
    }
}