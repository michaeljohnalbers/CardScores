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
 * Use the [ThreeFourteenGame.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThreeFourteenGame : Fragment() {
    private val args: ThreeFourteenGameArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment__314_game, container, false)
        view.findViewById<TextView>(R.id.TitleTextView).text = Game._3_14.readableName

        val headerTableRow = view.findViewById<TableRow>(R.id.HeaderRow)

        for (player in args.PlayerList.getPlayers()) {
            val playerNameTextView = TextView(context)
            playerNameTextView.text = player
            playerNameTextView.setPadding(5)
            headerTableRow.addView(playerNameTextView)
        }

        val tableLayout = view.findViewById<TableLayout>(R.id.ScoresTableLayout)
        val numPlayers = args.PlayerList.getPlayers().size
        // TODO: add dealer info
        for (hand in 3..10) {
            tableLayout.addView(createRow(hand.toString(), numPlayers))
        }

        for ((ii, hand) in listOf("J", "Q", "K", "A").withIndex()) {
            tableLayout.addView(createRow("$hand (${10 + (ii + 1)})", numPlayers))
        }

        val totalTableRow = TableRow(context)

        val totalTextView = TextView(context)
        totalTextView.text = resources.getText(R.string.total)
        totalTableRow.addView(totalTextView)

        for (player in 1..numPlayers) {
            val playerScoreTextView = TextView(context)
            totalTableRow.addView(playerScoreTextView)
        }

        tableLayout.addView(totalTableRow)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment _314Game.
         */
        @JvmStatic
        fun newInstance() = ThreeFourteenGame()
    }

    private fun createRow(handName: String, numPlayers: Int) : TableRow {
        val handTableRow = TableRow(context)

        val handNameTextView = TextView(context)
        handNameTextView.text = handName
        //handNameTextView.gravity = Gravity.END // To right-align text
        handTableRow.addView(handNameTextView)

        for (player in 1..numPlayers) {
            val playerScoreEditText = EditText(context)
            playerScoreEditText.inputType = (InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_CLASS_NUMBER)
            handTableRow.addView(playerScoreEditText)
        }

        return handTableRow
    }
}
