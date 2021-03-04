package sheridan.jawedzak.autoedu.dashLightSymbols

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import sheridan.jawedzak.autoedu.R


class SymbolActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symbol_list)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Symbols")

        getData()

        //oil pressure button intent
//        val oilPressureBtn = findViewById<Button>(R.id.oil_pressure)
//        oilPressureBtn.setOnClickListener{
//            startActivity(Intent(this@SymbolActivity, OilPressureActivity::class.java))
//        }

        //back button
//        val backBtn = findViewById<Button>(R.id.button_back)
//        backBtn.setOnClickListener{
//            startActivity(Intent(this@SymbolActivity, MainActivity::class.java))
//        }
    }
        private fun getData(){

            reference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("cancel", p0.toString())
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var list = ArrayList<DatabaseModel>()
                    for (data in p0.children) {
                        var model = data.getValue(DatabaseModel::class.java)
                        list.add(model as DatabaseModel)
                    }
                    if (list.size > 0) {
                        val adapter = DataAdapter(list)
                        var f = findViewById<RecyclerView>(R.id.recyclerview)
                        f.adapter = adapter
                    }

                }
            })
        }

}
