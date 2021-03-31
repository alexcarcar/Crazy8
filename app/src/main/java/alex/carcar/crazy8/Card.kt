package alex.carcar.crazy8

import android.graphics.Bitmap

class Card(var id: String) {
    private val TAG = "Card.kt"
    private var suit = 0
    private var rank = 0
    var bmp: Bitmap? = null
    private val scoreValue = 0

    init {
        //    Diamonds (100)
        //    Clubs (200)
        //    Hearts (300)
        //    Spades (400)
        if (id.contains("d")) {
            suit = 100
            rank = findRank(id, "d")
        } else if (id.contains("c")) {
            suit = 200
            rank = findRank(id, "c")
        } else if (id.contains("h")) {
            suit = 300
            rank = findRank(id, "h")
        } else if (id.contains("s")) {
            suit = 400
            rank = findRank(id, "s")
        }
    }

    private fun findRank(id: String, suit: String): Int {
        val rank = id.replace(suit, "")
        if (isNumber(rank)) return rank.toInt()
        if (rank.equals("j")) return 10
        if (rank.equals("q")) return 11
        if (rank.equals("k")) return 12
        if (rank.equals("a")) return 13
        return -1 // error: rank not found
    }

    fun isNumber(s: String?): Boolean {
        return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
    }

    fun Card(newId: String) {
        id = newId
//        suit = Math.round((id / 100 * 100).toFloat())
//        rank = id - suit
    }

    fun getScoreValue(): Int {
        return scoreValue
    }

    fun setBitmap(newBitmap: Bitmap) {
        bmp = newBitmap
    }

    fun getBitmap(): Bitmap? {
        return bmp
    }

    fun getSuit(): Int {
        return suit
    }

    fun getRank(): Int {
        return rank
    }

}