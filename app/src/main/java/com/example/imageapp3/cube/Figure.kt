package com.example.imageapp3.cube

import android.graphics.Canvas

open class Figure()
{
    open var nodes = arrayOf<Node>()
    open var faces = arrayOf<Face>()

    fun draw(canvas: Canvas) {
        for (face in this.faces) {
            if (face.isVisible())
            Visualiser.drawFace(canvas, face)
        }
    }
}