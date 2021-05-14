package com.example.imageapp3.cube

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path

object Visualiser {

    //val canvas = CubeActivity.MySurfaceView.
    const val distFromCenter = 250f
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val figure: Figure = Cube

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10F
        paint.color = Color.CYAN
        //canvas.translate((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())
    }

    fun Figure.draw(canvas: Canvas) {
        for (face in this.faces) {
            if (face.isVisible())
            drawFace(canvas, face)
        }
    }

    fun drawFace(canvas: Canvas, face: Face) {
        val path = Path().apply {
            fillType = Path.FillType.EVEN_ODD
            rMoveTo(face.nodes[3].x, face.nodes[3].y)
        }
        for (node in face.nodes) {
            path.lineTo(node.x, node.y)
        }
        path.close()
        canvas.drawPath(path, paint)
    }


}