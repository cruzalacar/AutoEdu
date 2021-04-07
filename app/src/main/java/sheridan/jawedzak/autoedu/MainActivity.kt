package sheridan.jawedzak.autoedu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import sheridan.jawedzak.autoedu.bottomNavbar.*
import sheridan.jawedzak.autoedu.chatBot.BotActivity
import sheridan.jawedzak.autoedu.dashLightSymbols.SymbolActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //  setSupportActionBar(findViewById(R.id.toolbar))


        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val assistantFragment = AssistantFragment()
        val historyFragment = HistoryFragment()
        val accountFragment = AccountFragment()

        makeCurrentFragement(homeFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home -> makeCurrentFragement(homeFragment)
                R.id.ic_search -> makeCurrentFragement(searchFragment)
                R.id.ic_live_help -> makeCurrentFragement(assistantFragment)
                R.id.ic_history -> makeCurrentFragement(historyFragment)
                R.id.ic_account -> makeCurrentFragement(accountFragment)

            }
            true
        }

    }

    private fun makeCurrentFragement(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }


}
