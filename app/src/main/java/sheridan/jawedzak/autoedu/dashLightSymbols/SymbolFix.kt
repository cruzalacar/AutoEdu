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

class SymbolFix  : YouTubeBaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.symbol_fix)

        //initializerPlayer()
        initializerPlayer(getYoutubeVideoIdFromUrl("https://www.youtube.com/watch?v=tEdtUFCRLbo")!!)

          //action bar
//        val actionbar = supportActionBar
//        actionbar!!.title = intent.getStringExtra("name")
//        actionbar.setDisplayHomeAsUpEnabled(true)
//        actionbar.setDisplayHomeAsUpEnabled(true)

        //symbol information
        var video = intent.getStringExtra("video")
        var name = intent.getStringExtra("name")
//        var icon = intent.getStringExtra("icon")
//        var description = intent.getStringExtra("description")
//        var trigger = intent.getStringExtra("trigger")
//        var solution = intent.getStringExtra("solution")

        //initialize labels
//        val lblDescription = findViewById<TextView>(R.id.description)
//        val lblTrigger = findViewById<TextView>(R.id.trigger)
//        val lblSolution = findViewById<TextView>(R.id.solution)
//        val img = findViewById<ImageView>(R.id.img)

        //label for each symbol information
//        Picasso.get().load(icon).into(img)
//        lblDescription.text = description
//        lblTrigger.text = trigger
//        lblSolution.text = solution
    }

    private fun initializerPlayer(videoId: String) {
        youtubePlayer.initialize(getString(R.string.api_key), object: YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubePlayer?,
                    p2: Boolean
            ) {
                p1!!.loadVideo(videoId)
                p1.play()
            }

            override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getYoutubeVideoIdFromUrl(inUrl: String): String?{
        if(inUrl.toLowerCase().contains("youtu.be")){
            return inUrl.substring(inUrl.lastIndexOf("/") + 1)
        }
        val pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(inUrl)
        return if (matcher.find()){
            matcher.group()
        }else null
    }


    //    private fun initializerPlayer() {
//
//        youtubePlayer.initialize(getString(R.string.api_key), object: YouTubePlayer.OnInitializedListener {
//            override fun onInitializationSuccess(
//                    p0: YouTubePlayer.Provider?,
//                    p1: YouTubePlayer?,
//                    p2: Boolean
//            ) {
//                var video = intent.getStringExtra("https://www.youtube.com/watch?v=tEdtUFCRLbo")
//                p1!!.loadVideo(video)
//                p1.play()
//            }
//
//            override fun onInitializationFailure(
//                    p0: YouTubePlayer.Provider?,
//                    p1: YouTubeInitializationResult?
//            ) {
//                Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
//            }
//        })
//    }







    //BACK BUTTON
    //back button navigation
//    override fun onSupportNavigateUp(): Boolean {
//        //go back to previous page
//        onBackPressed()
//        return true
//    }


}

