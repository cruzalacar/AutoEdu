package sheridan.jawedzak.autoedu.dashLightSymbols

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import sheridan.jawedzak.autoedu.R

class SymbolDetail  : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.symbol_detail)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Dashboard"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        //symbol information
        var name = intent.getStringExtra("name")
        var icon = intent.getStringExtra("icon")
        var description = intent.getStringExtra("description")
        var trigger = intent.getStringExtra("trigger")
        var solution = intent.getStringExtra("solution")

        //initialize labels
        val lblDescription = findViewById<TextView>(R.id.description)
        val lblTrigger = findViewById<TextView>(R.id.trigger)
        val lblSolution = findViewById<TextView>(R.id.solution)
        val img = findViewById<ImageView>(R.id.img)

        //label for each symbol information
        Picasso.get().load(icon).into(img)
        lblDescription.text = description
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
