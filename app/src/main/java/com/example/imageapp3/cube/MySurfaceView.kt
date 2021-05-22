package com.example.imageapp3.cube

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class MySurfaceView(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {
    private val ROTATION_SPEED: Float = 0.003F
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var curX = 0F
    private var curY = 0F

    private lateinit var canvas: Canvas

    override fun onTouchEvent(event: MotionEvent): Boolean {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10F
        paint.color = Color.CYAN

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                curX = event.x
                curY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                Cube.rotateY(-(event.x - curX) * ROTATION_SPEED)
                Cube.rotateX(-(event.y - curY) * ROTATION_SPEED)

                canvas = holder.lockCanvas()
                canvas.drawColor(Color.WHITE)
                canvas.translate((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())
                Cube.draw(canvas, paint)
                holder.unlockCanvasAndPost(canvas)

                curX = event.x
                curY = event.y
            }
            else -> {
                return super.onTouchEvent(event)
            }
        }
        return true
    }

    override fun surfaceCreated(holder: SurfaceHolder) = Unit

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) = Unit

    override fun surfaceDestroyed(holder: SurfaceHolder) = Unit
}