package com.example.cardscores

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.navigation.fragment.navArgs
import com.example.cardscores.support.Game

/**
 * A simple [Fragment] subclass.
 * Use the [ShanghaiRummyGame.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShanghaiRummyGame : Fragment() {
    private val args: ShanghaiRummyGameArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_shanghai_rummy_game, container, false)
        // To avoid redundant string in enum and strings resource
        view.findViewById<TextView>(R.id.TitleTextView).text = Game.ShanghaiRummy.readableName

        val headerTableRow = view.findViewById<TableRow>(R.id.HeaderRow)

        for (player in args.PlayerList.getPlayers()) {
            val playerNameTextView = TextView(context)
            playerNameTextView.text = player
            playerNameTextView.setPadding(5)
            headerTableRow.addView(playerNameTextView)
        }

        val scoresTableLayout = view.findViewById<TableLayout>(R.id.ScoresTableLayout)
        val numPlayers = args.PlayerList.getPlayers().size
        for (ii in 1 until scoresTableLayout.childCount - 1) {
            val handTableRow = scoresTableLayout.getChildAt(ii) as TableRow

            for (player in 1..numPlayers) {
                val playerScoreEditText = EditText(context)
                playerScoreEditText.inputType = (InputType.TYPE_CLASS_NUMBER)
                handTableRow.addView(playerScoreEditText)
            }
        }

        val totalTableRow = scoresTableLayout.getChildAt(scoresTableLayout.childCount-1) as TableRow
        for (player in 1..numPlayers) {
            val playerScoreTextView = TextView(context)
            totalTableRow.addView(playerScoreTextView)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ShanghaiRummyGame.
         */
        @JvmStatic
        fun newInstance() = ShanghaiRummyGame()
    }
}
