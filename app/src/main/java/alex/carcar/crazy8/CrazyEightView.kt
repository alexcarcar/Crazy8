package alex.carcar.crazy8

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.View
import java.util.*
import kotlin.collections.ArrayList


class CrazyEightView(context: Context?) : View(context) {
    private var ctx: Context
    private var scrW: Int = 0
    private var scrH: Int = 0
    private var scaledCW: Int = 0
    private var scaledCH: Int = 0

    private val playerHand: List<Card> = ArrayList()
    private val computerHand: List<Card> = ArrayList()
    private val discardPile: List<Card> = ArrayList()
    private val deck: List<Card> = ArrayList()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.scrW = w
        this.scrH = h
    }

    override fun onDraw(canvas: Canvas) {
        ctx = context
        super.onDraw(canvas)
    }

    private fun initializeDeck() {
        for (i in 0..3) {
            for (j in 102..114) {
                val tempId = j + i * 100
                val tempCard = Card(tempId)
                val resourceId = resources.getIdentifier(
                    "card$tempId", "drawable",
                    ctx.packageName
                )
                val tempBitmap = BitmapFactory.decodeResource(
                    ctx.resources,
                    resourceId
                )
                scaledCW = (scrW / 8).toInt()
                scaledCH = (scaledCW * 1.28).toInt()
                val scaledBitmap = Bitmap.createScaledBitmap(
                    tempBitmap,
                    scaledCW, scaledCH, false
                )
                tempCard.bitmap = scaledBitmap
                //deck.add(tempCard)
            }
        }
    }

    private fun dealCards() {
        Collections.shuffle(deck, Random())
        for (i in 0..6) {
            drawCard(playerHand)
            drawCard(computerHand)
        }
    }

    private fun drawCard(hand: List<Card>) {
        hand.add(0, deck[0])
        deck.removeAt(0)
        if (deck.isEmpty()) {
            for (i in discardPile.size - 1 downTo 1) {
                deck.add(discardPile[i])
                discardPile.removeAt(i)
                Collections.shuffle(deck, Random())
            }
        }
    }
    init {
        ctx = context!!
    }
}