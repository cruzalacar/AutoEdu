package sheridan.jawedzak.autoedu


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import sheridan.jawedzak.autoedu.dashLightSymbols.SymbolActivity


class OilPressureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dash_info)

        //tutorial button intent
        val tutorialBtn = findViewById<Button>(R.id.button_tutorial)
        tutorialBtn.setOnClickListener{
            startActivity(Intent(this@OilPressureActivity, TutorialActivity::class.java))
        }

        //back button
        val backBtn = findViewById<Button>(R.id.button_back)
        backBtn.setOnClickListener{
            startActivity(Intent(this@OilPressureActivity, SymbolActivity::class.java))
        }


    }
}
