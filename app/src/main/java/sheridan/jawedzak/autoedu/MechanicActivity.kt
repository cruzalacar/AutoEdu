package sheridan.jawedzak.autoedu


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MechanicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dash_maps)

        //back button
        val backBtn = findViewById<Button>(R.id.button_back)
        backBtn.setOnClickListener{
            startActivity(Intent(this@MechanicActivity, TutorialActivity::class.java))
        }
    }
}
