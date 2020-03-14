package com.example.cardscores

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import com.example.cardscores.support.Game
import java.util.logging.Logger

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewGameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewGameFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    private val logger: Logger = Logger.getLogger(NewGameFragment::class.java.simpleName)

    private lateinit var gameSpinner: Spinner
    private lateinit var playerListViewGroup: ViewGroup
    private lateinit var addPlayerButton: View

//    private var param1: String? = null
//    private var param2: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    // TODO: display recommended number of decks (varies by player count)
    // TODO: fix placement of "Start Game" button when lots of players and keyboard is displayed
    // TODO: restore state if back button used

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_new_game, container, false)

        gameSpinner = view.findViewById(R.id.GameSelectionSpinner)
        playerListViewGroup = view.findViewById(R.id.PlayerListLayout)
        addPlayerButton = view.findViewById(R.id.AddPlayerButton)

        gameSpinner.onItemSelectedListener = this
        gameSpinner.adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, Game.values())

        updatePlayerListForGameSelection()

        addPlayerButton.setOnClickListener(this)
        view.findViewById<Button>(R.id.StartGameButton).setOnClickListener(this)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewGameFragment()
//        fun newInstance(param1: String, param2: String) =
//            NewGameFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        logger.info("Nothing selected from spinner")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        updatePlayerListForGameSelection()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.AddPlayerButton -> addPlayer()
                R.id.StartGameButton -> startGame()
                else -> {
                    throw IllegalArgumentException("Unhandled view ID provided: ${v.id}")
                }
            }
        }
    }

    private fun updatePlayerListForGameSelection() {
        val currentGame = gameSpinner.selectedItem as Game
        logger.info("Game: ${gameSpinner.selectedItem}, Current players: ${playerListViewGroup.childCount}, min/max: ${currentGame.minPlayers}/${currentGame.maxPlayers}")

        while (playerListViewGroup.childCount < currentGame.minPlayers) {
            addPlayer(false)
        }

        while (playerListViewGroup.childCount > currentGame.maxPlayers) {
            playerListViewGroup.removeViewAt(playerListViewGroup.childCount-1)
        }

        if (playerListViewGroup.childCount < currentGame.maxPlayers) {
            view?.findViewById<Button>(R.id.AddPlayerButton)?.visibility = View.VISIBLE
        }
    }

    private fun addPlayer() {
        addPlayer(true)
        setAddPlayerButtonVisiblity()
    }

    private fun addPlayer(canRemove: Boolean) {
        val newPlayerView : View = layoutInflater.inflate(R.layout.player_entry, null);
        val removeButton = newPlayerView.findViewById<Button>(R.id.RemovePlayerButton)
        if (! canRemove) {
            removeButton.visibility = View.GONE
        }
        removeButton.setOnClickListener {
            playerListViewGroup.removeView(newPlayerView)
            setAddPlayerButtonVisiblity()
        }
        playerListViewGroup.addView(newPlayerView)
    }

    private fun setAddPlayerButtonVisiblity() {
        val currentGame = gameSpinner.selectedItem as Game
        if (playerListViewGroup.childCount >= currentGame.maxPlayers) {
            addPlayerButton.visibility = View.INVISIBLE
        }
        else {
            addPlayerButton.visibility = View.VISIBLE
        }
    }

    private fun startGame() {
        var canProceed = true
        for (child in playerListViewGroup.children) {
            if (child.findViewById<EditText>(R.id.PlayerName).text.toString() == "") {
                canProceed = false
                Toast.makeText(requireContext(),
                    "Please enter all of the players names before starting a game.",
                        Toast.LENGTH_SHORT)
                    .show()
            }
        }

        if (canProceed) {
            logger.info("Do some navigation here")
        }
    }
}
