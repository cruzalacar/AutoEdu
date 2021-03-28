package sheridan.jawedzak.autoedu.dashLightSymbols

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.symbol_detail.*
import sheridan.jawedzak.autoedu.R

class SymbolDetail  : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.symbol_detail)

        var name = intent.getStringExtra("name")
        var icon = intent.getStringExtra("icon")
        var description = intent.getStringExtra("description")
        var trigger = intent.getStringExtra("trigger")
        var solution = intent.getStringExtra("solution")


        val lblName = findViewById<TextView>(R.id.name)
        val lblDescription = findViewById<TextView>(R.id.description)
        val lblTrigger = findViewById<TextView>(R.id.trigger)
        val lblSolution = findViewById<TextView>(R.id.solution)

        lblName.text = name
        lblDescription.text = description
        lblTrigger.text = trigger
        lblSolution.text = solution
    }


}
