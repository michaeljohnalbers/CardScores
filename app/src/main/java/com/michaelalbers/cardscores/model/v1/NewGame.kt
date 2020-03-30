package com.michaelalbers.cardscores.model.v1

import com.michaelalbers.cardscores.support.Game
import java.lang.IllegalStateException

data class NewGame constructor (val game: Game) {
    val players = ArrayList<String>()

    init {
        for (ii in 0 until game.minPlayers) {
            addPlayer()
        }
    }

    constructor(game: Game, players: ArrayList<String>) : this(game) {
        this.players.clear()
        this.players.addAll(players)

        if (this.players.size > game.maxPlayers) {
            this.players.subList(game.maxPlayers, this.players.size).clear()
        }
    }

    fun addPlayer() {
        if (players.size >= game.maxPlayers) {
            throw IllegalStateException("Cannot add another player, already at the maximum of ${game.maxPlayers}.")
        }
        players.add("")
    }

    fun canAddNewPlayer() : Boolean {
        return players.size < game.maxPlayers
    }

    fun removePlayer(index: Int) {
        if (players.size <= game.minPlayers) {
            throw IllegalStateException("Cannot remove another player, already at the minimum of ${game.minPlayers}.")
        }
        players.removeAt(index)
    }

    fun setPlayerName(index: Int, name: String) : Boolean {
        val changed = players[index] != name
        players[index] = name
        return changed
    }
}
