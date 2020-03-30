package com.michaelalbers.cardscores

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.michaelalbers.cardscores.model.v1.NewGame
import com.michaelalbers.cardscores.support.Game
import com.michaelalbers.cardscores.support.PlayerList
import com.michaelalbers.cardscores.viewmodel.v1.NewGameViewModel
import java.util.logging.Logger

/**
 * A simple [Fragment] subclass.
 * Use the [NewGameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewGameFragment : Fragment() {

    private val logger: Logger = Logger.getLogger(NewGameFragment::class.java.simpleName)

    private lateinit var gameSpinner: Spinner
    private lateinit var playerListViewGroup: ViewGroup
    private lateinit var addPlayerButton: View
    private lateinit var newGameViewModel: NewGameViewModel


    // TODO: display recommended number of decks (varies by player count)
    // TODO: fix placement of "Start Game" button when lots of players and keyboard is displayed
    // TODO: restore state if back button used

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_new_game, container, false)

        newGameViewModel = ViewModelProviders.of(this).get(NewGameViewModel::class.java)

        newGameViewModel.getNewGame().observe(this, Observer {newGame ->
            updatePlayerList(newGame)
        })

        gameSpinner = view.findViewById(R.id.GameSelectionSpinner)
        playerListViewGroup = view.findViewById(R.id.PlayerListLayout)
        addPlayerButton = view.findViewById(R.id.AddPlayerButton)

        gameSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newGameViewModel.setGame(parent?.getItemAtPosition(position) as Game)
            }
        }

        gameSpinner.adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, Game.values())

        addPlayerButton.setOnClickListener { newGameViewModel.addPlayer() }
        view.findViewById<Button>(R.id.StartGameButton).setOnClickListener { startGame() }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewGameFragment()
    }

    /**
     * Update the player widgets. This function has to handle a lot of cases:
     * * Drawing the initial set of widgets
     * * Screen rotation
     * * Correctly updating player's names
     * * Adding/removing players
     * * Changing games
     * * Maybe more
     *
     * That's a long way of saying, if you think you can remove a bit of code, give it a
     * thorough testing first.
     */
    private fun updatePlayerList(newGame: NewGame) {
        for ((ii, player) in newGame.players.withIndex()) {
            var playerNameEditText: EditText

            if (playerListViewGroup.childCount <= ii) {
                val newPlayerView : View = layoutInflater.inflate(R.layout.player_entry, null);
                playerNameEditText = newPlayerView.findViewById(R.id.PlayerName)
                playerNameEditText.addTextChangedListener(object : TextWatcher{
                    override fun afterTextChanged(s: Editable?) {
                        if (s != null) {
                            newGameViewModel.setPlayerName(ii, s.toString())
                        }
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })

                val removeButton = newPlayerView.findViewById<Button>(R.id.RemovePlayerButton)
                if (ii < newGame.game.minPlayers) {
                    removeButton.visibility = View.GONE
                }
                removeButton.setOnClickListener { newGameViewModel.removePlayer(ii) }

                playerListViewGroup.addView(newPlayerView)
            }
            else {
                playerNameEditText = playerListViewGroup.getChildAt(ii).findViewById(R.id.PlayerName)
            }

            // Without this check the cursor was being set at the beginning of the text entry
            if (playerNameEditText.text.toString() != player) {
                playerNameEditText.setText(player)
            }
        }

        if (newGame.canAddNewPlayer()) {
            addPlayerButton.visibility = View.VISIBLE
        }
        else {
            addPlayerButton.visibility = View.INVISIBLE
        }

        while (playerListViewGroup.childCount > newGame.players.size) {
            playerListViewGroup.removeViewAt(playerListViewGroup.childCount-1)
        }
    }

    private fun startGame() {
        val newGame = newGameViewModel.getNewGame().value ?: return

        var canProceed = true
        val playerList = PlayerList()
        for (player in newGame.players) {
            if (player == "") {
                canProceed = false
                Toast.makeText(requireContext(),
                    "Please enter all of the players' names before starting a game.",
                    Toast.LENGTH_SHORT
                ).show()
                break
            }
            else {
                playerList.addPlayer(player)
            }
        }

        if (canProceed) {
            val navController : NavController = Navigation.findNavController(activity as FragmentActivity, R.id.nav_host_fragment)

            when (newGame.game) {
                Game._3_14 -> {
                    val action = NewGameFragmentDirections.actionNewGameFragmentTo314Game(playerList)
                    navController.navigate(action)
                }
                Game.GinRummy -> {
                    val action = NewGameFragmentDirections.actionNewGameFragmentToGinRummyGame(playerList)
                    navController.navigate(action)
                }
                Game.ShanghaiRummy -> {
                    val action = NewGameFragmentDirections.actionNewGameFragmentToShanghaiRummyGame(playerList)
                    navController.navigate(action)
                }
                else -> {
                    throw IllegalStateException("Unhandled game in start game: ${newGame.game}.")
                }
            }
        }
    }
}
