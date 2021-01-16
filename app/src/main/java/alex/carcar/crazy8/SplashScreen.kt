package alex.carcar.crazy8

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class SplashScreen(context: Context?) : View(context) {
    private val paint: Paint = Paint()
    private var cx = 200f
    private var cy = 200f
    private var radius = 50f

    init {
        paint.color = Color.GREEN
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(cx, cy, radius, paint)
    }
}