package com.michaelalbers.cardscores.support

enum class Game(val readableName:String, val minPlayers:Int, val maxPlayers:Int) {
    _3_14("3-14", 3, 6),
    ShanghaiRummy("Shanghai Rummy", 3, 6),
    GinRummy("Gin Rummy", 2, 2);

    override fun toString(): String {
        return readableName
    }


    companion object {
        /**
         * Returns the Game with the given name.
         *
         * @param name Name of the game
         * @return Game
         * @throws NoSuchElementException On invalid name
         */
        fun getGameByName(name: String): Game {
            return values().first { name == it.readableName }
        }
    }
}
