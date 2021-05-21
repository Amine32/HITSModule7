package com.example.imageapp3.cube

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

class Face(var nodes: Array<Node>) {
    private var a = (nodes[1].y * nodes[2].z - nodes[1].z * nodes[2].y) +
                    (nodes[0].z * nodes[2].y - nodes[0].y * nodes[2].z) +
                    (nodes[0].y * nodes[1].z - nodes[0].z * nodes[1].y)

    private var b = (nodes[1].z * nodes[2].x - nodes[1].x * nodes[2].z) +
                    (nodes[0].x * nodes[2].z - nodes[0].z * nodes[2].x) +
                    (nodes[0].z * nodes[1].x - nodes[0].x * nodes[1].z)

    private var c = (nodes[1].x * nodes[2].y - nodes[1].y * nodes[2].x) +
                    (nodes[0].y * nodes[2].x - nodes[0].x * nodes[2].y) +
                    (nodes[0].x * nodes[1].y - nodes[0].y * nodes[1].x)

    private var d = -(a * nodes[0].x + b * nodes[0].y + c * nodes[0].z)

    fun updatePlaneValues() {
        a = (nodes[1].y * nodes[2].z - nodes[1].z * nodes[2].y) +
            (nodes[0].z * nodes[2].y - nodes[0].y * nodes[2].z) +
            (nodes[0].y * nodes[1].z - nodes[0].z * nodes[1].y)

        b = (nodes[1].z * nodes[2].x - nodes[1].x * nodes[2].z) +
            (nodes[0].x * nodes[2].z - nodes[0].z * nodes[2].x) +
            (nodes[0].z * nodes[1].x - nodes[0].x * nodes[1].z)

        c = (nodes[1].x * nodes[2].y - nodes[1].y * nodes[2].x) +
            (nodes[0].y * nodes[2].x - nodes[0].x * nodes[2].y) +
            (nodes[0].x * nodes[1].y - nodes[0].y * nodes[1].x)

        d = -(a * nodes[0].x + b * nodes[0].y + c * nodes[0].z)
    }

    fun draw(canvas: Canvas, paint: Paint, n: Int) {
        val path = Path().apply {
            rMoveTo(nodes[3].x, nodes[3].y)
        }
        for (node in nodes) {
            path.lineTo(node.x, node.y)
        }
        path.close()
        canvas.drawPath(path, paint)
        drawNumber(canvas, paint, n)
    }

    private fun drawNumber(canvas: Canvas, paint: Paint, n: Int) {
        val path = Path()
        val center = Node(
            (nodes[0].x + nodes[1].x + nodes[2].x + nodes[3].x) / 4,
            (nodes[0].y + nodes[1].y + nodes[2].y + nodes[3].y) / 4,
            (nodes[0].z + nodes[1].z + nodes[2].z + nodes[3].z) / 4,
            true
        )
        val horVector = Vector(
            nodes[3].x - nodes[0].x,
            nodes[3].y - nodes[0].y,
            nodes[3].z - nodes[0].z,
        )
        val vertVector = Vector(
            nodes[1].x - nodes[0].x,
            nodes[1].y - nodes[0].y,
            nodes[1].z - nodes[0].z,
        )
        when(n) {
            1 -> {
                path.moveTo(center.x, center.y)
                path.rLineTo(vertVector.x / 4, vertVector.y / 4)
                path.rLineTo(-vertVector.x / 2, -vertVector.y / 2)
            }
            2 -> {
                path.moveTo(center.x, center.y)
                path.rLineTo(-horVector.x / 4, -horVector.y / 4)
                path.rLineTo(vertVector.x / 4, vertVector.y / 4)
                path.rLineTo(horVector.x / 2, horVector.y / 2)
                path.moveTo(center.x, center.y)
                path.rLineTo(horVector.x / 4, horVector.y / 4)
                path.rLineTo(-vertVector.x / 4, -vertVector.y / 4)
                path.rLineTo(-horVector.x / 2, -horVector.y / 2)
            }
            3 -> {
                path.moveTo(center.x, center.y)
                path.rLineTo(horVector.x / 4, horVector.y / 4)
                path.rLineTo(-horVector.x / 2, -horVector.y / 2)
                path.rLineTo(vertVector.x / 4, vertVector.y / 4)
                path.rLineTo(horVector.x / 2, horVector.y / 2)
                path.rLineTo(-horVector.x / 2, -horVector.y / 2)
                path.rLineTo(-vertVector.x / 2, -vertVector.y / 2)
                path.rLineTo(horVector.x / 2, horVector.y / 2)
                path.rLineTo(-horVector.x / 2, -horVector.y / 2)
            }
            4 -> {
                path.moveTo(center.x, center.y)
                path.rLineTo(-horVector.x / 4, -horVector.y / 4)
                path.rLineTo(-vertVector.x / 4, -vertVector.y / 4)
                path.moveTo(center.x, center.y)
                path.rLineTo(horVector.x / 4, horVector.y / 4)
                path.rLineTo(-vertVector.x / 4, -vertVector.y / 4)
                path.rLineTo(vertVector.x / 2, vertVector.y / 2)
            }
            5 -> {
                path.moveTo(center.x, center.y)
                path.rLineTo(horVector.x / 4, horVector.y / 4)
                path.rLineTo(vertVector.x / 4, vertVector.y / 4)
                path.rLineTo(-horVector.x / 2, -horVector.y / 2)
                path.moveTo(center.x, center.y)
                path.rLineTo(-horVector.x / 4, -horVector.y / 4)
                path.rLineTo(-vertVector.x / 4, -vertVector.y / 4)
                path.rLineTo(horVector.x / 2, horVector.y / 2)
            }
            6 -> {
                path.moveTo(center.x, center.y)
                path.rLineTo(horVector.x / 4, horVector.y / 4)
                path.rLineTo(-horVector.x / 2, -horVector.y / 2)
                path.rLineTo(vertVector.x / 4, vertVector.y / 4)
                path.rLineTo(horVector.x / 2, horVector.y / 2)
                path.rLineTo(-horVector.x / 2, -horVector.y / 2)
                path.rLineTo(-vertVector.x / 2, -vertVector.y / 2)
                path.rLineTo(horVector.x / 2, horVector.y / 2)
                path.rLineTo(vertVector.x / 4, vertVector.y / 4)
            }
        }
        path.moveTo(center.x, center.y)
        path.close()
        canvas.drawPath(path, paint)
    }

    fun isVisible(): Boolean {
        var isVisible: Boolean = c >= 0
        if (a + b + c + d > 0) isVisible = !isVisible

        return isVisible
    }
}