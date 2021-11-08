package sheridan.jawedzak.autoedu.userProfile

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        //get firebase info
        auth = FirebaseAuth.getInstance()

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
            else {
                auth.sendPasswordResetEmail(emailInput.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "Check your email to reset your password!",
                                Toast.LENGTH_LONG
                            ).show() //show toast message
                            finish()
                        }
                        else {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "Something went wrong, Please try again!",
                                Toast.LENGTH_LONG
                            ).show() //show toast message
                    }
                }
            }
        }
    }
}