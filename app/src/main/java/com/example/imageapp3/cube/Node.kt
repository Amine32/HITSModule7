package com.example.imageapp3.cube

import kotlin.math.sqrt

class Node(x: Float, y: Float, z: Float, isNumNode: Boolean = false) {
    private val cubeDiagonal: Float = if (!isNumNode) 350F * sqrt(3F) / 3 else 1F
    var x: Float = x * cubeDiagonal
    var y: Float = y * cubeDiagonal
    var z: Float = z * cubeDiagonal
}