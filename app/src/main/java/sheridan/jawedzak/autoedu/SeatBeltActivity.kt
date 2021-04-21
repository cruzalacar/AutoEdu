package sheridan.jawedzak.autoedu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SeatBeltActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seatbelt)

        val actionbar = supportActionBar
        actionbar!!.title = intent.getStringExtra("Seatbelt Light")
    }
}