package sheridan.jawedzak.autoedu.dashLightSymbols

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import sheridan.jawedzak.autoedu.MapActivity
import sheridan.jawedzak.autoedu.R


class SymbolFix  : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.symbol_fix)

        val actionbar = supportActionBar
        actionbar!!.title = intent.getStringExtra("name")
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        //symbol information
        var name = intent.getStringExtra("name")
        var trigger = intent.getStringExtra("trigger")
        var solution = intent.getStringExtra("solution")
        var video = intent.getStringExtra("video")

        //youtube video
        val youTubePlayer = findViewById<YouTubePlayerView>(R.id.symbol_video)
        val mapBtn = findViewById<Button>(R.id.btn_map)

        lifecycle.addObserver(youTubePlayer)


        youTubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = video
                if (videoId != null) {
                    youTubePlayer.loadVideo(videoId, 0F)
                }
            }
        })

        mapBtn.setOnClickListener {
            val mapIntent = Intent(this, MapActivity::class.java)
            startActivity(mapIntent)
        }


        //count number of steps
        var steps = intent.getSerializableExtra("steps") as HashMap<String, String>
        var stepsString: String = ""
        var stepNumber: Int = 1

        //retrieve number of steps for each symbol
        for ((k, v) in steps.toSortedMap()) {
            stepsString += "Step ${stepNumber}. ${v}\n\n"
            stepNumber++
        }
        //label solution
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
