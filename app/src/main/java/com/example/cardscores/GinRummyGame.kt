package com.example.cardscores

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.navigation.fragment.navArgs
import com.example.cardscores.support.Game

/**
 * A simple [Fragment] subclass.
 * Use the [GinRummyGame.newInstance] factory method to
 * create an instance of this fragment.
 */
class GinRummyGame : Fragment() {
    private val args: GinRummyGameArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_gin_rummy_game, container, false)

        view.findViewById<TextView>(R.id.TitleTextView).text = Game.GinRummy.readableName

        val headerTableRow = view.findViewById<TableRow>(R.id.HeaderRow)

        val numGames: Int = 3

        for (gameNum in 0 until numGames) {
            for (player in args.PlayerList.getPlayers()) {
                val playerNameTextView = TextView(context)
                playerNameTextView.text = player
                playerNameTextView.setPadding(5)
                headerTableRow.addView(playerNameTextView)
            }

            // TODO: must be a better way to do this
            val spacer = TextView(context)
            spacer.text = "  "
            headerTableRow.addView(spacer)
        }

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
}
