package com.example.imageapp3.cube

import android.graphics.Canvas
import kotlin.math.sqrt

object Cube {
    private const val distFromCenter = 250f
    private val cubeDiagonal = distFromCenter * sqrt(3F) / 2
    var nodes = arrayOf(
        Node(
            -cubeDiagonal,
            cubeDiagonal,
            cubeDiagonal
        ),
        Node(
            -cubeDiagonal,
            -cubeDiagonal,
            cubeDiagonal
        ),
        Node(
            cubeDiagonal,
            -cubeDiagonal,
            cubeDiagonal
        ),
        Node(
            cubeDiagonal,
            cubeDiagonal,
            cubeDiagonal
        ),
        Node(
            -cubeDiagonal,
            cubeDiagonal,
            -cubeDiagonal
        ),
        Node(
            -cubeDiagonal,
            -cubeDiagonal,
            -cubeDiagonal
        ),
        Node(
            cubeDiagonal,
            -cubeDiagonal,
            -cubeDiagonal
        ),
        Node(
            cubeDiagonal,
            cubeDiagonal,
            -cubeDiagonal
        ),
    )
    var faces = arrayOf(
        Face(arrayOf(nodes[0], nodes[1], nodes[2], nodes[3])),
        Face(arrayOf(nodes[0], nodes[1], nodes[5], nodes[4])),
        Face(arrayOf(nodes[4], nodes[5], nodes[6], nodes[7])),
        Face(arrayOf(nodes[2], nodes[3], nodes[7], nodes[6])),
        Face(arrayOf(nodes[1], nodes[2], nodes[6], nodes[5])),
        Face(arrayOf(nodes[0], nodes[3], nodes[7], nodes[4])),
    )

    fun draw(canvas: Canvas) {
        var numCounter = 1  
        for (face in this.faces) {
            if (face.isVisible()) {
                face.draw(canvas, numCounter)
            }
            numCounter++
        }
    }
}