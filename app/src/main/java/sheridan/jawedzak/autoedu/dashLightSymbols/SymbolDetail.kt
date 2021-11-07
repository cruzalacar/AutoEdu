package sheridan.jawedzak.autoedu.dashLightSymbols

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import sheridan.jawedzak.autoedu.R

class SymbolDetail  : AppCompatActivity(){

    //hash maps for steps, triggers and selections
    private lateinit var steps: HashMap<String, String>
    private lateinit var triggerSelections: List<String>
    private lateinit var triggers: HashMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.symbol_detail)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = intent.getStringExtra("name")
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        //symbol information
        var name = intent.getStringExtra("name")
        var icon = intent.getStringExtra("icon")
        var description = intent.getStringExtra("description")
        var solution = intent.getStringExtra("solution")
        triggers = intent.getSerializableExtra("trigger") as HashMap<String, String>
        steps = intent.getSerializableExtra("steps") as HashMap<String, String>
        var video = intent.getStringExtra("video")
        triggerSelections = triggers.keys.toList()

        //initialize labels
        val lblDescription = findViewById<TextView>(R.id.description)
        val triggerSpinner: Spinner = findViewById(R.id.triggerSpinner)
        val lblTriggerDesc = findViewById<TextView>(R.id.trigger_desc)
        val lblSolution = findViewById<TextView>(R.id.solution)
        val img = findViewById<ImageView>(R.id.img)

        // initialize spinner
        val triggerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, triggerSelections)
        triggerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        triggerSpinner.adapter = triggerAdapter
        val defaultTrigger: String = triggerSelections[0]

        //label for each symbol information
        Picasso.get().load(icon).into(img)
        lblDescription.text = description
        lblTriggerDesc.text = triggers[defaultTrigger]
        lblSolution.text = solution

        triggerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //retrieve trigger selection and symbol description
                var mapKey: String = triggerSelections[position]
                var triggerDesc = triggers[mapKey]
                val lblTriggerDesc = findViewById<TextView>(R.id.trigger_desc)
                lblTriggerDesc.text = triggerDesc
            }
        }

        //how to fix button on click listener
        val mechanicBtn = findViewById<Button>(R.id.btn_fix)
        mechanicBtn.setOnClickListener{
            //open steps activity
            var symbolFixIntent = Intent(this@SymbolDetail, SymbolFix::class.java)
            symbolFixIntent.putExtra("steps", steps)
            startActivity(symbolFixIntent)
        }
    }

    //back button navigation
    override fun onSupportNavigateUp(): Boolean {
        //go back to previous page
        onBackPressed()
        return true
    }
}
