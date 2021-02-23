package sheridan.jawedzak.autoedu


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class OilPressureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dash_info)

        //tutorial button intent
        val tutorialBtn = findViewById<Button>(R.id.button_tutorial)
        tutorialBtn.setOnClickListener{
            startActivity(Intent(this@OilPressureActivity, TutorialActivity::class.java))
        }


    }
}
