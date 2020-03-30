package com.michaelalbers.cardscores.viewmodel.v1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.michaelalbers.cardscores.model.v1.NewGame
import com.michaelalbers.cardscores.support.Game
import java.util.logging.Logger

class NewGameViewModel: ViewModel() {
    private val logger: Logger = Logger.getLogger(NewGameViewModel::class.java.simpleName)

    private val newGameData = MutableLiveData(NewGame(Game._3_14))

    fun getNewGame(): LiveData<NewGame> {
        // TODO: in the future pull list of saved users here and use that to populate new field in NewGame
        return newGameData
    }

    fun addPlayer() {
        val newGame = newGameData.value
        if (newGame != null) {
            newGame.addPlayer()
            newGameData.value = newGame
        }
    }

    fun setGame(game: Game) {
        val newGame = newGameData.value
        if (newGame != null) {
            if (newGame.game != game) {
                newGameData.value = NewGame(game, newGame.players)
            }
        }
    }

    fun setPlayerName(index: Int, name: String) {
        logger.info("Setting player $index to '$name'")
        val previousNewGame = newGameData.value
        if (previousNewGame != null) {
            val changed = previousNewGame.setPlayerName(index, name)
            // Avoid infinite loop (edit text callback calls this function after every change)
            if (changed) {
                logger.info("Setting new value")
                newGameData.value = previousNewGame
            }
        }
    }

    fun removePlayer(index: Int) {
        logger.info("Removing player at $index")
        val previousNewGame = newGameData.value
        if (previousNewGame != null) {
            previousNewGame.removePlayer(index)
            newGameData.value = previousNewGame
        }
    }
}
