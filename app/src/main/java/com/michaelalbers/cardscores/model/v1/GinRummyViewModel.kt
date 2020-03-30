package com.michaelalbers.cardscores.model.v1

import androidx.lifecycle.ViewModel

class GinRummyViewModel(private val player1: String, private val player2: String,
                        private val outMoneyCents: Int, private val handWinnerCents: Int) : ViewModel() {
    private val version: String = "v1"
    private val games = ArrayList<Game>(3)

    class Game {
        private val player1Scores = mutableListOf<Int>()
        private val player2Scores = mutableListOf<Int>()
    }
}
