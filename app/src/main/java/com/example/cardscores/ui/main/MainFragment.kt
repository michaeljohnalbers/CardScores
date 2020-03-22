package com.example.cardscores.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.cardscores.R
import java.util.logging.Logger

class MainFragment : Fragment(), View.OnClickListener {

    private val logger : Logger = Logger.getLogger(MainFragment::class.java.name)

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        setClickListeners(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.NewGameButton -> newGame()
                R.id.ContinueGameButton -> continueGame()
                R.id.ViewSavedGamesButton -> viewSavedGames()
                else -> {
                    throw IllegalArgumentException("Unhandled view ID provided: ${view.id}")
                }
            }
        }
    }

    private fun setClickListeners(view: View) {
        val buttonIds = listOf(R.id.NewGameButton, R.id.ContinueGameButton,
            R.id.ViewSavedGamesButton)
        for (buttonId in buttonIds) {
            val button : Button = view.findViewById(buttonId) as Button
            button.setOnClickListener(this)
        }
    }

    private fun newGame() {
        logger.info("New Game")
        val action = MainFragmentDirections.actionMainFragmentToNewGameFragment()
        val navController : NavController = Navigation.findNavController(activity as FragmentActivity, R.id.nav_host_fragment)
        logger.info("navigation controller: $navController")
        navController.navigate(action)
        //navController.navigate(R.id.newGameFragment)
    }

    // TODO: geotag (optional) + timestamp game
    private fun continueGame() {
        Toast.makeText(context, "Continue Game coming soon!", Toast.LENGTH_SHORT).show()
    }

    private fun viewSavedGames() {
        Toast.makeText(context, "View Saved Games coming soon!", Toast.LENGTH_SHORT).show()
    }
}
