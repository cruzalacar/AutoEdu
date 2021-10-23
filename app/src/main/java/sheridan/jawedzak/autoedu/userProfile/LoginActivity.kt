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

    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val currentuser = auth.currentUser
        if(currentuser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
        login()
    }

    private fun login() {

        loginButton.setOnClickListener {

            if(TextUtils.isEmpty(emailInput.text.toString())){
                emailInput.setError("Please enter email")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(passwordInput.text.toString())){
                emailInput.setError("Please enter password")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }

        }

        registerText.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))

        }
    }
}