package alex.carcar.crazy8

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View

class SplashScreen(context: Context?) : View(context) {
    private val paint: Paint
    private var cx: Int
    private var cy: Int
    private val radius: Float
    private val TAG = getContext().javaClass.name
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        cx = cx + 50
        cy = cy + 25
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius, paint)
    }

    override fun onTouchEvent(evt: MotionEvent): Boolean {
        val action = evt.action
        when (action) {
            MotionEvent.ACTION_DOWN -> Log.d(TAG, "Down")
            MotionEvent.ACTION_UP -> Log.d(TAG, "Up")
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "Move")
                cx = evt.x.toInt()
                cy = evt.y.toInt()
            }
        }
        invalidate()
        return true
    }

    init {
        paint = Paint()
        paint.color = Color.GREEN
        paint.isAntiAlias = true
        cx = 200
        cy = 200
        radius = 50f
    }
}