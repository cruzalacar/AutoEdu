package sheridan.jawedzak.autoedu.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import sheridan.jawedzak.autoedu.R

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DashboardFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_dashlights).setOnClickListener {
            findNavController().navigate(R.id.action_DashboardFragment_to_DashLightSymbolsFragment)
        }

        view.findViewById<Button>(R.id.button_history).setOnClickListener{
            findNavController().navigate(R.id.action_DashboardFragment_to_DashLightSymbolsFragment)
        }

    }

//            val historyBtn = findViewById<Button>(R.id.button_history)
//
//        historyBtn.setOnClickListener{
//            startActivity(Intent (this@MainActivity, MainActivity::class.java))
//        }

}