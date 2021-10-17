package sheridan.jawedzak.autoedu.dashLightSymbols

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.symbol_fix.*
import sheridan.jawedzak.autoedu.R
import java.util.regex.Pattern

class SymbolFix  : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.symbol_fix)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = intent.getStringExtra("name")
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        //symbol information
        var name = intent.getStringExtra("name")
        var trigger = intent.getStringExtra("trigger")
        var solution = intent.getStringExtra("solution")

        //initialize labels
        val lblTrigger = findViewById<TextView>(R.id.trigger)
        val lblSolution = findViewById<TextView>(R.id.solution)

        //label for each symbol information
        lblTrigger.text = trigger
        lblSolution.text = solution
    }

    //back button navigation
    override fun onSupportNavigateUp(): Boolean {
        //go back to previous page
        onBackPressed()
        return true
    }
}
