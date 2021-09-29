package sheridan.jawedzak.autoedu.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import sheridan.jawedzak.autoedu.MainActivity
import sheridan.jawedzak.autoedu.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //initialize image
        val lod = findViewById<ImageView>(R.id.iv_note)

        //retrieve and animate image
        lod.alpha = 0f
        lod.animate().setDuration(1500).alpha(1f).withEndAction {

            //open main activity
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)

            //fade in and out image
            // testing comment
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}