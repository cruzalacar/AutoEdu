package sheridan.jawedzak.autoedu


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SymbolActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.symbol_list)

        //oil pressure button intent
        val oilPressureBtn = findViewById<Button>(R.id.oil_pressure)
        oilPressureBtn.setOnClickListener{
            startActivity(Intent(this@SymbolActivity, OilPressureActivity::class.java))
        }

        //back button
        val backBtn = findViewById<Button>(R.id.button_back)
        backBtn.setOnClickListener{
            startActivity(Intent(this@SymbolActivity, MainActivity::class.java))
        }

    }
}
