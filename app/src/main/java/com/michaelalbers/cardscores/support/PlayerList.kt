package com.michaelalbers.cardscores.support

import android.os.Parcel
import android.os.Parcelable

class PlayerList() : Parcelable {

    private val players = mutableListOf<String>()

    fun addPlayer(player: String) {
        players.add(player)
    }

    fun getPlayers() : List<String> {
        return players
    }

    override fun toString(): String {
        return players.toString()
    }

    constructor(parcel: Parcel) : this() {
        parcel.readList(players as List<*>, String::class.java.classLoader)
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeList(players as List<*>?)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayerList> {
        override fun createFromParcel(parcel: Parcel): PlayerList {
            return PlayerList(parcel)
        }

        override fun newArray(size: Int): Array<PlayerList?> {
            return arrayOfNulls(size)
        }
    }
}