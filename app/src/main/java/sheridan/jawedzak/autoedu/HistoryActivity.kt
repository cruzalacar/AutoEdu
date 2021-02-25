package sheridan.jawedzak.autoedu


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history)

        //back button
        val backBtn = findViewById<Button>(R.id.button_back)
        backBtn.setOnClickListener{
            startActivity(Intent(this@HistoryActivity, MainActivity::class.java))
        }

    }
}
