package sheridan.jawedzak.autoedu.userProfile

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.emailInput
import kotlinx.android.synthetic.main.activity_login.passwordInput
import kotlinx.android.synthetic.main.activity_login.progressBar
import kotlinx.android.synthetic.main.activity_registration.*
import sheridan.jawedzak.autoedu.MainActivity
import sheridan.jawedzak.autoedu.R

class LoginActivity : AppCompatActivity() {

    //get firebase authentication
    lateinit var auth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser

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
            if (TextUtils.isEmpty(emailInput.text.toString())) {
                emailInput.setError("Please enter email")
                emailInput.requestFocus()
                return@setOnClickListener
            }
            //empty info - tell user to enter password
            else if (TextUtils.isEmpty(passwordInput.text.toString())) {
                passwordInput.setError("Please enter password")
                passwordInput.requestFocus()
                return@setOnClickListener
            } else if (passwordInput.length() > 6) {
                passwordInput.setError("Mininmum password length is 6 characters")
                passwordInput.requestFocus()
            } else {
                progressBar.isVisible = true
                //firebase login info authentication - get user sign in, email, password approved
                auth.signInWithEmailAndPassword(
                    emailInput.text.toString(),
                    passwordInput.text.toString()
                )
                    .addOnCompleteListener {
                        //user login success
                        if (it.isSuccessful) {
                            val user = auth.currentUser

                            if (user != null) {
                                if (user.isEmailVerified){
                                    //go to home page
                                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                    finish()
                                }
                                else {
                                    user.sendEmailVerification()
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Check your email to verify your account!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    progressBar.isVisible = false
                                }
                            }
                            //user login unsuccessful
                        } else {
                            //stay in login page and tell user to try again
                            Toast.makeText(
                                this@LoginActivity,
                                "Login failed, please try again!",
                                Toast.LENGTH_LONG
                            ).show()
                            progressBar.isVisible = false
                        }
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

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}