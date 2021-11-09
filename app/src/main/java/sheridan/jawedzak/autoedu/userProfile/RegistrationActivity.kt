package sheridan.jawedzak.autoedu.userProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*
import sheridan.jawedzak.autoedu.R

class RegistrationActivity : AppCompatActivity() {
    //firebase authentication
    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //get firebase info
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        //create user account
        databaseReference = database?.reference!!.child("Profile")
        //register user
        register()
    }

    //register user method
    private fun register() {
        //register button on click listener
        registerButton.setOnClickListener {

            //empty info - tell user input first name
            if(TextUtils.isEmpty(firstnameInput.text.toString())) {
                firstnameInput.setError("Please enter first name ")
                firstnameInput.requestFocus()
                return@setOnClickListener

            //empty info - tell user to input last name
            } else if(TextUtils.isEmpty(lastnameInput.text.toString())) {
                lastnameInput.setError("Please enter last name ")
                lastnameInput.requestFocus()
                return@setOnClickListener

            //empty info - tell user to input email
            }else if(TextUtils.isEmpty(emailInput.text.toString())) {
                emailInput.setError("Please enter email ")
                emailInput.requestFocus()
                return@setOnClickListener

            //empty info - tell user to input password
            }else if(TextUtils.isEmpty(passwordInput.text.toString())) {
                passwordInput.setError("Please enter password ")
                passwordInput.requestFocus()
                return@setOnClickListener

            }else {
                //create authentication for user email and password
                progressBar.isVisible = true
                auth.createUserWithEmailAndPassword(
                    //get user email and password saved in database
                    emailInput.text.toString(),
                    passwordInput.text.toString()
                )
                    //check user completion
                    .addOnCompleteListener {
                        //registration success
                        if (it.isSuccessful) {
                            val currentUser = auth.currentUser
                            val currentUSerDb = databaseReference?.child((currentUser?.uid!!))
                            //user first name
                            currentUSerDb?.child("firstname")
                                ?.setValue(firstnameInput.text.toString())
                            //user last name
                            currentUSerDb?.child("lastname")
                                ?.setValue(lastnameInput.text.toString())

                            //tell user registration is complete
                            Toast.makeText(
                                this@RegistrationActivity,
                                "Registration Success. ",
                                Toast.LENGTH_LONG
                            ).show() //show toast message
                            progressBar.isVisible = false
                            finish()

                        //user registration is incomplete
                        } else {
                            //tell user to try again
                            Toast.makeText(
                                this@RegistrationActivity,
                                "Registration failed, please try again! ",
                                Toast.LENGTH_LONG
                            ).show() //show toast message
                            progressBar.isVisible = false

                        }
                    }
            }
        }
    }
}