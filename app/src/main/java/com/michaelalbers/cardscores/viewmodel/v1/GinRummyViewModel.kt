package com.michaelalbers.cardscores.viewmodel.v1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.michaelalbers.cardscores.model.v1.GinRummy
import com.michaelalbers.cardscores.support.Game
import com.michaelalbers.cardscores.support.PlayerList
import java.util.logging.Logger

class GinRummyViewModel : ViewModel() {
    private val logger: Logger = Logger.getLogger(GinRummyViewModel::class.java.simpleName)

    private val ginRummyData = MutableLiveData(GinRummy())

    fun getGinRummy(): LiveData<GinRummy> {
        return ginRummyData
    }

    fun setPlayers(playerList: PlayerList) {
        val players = playerList.getPlayers()
        if (players.size != 2) {
            throw IllegalArgumentException("Incorrect number of ${Game.GinRummy.readableName} players. Need 2, got ${players.size}")
        }
        val ginRummy = ginRummyData.value
        if (null != ginRummy) {
            ginRummy.setPlayers(players[0], players[1])
            ginRummyData.value = ginRummy
        }
    }
}
