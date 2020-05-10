package com.michaelalbers.cardscores.model.v1

import java.lang.IllegalArgumentException

class GinRummy() {

    private val version: String = "v1"
    private val games = ArrayList<Game>(NUM_GAMES)
    private var player1: String = ""
    private var player2: String = ""

    class Game {
        private val player1HandScores = mutableListOf<Int>()
        private val player2HandScores = mutableListOf<Int>()

        fun getPlayerHandScores(playerNumber: Int) : List<Int> {
            return when (playerNumber) {
                0 -> player1HandScores
                1 -> player2HandScores
                else -> throw IllegalArgumentException("Invalid Gin Rummy player index ${playerNumber}.")
            }
        }

        fun getPlayerScore(playerNumber: Int) : Int {
            return when (playerNumber) {
                0 -> player1HandScores.sum()
                1 -> player2HandScores.sum()
                else -> throw IllegalArgumentException("Invalid Gin Rummy player index ${playerNumber}.")
            }
        }

        fun isGameFinished() : Boolean {
            return player1HandScores.sum() > MAX_SCORE || player2HandScores.sum() > MAX_SCORE
        }
    }

    companion object {
        const val NUM_GAMES = 3
        const val NUM_PLAYERS = 2
        const val MAX_SCORE = 150
    }

    init {
        for (gameNum in 0 until NUM_GAMES) {
            games.add(Game())
        }
    }

    fun setPlayers(player1: String, player2: String) {
        this.player1 = player1
        this.player2 = player2
    }

    fun getPlayer(index: Int) : String {
        return when (index) {
            0 -> player1
            1 -> player2
            else -> throw IllegalArgumentException("Invalid Gin Rummy player index ${index}.")
        }
    }

    fun getGame(index: Int) : Game {
        return games[index]
    }
}
