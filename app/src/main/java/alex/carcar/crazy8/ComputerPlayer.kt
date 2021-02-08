package alex.carcar.crazy8

class ComputerPlayer {
    fun playCard(hand: MutableList<Card>, suit: Int, rank: Int): String {
        var play = ""
        for (i in hand.indices) {
            val tempId: String = hand[i].id
            val tempRank = hand[i].getRank()
            val tempSuit = hand[i].getSuit()
            if (tempRank != 8) {
                if (rank == 8) {
                    if (suit == tempSuit) {
                        play = tempId
                    }
                } else if (suit == tempSuit || rank == tempRank) {
                    play = tempId
                }
            }
        }
        if (play == "") {
            for (i in hand.indices) {
                val tempId: String = hand[i].id
                if (tempId == "8c" || tempId == "8d" || tempId == "8h" || tempId == "8s") { // <>
                    play = tempId
                }
            }
        }
        return play
    }
}