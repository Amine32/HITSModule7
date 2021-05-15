package com.example.imageapp3.cube

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates


class CubeActivity : AppCompatActivity() {

    companion object {
        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()?.hide(); // hide the title bar

        setContentView(MySurfaceView(this))
    }

    internal class MySurfaceView(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {
        private var path = Path()

        private var curX by Delegates.notNull<Float>()
        private var curY by Delegates.notNull<Float>()

        private var canvas by Delegates.notNull<Canvas>()

        override fun onTouchEvent(event: MotionEvent): Boolean {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 10F
            paint.color = Color.CYAN

            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    curX = event.x
                    curY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    Rotator.RotateY(-(event.x - curX) / 200)
                    Rotator.RotateX(-(event.y - curY) / 200)

                    canvas = holder.lockCanvas()
                    canvas.drawColor(Color.WHITE)
                    canvas.translate((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())
                    Cube.draw(canvas)
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

        override fun surfaceCreated(holder: SurfaceHolder) {
            TODO("Not yet implemented")
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            TODO("Not yet implemented")
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            TODO("Not yet implemented")
        }


    }
}