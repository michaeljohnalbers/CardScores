package com.michaelalbers.cardscores

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.michaelalbers.cardscores.model.v1.GinRummy
import com.michaelalbers.cardscores.support.Game
import com.michaelalbers.cardscores.viewmodel.v1.GinRummyViewModel
import kotlin.math.max

/**
 * A simple [Fragment] subclass.
 * Use the [GinRummyGame.newInstance] factory method to
 * create an instance of this fragment.
 */
class GinRummyGame : Fragment() {
    private val args: GinRummyGameArgs by navArgs()

    private lateinit var ginRummyViewModel: GinRummyViewModel
    private val gameTableLayouts = mutableListOf<TableLayout>()

    // TODO: need a consolidated approach to accessing LiveData model
    //  Option 1: always call .value.accessor
    //  Option 2: add wapper calls in ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gin_rummy_game, container, false)

        view.findViewById<TextView>(R.id.TitleTextView).text = Game.GinRummy.readableName

        val gamesLayout = view.findViewById<LinearLayout>(R.id.GinRummyGamesLayout)

        for (gameNum in 0 until GinRummy.NUM_GAMES) {
            val gameLayout = TableLayout(context)
            gamesLayout.addView(gameLayout)

            val headerRow = TableRow(context)
            gameLayout.addView(headerRow)

            gameTableLayouts.add(gameLayout)
        }

        ginRummyViewModel = ViewModelProviders.of(this).get(GinRummyViewModel::class.java)

        ginRummyViewModel.getGinRummy().observe(this, Observer {ginRummy ->
            updateUI(ginRummy)
        })

        ginRummyViewModel.setPlayers(args.PlayerList)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment GinRummyGame.
         */
        @JvmStatic
        fun newInstance() = GinRummyGame()
    }

    private fun updateUI(ginRummy: GinRummy) {
        for (gameNum in 0 until GinRummy.NUM_GAMES) {

            val gameTableLayout = gameTableLayouts[gameNum]
            val headerRow = gameTableLayout.getChildAt(0) as TableRow

            val game = ginRummy.getGame(gameNum)
            var maxWins = -1

            for (playerNum in 0 until GinRummy.NUM_PLAYERS) {
                if (null == headerRow.getChildAt(playerNum)) {
                    val playerNameTextView = TextView(context)
                    playerNameTextView.text = ginRummy.getPlayer(playerNum)
                    headerRow.addView(playerNameTextView)
                }
                maxWins = max(maxWins, game.getPlayerHandScores(playerNum).size)
            }

            var rowNum = 0
            val isGameFinished = game.isGameFinished()
            do {
                var tableRow = gameTableLayout.getChildAt(rowNum + 1) as? TableRow
                if (null == tableRow) {
                    tableRow = TableRow(context)
                    gameTableLayout.addView(tableRow)
                }

                for (playerNum in 0 until GinRummy.NUM_PLAYERS) {
                    if (playerNum <= tableRow.size) {
                        if (isGameFinished) {
                            tableRow.addView(TextView(context))
                        }
                        else {
                            val handScoreEditText = EditText(context)
                            handScoreEditText.inputType =
                                (InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_CLASS_NUMBER)
                            tableRow.addView(handScoreEditText)
                        }
                    }

                    if (isGameFinished) {
                        val handScoreEditText = tableRow[playerNum] as EditText
                        if (rowNum < game.getPlayerHandScores(playerNum).size) {
                            handScoreEditText.setText(game.getPlayerHandScores(playerNum)[rowNum].toString())
                            // TODO: add callback to set score at index
                        } else {
                            handScoreEditText.setText("")
                            // TODO: add callback to append new score
                        }
                    }
                    else {
                        val handScoreTextView = tableRow[playerNum] as TextView
                        if (rowNum < game.getPlayerHandScores(playerNum).size) {
                            handScoreTextView.setText(game.getPlayerHandScores(playerNum)[rowNum].toString())
                        } else {
                            handScoreTextView.setText("") // TODO: set hand payout, make it red italics, too
                        }
                    }
                }
            }
            while (++rowNum < maxWins)

            if (isGameFinished) {
                // TODO: extra row, for winner shows 'x' for winning, for loser, shows money owed
            }
        }
    }
}
