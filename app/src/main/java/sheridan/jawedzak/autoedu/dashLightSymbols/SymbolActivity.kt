package sheridan.jawedzak.autoedu.dashLightSymbols

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import sheridan.jawedzak.autoedu.R


class SymbolActivity : AppCompatActivity() , OnSymbolClickListener{

    var list = ArrayList<DatabaseModel>()

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

    override fun onSymbolItemClicked(position: Int) {
        //Toast.makeText(this, list[position].name, Toast.LENGTH_LONG).show()

        var intent = Intent(this, SymbolDetail::class.java)
        intent.putExtra("name", list[position].name)
        startActivity(intent)
    }
        private fun getData(){

            reference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("cancel", p0.toString())
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (data in p0.children) {
                        var model = data.getValue(DatabaseModel::class.java)
                        list.add(model as DatabaseModel)
                    }
                    if (list.size > 0) {
                        val adapter = DataAdapter(list, this@SymbolActivity)
                        var f = findViewById<RecyclerView>(R.id.recyclerview)
                        f.adapter = adapter
                    }

                }
            })
        }

}
