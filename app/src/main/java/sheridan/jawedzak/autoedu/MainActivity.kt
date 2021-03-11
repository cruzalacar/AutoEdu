package sheridan.jawedzak.autoedu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import sheridan.jawedzak.autoedu.chatBot.BotActivity
import sheridan.jawedzak.autoedu.dashLightSymbols.SymbolActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //  setSupportActionBar(findViewById(R.id.toolbar))


        //history button intent
        val historyBtn = findViewById<Button>(R.id.button_history)
        historyBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
        }

        //chat button intent
        val chatBtn = findViewById<Button>(R.id.button_chat)
        chatBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity, BotActivity::class.java))
        }

        //symbol button intent
        val symbolListBtn = findViewById<Button>(R.id.button_dashlights)
        symbolListBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity, SymbolActivity::class.java))
        }

        //camera button intent
        val cameraBtn = findViewById<Button>(R.id.button_camera)
        cameraBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity, CameraActivity::class.java))
        }






    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
