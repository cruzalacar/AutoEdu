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

        val lblName = findViewById<TextView>(R.id.name)
        lblName.text = name
    }


}
