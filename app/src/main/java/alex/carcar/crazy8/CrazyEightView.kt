package alex.carcar.crazy8

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class CrazyEightView(context: Context?) : View(context) {
    private var ctx: Context
    private var scrW: Int = 0
    private var scrH: Int = 0
    private var scaledCW: Int = 0
    private var scaledCH: Int = 0
    private var scale: Float = 0.0f
    private lateinit var paint: Paint

    private val playerHand: MutableList<Card> = ArrayList()
    private val computerHand: MutableList<Card> = ArrayList()
    private val discardPile: MutableList<Card> = ArrayList()
    private val deck: MutableList<Card> = ArrayList()
    private lateinit var cardBack: Bitmap

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.scale = ctx.resources.displayMetrics.density
        this.scrW = w
        this.scrH = h
        this.paint = Paint()
        initializeDeck()
        dealCards()

        scaledCW = scrW / 8
        scaledCH = (scaledCW * 1.28).roundToInt()
        val tempBitmap: Bitmap = BitmapFactory.decodeResource(
            ctx.resources,
            R.drawable.card_blue_back
        )
        cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCW, scaledCH, false)
    }

    override fun onDraw(canvas: Canvas) {
        ctx = context
        super.onDraw(canvas)
        for (i in computerHand.indices) {
            canvas.drawBitmap(
                cardBack,
                i * (scale * 5),
                paint.textSize + 50 * scale,
                null
            )
        }

        val cbackLeft: Float = ((scrW / 2) - cardBack.width - 10).toFloat()
        val cbackTop: Float = ((scrH / 2) - (cardBack.height / 2)).toFloat()
        canvas.drawBitmap(cardBack, cbackLeft, cbackTop, null)

        if (!discardPile.isEmpty()) {
            canvas.drawBitmap(
                discardPile[0].bitmap!!,
                ((scrW / 2) + 10).toFloat(),
                ((scrH / 2) - (cardBack.height / 2)).toFloat(),
                null
            )
        }
    }

    private fun initializeDeck() {
        for (suit in arrayOf("c", "s", "h", "d")) {
            for (value in arrayOf(
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "j",
                "q",
                "k",
                "a"
            )) {
                val tempId = value + suit
                val tempCard = Card(tempId)
                val resourceId = resources.getIdentifier(
                    "crd_$tempId", "drawable",
                    ctx.packageName
                )
                val tempBitmap = BitmapFactory.decodeResource(
                    ctx.resources,
                    resourceId
                )
                scaledCW = (scrW / 8)
                scaledCH = (scaledCW * 1.28).toInt()
                val scaledBitmap = Bitmap.createScaledBitmap(
                    tempBitmap,
                    scaledCW, scaledCH, false
                )
                tempCard.bitmap = scaledBitmap
                deck.add(tempCard)
            }
        }
    }

    private fun dealCards() {
        deck.shuffle(Random())
        for (i in 0..6) {
            drawCard(playerHand)
            drawCard(computerHand)
        }
    }

    private fun drawCard(hand: MutableList<Card>) {
        hand.add(0, deck[0])
        deck.removeAt(0)
        if (deck.isEmpty()) {
            for (i in discardPile.size - 1 downTo 1) {
                deck.add(discardPile[i])
                discardPile.removeAt(i)
                deck.shuffle(Random())
            }
        }
    }

    init {
        ctx = context!!
    }
}