package alex.carcar.crazy8

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class CrazyEightView(context: Context?) : View(context) {
    private var validRank: Int = 0
    private var validSuit: Int = 0
    private var movingY: Int = 0
    private var movingX: Int = 0
    private var movingIdx: Int = 0
    private var ctx: Context
    private var scrW: Int = 0
    private var scrH: Int = 0
    private var scaledCW: Int = 0
    private var scaledCH: Int = 0
    private var scale: Float = 0.0f
    private lateinit var paint: Paint
    private var myTurn: Boolean = true

    private val playerHand: MutableList<Card> = ArrayList()
    private val computerHand: MutableList<Card> = ArrayList()
    private val discardPile: MutableList<Card> = ArrayList()
    private val deck: MutableList<Card> = ArrayList()
    private lateinit var cardBack: Bitmap
    private lateinit var computerPlayer: ComputerPlayer

    private fun computerPlay() {
        var tempPlay = ""
        val validRank = 1
        val validSuit = 1
        while (tempPlay == "") {
            tempPlay = computerPlayer.playCard(computerHand, validSuit, validRank)
            if (tempPlay == "") {
                drawCard(computerHand)
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.scale = ctx.resources.displayMetrics.density
        this.scrW = w
        this.scrH = h
        this.paint = Paint()
        this.computerPlayer = ComputerPlayer()
        scaledCW = scrW / 8
        scaledCH = (scaledCW * 1.28).roundToInt()

        val tempBitmap: Bitmap = BitmapFactory.decodeResource(
            ctx.resources,
            R.drawable.card_blue_back
        )
        cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCW, scaledCH, false)
        initializeDeck()
        dealCards()
        drawCard(discardPile)
        validSuit = discardPile[0].getSuit()
        validRank = discardPile[0].getRank()
        this.myTurn = Random().nextBoolean()
        if (!myTurn) {
            computerPlay()
        }
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

        val cardBackLeft: Float = ((scrW / 2) - cardBack.width - 10).toFloat()
        val cardBackTop: Float = ((scrH / 2) - (cardBack.height / 2)).toFloat()
        canvas.drawBitmap(cardBack, cardBackLeft, cardBackTop, null)

        if (discardPile.isNotEmpty()) {
            canvas.drawBitmap(
                discardPile[0].bmp!!,
                ((scrW / 2) + 10).toFloat(),
                ((scrH / 2) - (cardBack.height / 2)).toFloat(),
                null
            )
        }

        for (i in playerHand.indices) {
            if (i == movingIdx) {
                canvas.drawBitmap(
                    playerHand[i].getBitmap()!!,
                    movingX.toFloat(),
                    movingY.toFloat(),
                    null
                )
            } else {
                if (i < 7) {
                    canvas.drawBitmap(
                        playerHand[i].getBitmap()!!,
                        (i * (scaledCW + 5)).toFloat(),
                        scrH - scaledCH - paint.textSize - 50 * scale,
                        null
                    )
                }
            }
        }
        invalidate()
        setToFullScreen()
    }

    private fun setToFullScreen() {
        this.systemUiVisibility = (SYSTEM_UI_FLAG_LOW_PROFILE
                or SYSTEM_UI_FLAG_FULLSCREEN
                or SYSTEM_UI_FLAG_LAYOUT_STABLE
                or SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or SYSTEM_UI_FLAG_HIDE_NAVIGATION)
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
                tempCard.bmp = scaledBitmap
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val eventAction = event.action
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (eventAction) {
            MotionEvent.ACTION_DOWN -> if (myTurn) {
                var i = 0
                while (i < 7) {
                    if (x > i * (scaledCW + 5) && x < i * (scaledCW + 5) + scaledCW && y > scrH - scaledCH - paint.textSize - 50 * scale) {
                        movingIdx = i
                        movingX = x - (30 * scale).toInt()
                        movingY = y - (70 * scale).toInt()
                    }
                    i++
                }
            }
            MotionEvent.ACTION_MOVE -> {
                movingX = x - (30 * scale).toInt()
                movingY = y - (70 * scale).toInt()
            }
            MotionEvent.ACTION_UP -> {
                if (movingIdx > -1 &&
                    x > (scrW / 2) - (100 * scale) &&
                    x < (scrW / 2) + (100 * scale) &&
                    y > (scrH / 2) - (100 * scale) &&
                    y < (scrH / 2) + (100 * scale) &&
                    (playerHand[movingIdx].getRank() == 8 ||
                            playerHand[movingIdx].getRank() == validRank ||
                            playerHand[movingIdx].getSuit() == validSuit)
                ) {
                    validRank = playerHand[movingIdx].getRank()
                    validSuit = playerHand[movingIdx].getSuit()
                    discardPile.add(0, playerHand[movingIdx])
                    playerHand.removeAt(movingIdx)
                }
            }
        }
        invalidate()
        return true
    }

    init {
        ctx = context!!
    }
}