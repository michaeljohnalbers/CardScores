package com.michaelalbers.cardscores.model.v1

import androidx.lifecycle.ViewModel

class ThreeFourteenViewModel: ViewModel() {
    private val version: String = "v1"
    private val players = mutableListOf<Player>()

    class Player(private val name: String) {
        private val NUMBER_HANDS = 12
        private val scores = IntArray(NUMBER_HANDS)

        fun computeTotal(): Int {
            return scores.sum()
        }
    }
}
