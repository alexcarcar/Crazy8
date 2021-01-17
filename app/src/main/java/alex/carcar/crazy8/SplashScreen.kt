package alex.carcar.crazy8

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.View


class SplashScreen(context: Context?) : View(context) {
    private val paint: Paint = Paint()
    private val titleG: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.splash_graphic)
    private var cx: Int = 0
    private var cy: Int = 0
    private val radius: Float = 50f
    private val tag: String = getContext().javaClass.name

    private var scrW: Int = 0
    private var scrH : Int = 0
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.scrW = w
        this.scrH = h
        this.cx = ((w - this.radius) / 2).toInt()
        this.cy = h/6
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        cx += 50
        cy += 25
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius, paint)
        val titleGLeftPos = (scrW - titleG.width)/2
        canvas.drawBitmap(titleG, titleGLeftPos.toFloat(), 100f, null)
    }

    override fun onTouchEvent(evt: MotionEvent): Boolean {
        when (evt.action) {
            MotionEvent.ACTION_DOWN -> Log.d(tag, "Down")
            MotionEvent.ACTION_UP -> Log.d(tag, "Up")
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