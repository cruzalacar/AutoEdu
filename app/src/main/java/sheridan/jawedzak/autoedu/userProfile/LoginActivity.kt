package sheridan.jawedzak.autoedu.userProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import sheridan.jawedzak.autoedu.MainActivity
import sheridan.jawedzak.autoedu.R

class LoginActivity : AppCompatActivity() {

    //get firebase authentication
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //user info from firebase
        auth = FirebaseAuth.getInstance()

        //get current user login from database
        val currentuser = auth.currentUser

        //if there is no current user logged in, go to login activity
        if(currentuser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
        //user logged in
        login()
    }

    private fun login() {
        //login button on click listener and error checking
        loginButton.setOnClickListener {

            //empty info - tell user to enter email
            if(TextUtils.isEmpty(emailInput.text.toString())){
                emailInput.setError("Please enter email")
                emailInput.requestFocus()
                return@setOnClickListener
            }
            //empty info - tell user to enter password
            else if(TextUtils.isEmpty(passwordInput.text.toString())){
                passwordInput.setError("Please enter password")
                passwordInput.requestFocus()
                return@setOnClickListener
            }

            //firebase login info authentication - get user sign in, email, password approved
            auth.signInWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                .addOnCompleteListener {
                    //user login success
                    if(it.isSuccessful) {
                        //go to home page
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()

                    //user login unsuccessful
                    } else {
                        //stay in login page and tell user to try again
                        Toast.makeText(this@LoginActivity, "Login failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }
        }

        //user registration button/create account
        registerText.setOnClickListener{
            //go to registration page for user to sign up
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))

        }

        //Forgot/reset password for exisiting account
        forgotPswdText.setOnClickListener{
            //go to registration page for user to sign up
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))

        }
    }
}