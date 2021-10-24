package sheridan.jawedzak.autoedu.dashLightSymbols

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import sheridan.jawedzak.autoedu.R
import java.util.*
import kotlin.collections.HashMap

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
        var steps = intent.getSerializableExtra("steps") as HashMap<String, String>
//        var steps = intent.get

        var stepsString: String = ""
        var stepNumber: Int = 1

        for ((k, v) in steps.toSortedMap()) {
            stepsString += "Step ${stepNumber}. ${v}\n\n"
            stepNumber++
        }


        val lblSolution = findViewById<TextView>(R.id.solution)

        //label for each symbol information
        lblSolution.text = stepsString
    }

    //back button navigation
    override fun onSupportNavigateUp(): Boolean {
        //go back to previous page
        onBackPressed()
        return true
    }
}
