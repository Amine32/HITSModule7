package com.example.imageapp3.cube

import kotlin.math.cos
import kotlin.math.sin

object Rotator {
    fun RotateX(rad: Float) {
        val sin = sin(rad)
        val cos = cos(rad)

        for (node in Cube.nodes) {
            val newY = node.y * cos - node.z * sin
            val newZ = node.z * cos + node.y * sin

            node.y = newY
            node.z = newZ
        }
    }

    fun RotateY(rad: Float) {
        val sin = sin(rad)
        val cos = cos(rad)

        for (node in Cube.nodes) {
            val newX = node.x * cos - node.z * sin
            val newZ = node.z * cos + node.x * sin

            node.x = newX
            node.z = newZ
        }
    }

    fun RotateZ(rad: Float) {
        val sin = sin(rad)
        val cos = cos(rad)

        for (node in Cube.nodes) {
            val newX = node.x * cos - node.y * sin
            val newY = node.y * cos + node.x * sin

            node.x = newX
            node.y = newY
        }
    }
}