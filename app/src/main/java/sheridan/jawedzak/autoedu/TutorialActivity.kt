package sheridan.jawedzak.autoedu


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import sheridan.jawedzak.autoedu.bottomNavbar.HomeFragment

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dash_tutorial)

        //mechanic button intent
        val mechanicBtn = findViewById<Button>(R.id.btn_mechanic)
        mechanicBtn.setOnClickListener{
            startActivity(Intent(this@TutorialActivity, MechanicActivity::class.java))
        }

        //back button
        val backBtn = findViewById<Button>(R.id.button_back)
        backBtn.setOnClickListener{
            startActivity(Intent(this@TutorialActivity, HomeFragment::class.java))
        }
    }
}
