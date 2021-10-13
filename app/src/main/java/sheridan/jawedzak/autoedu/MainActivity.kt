package sheridan.jawedzak.autoedu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import sheridan.jawedzak.autoedu.bottomNavbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize fragments
        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val assistantFragment = AssistantFragment()
        val feedbackFragment = FeedbackFragment()
        val accountFragment = AccountFragment()

        //current fragment is home/dashboard
        makeCurrentFragement(homeFragment)

        //bottom navigator on click (home,search,help,history,account)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home -> makeCurrentFragement(homeFragment)
                R.id.ic_search -> makeCurrentFragement(searchFragment)
                R.id.ic_live_help -> makeCurrentFragement(assistantFragment)
                R.id.ic_history -> makeCurrentFragement(feedbackFragment)
                R.id.ic_account -> makeCurrentFragement(accountFragment)
            }
            true
        }
    }

    //wrap fragment to being transaction
    private fun makeCurrentFragement(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}
