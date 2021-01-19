package alex.carcar.crazy8

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.View


class SplashScreen(context: Context?) : View(context) {
    private val paint: Paint = Paint()
    private val titleG: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.splash_graphic)
    private val playBtnUp: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.btn_up)
    private val playBtnDn: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.btn_down)
    private var playBtnPressed: Boolean = false
    private var cx: Int = 0
    private var cy: Int = 0
    private val radius: Float = 50f
    private val tag: String = getContext().javaClass.name

    private var scrW: Int = 0
    private var scrH: Int = 0
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.scrW = w
        this.scrH = h
        this.cx = ((w - this.radius) / 2).toInt()
        this.cy = h / 6
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        cx += 50
        cy += 25
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius, paint)
        val titleGLeftPos = (scrW - titleG.width) / 2
        canvas.drawBitmap(titleG, titleGLeftPos.toFloat(), 100f, null)
        val playBtnLeftPos = (scrW - playBtnUp.width) / 2
        val playBtnTopPos = scrH / 2
        if (playBtnPressed) {
            canvas.drawBitmap(playBtnDn, playBtnLeftPos.toFloat(), playBtnTopPos.toFloat(), null)
        } else {
            canvas.drawBitmap(playBtnUp, playBtnLeftPos.toFloat(), playBtnTopPos.toFloat(), null)
        }
    }

    override fun onTouchEvent(evt: MotionEvent): Boolean {
        val x = evt.x
        val y = evt.y

        when (evt.action) {
            MotionEvent.ACTION_DOWN -> {
                cx = x.toInt()
                cy = y.toInt()
                Log.d(tag, "Down")
                val btnLeft = (scrW - playBtnUp.width) / 2
                val btnRight = btnLeft + playBtnUp.width
                val btnTop = scrH * 0.5
                val btnBottom = btnTop + playBtnUp.height
                val withinBounds = x > btnLeft && x < btnRight && y > btnTop && y < btnBottom
                if (withinBounds) {
                    playBtnPressed = true
                }
            }
            MotionEvent.ACTION_UP -> {
                Log.d(tag, "Up")
                if (playBtnPressed) {
                    // Launch main game screen
                    Log.d(tag, "Launch main game screen")
                }
                playBtnPressed = false
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(tag, "Move")
                cx = evt.x.toInt()
                cy = evt.y.toInt()
            }
        }
        invalidate()
        return true
    }

    init {
        paint.color = Color.GREEN
        paint.isAntiAlias = true
    }
}