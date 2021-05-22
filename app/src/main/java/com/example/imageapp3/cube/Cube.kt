package com.example.imageapp3.cube

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.cos
import kotlin.math.sin

object Cube {
    var nodes = arrayOf(
        Node(-1F, 1F, 1F),
        Node(-1F, -1F, 1F),
        Node(1F, -1F, 1F),
        Node(1F, 1F, 1F),
        Node(-1F, 1F, -1F),
        Node(-1F, -1F, -1F),
        Node(1F, -1F, -1F),
        Node(1F, 1F, -1F),
    )
    var faces = arrayOf(
        Face(arrayOf(nodes[0], nodes[1], nodes[2], nodes[3])),
        Face(arrayOf(nodes[0], nodes[1], nodes[5], nodes[4])),
        Face(arrayOf(nodes[4], nodes[5], nodes[6], nodes[7])),
        Face(arrayOf(nodes[2], nodes[3], nodes[7], nodes[6])),
        Face(arrayOf(nodes[1], nodes[2], nodes[6], nodes[5])),
        Face(arrayOf(nodes[0], nodes[3], nodes[7], nodes[4])),
    )

    fun draw(canvas: Canvas, paint: Paint) {
        var numCounter = 1
        for (face in faces) {
            face.updatePlaneValues()
            if (face.isVisible()) {
                face.draw(canvas, paint, numCounter)
            }
            numCounter++
        }
    }

    fun rotateX(rad: Float) {
        val sin = sin(rad)
        val cos = cos(rad)

        for (node in nodes) {
            val newY = node.y * cos - node.z * sin
            val newZ = node.z * cos + node.y * sin

            node.y = newY
            node.z = newZ
        }
    }

    fun rotateY(rad: Float) {
        val sin = sin(rad)
        val cos = cos(rad)

        for (node in nodes) {
            val newX = node.x * cos - node.z * sin
            val newZ = node.z * cos + node.x * sin

            node.x = newX
            node.z = newZ
        }
    }
}