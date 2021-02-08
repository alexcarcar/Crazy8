package alex.carcar.crazy8

import android.graphics.Bitmap

class Card(var id: String) {
    private var suit = 0
    private var rank = 0
    var bmp: Bitmap? = null
    private val scoreValue = 0

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