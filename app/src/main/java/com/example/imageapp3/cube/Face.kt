package com.example.imageapp3.cube

import android.graphics.Canvas
import android.graphics.Path

class Face(var nodes: Array<Node>) {
    var numNodes: Array<Node> = setNumNodes()

    private val a = (nodes[1].y * nodes[2].z - nodes[1].z * nodes[2].y) +
                    (nodes[0].z * nodes[2].y - nodes[0].y * nodes[2].z) +
                    (nodes[0].y * nodes[1].z - nodes[0].z * nodes[1].y)

    private val b = (nodes[1].z * nodes[2].x - nodes[1].x * nodes[2].z) +
                    (nodes[0].x * nodes[2].z - nodes[0].z * nodes[2].x) +
                    (nodes[0].z * nodes[1].x - nodes[0].x * nodes[1].z)

    private val c = (nodes[1].x * nodes[2].y - nodes[1].y * nodes[2].x) +
                    (nodes[0].y * nodes[2].x - nodes[0].x * nodes[2].y) +
                    (nodes[0].x * nodes[1].y - nodes[0].y * nodes[1].x)

    private val d = -(a * nodes[0].x + b * nodes[0].y + c * nodes[0].z)

    private fun setNumNodes(): Array<Node> {
        val x1 = nodes[0].x
        val x2 = nodes[3].x

        val y1 = nodes[0].y
        val y2 = nodes[1].y

        var array: Array<Node> = arrayOf()

        array = array.plus(getNodeFromXY(0.3F * x2 + 0.7F * x1, 0.8F * y2 + 0.2F * y1))
        array = array.plus(getNodeFromXY(0.7F * x2 + 0.3F * x1, 0.8F * y2 + 0.2F * y1))
        array = array.plus(getNodeFromXY(0.3F * x2 + 0.7F * x1, 0.5F * y2 + 0.5F * y1))
        array = array.plus(getNodeFromXY(0.7F * x2 + 0.3F * x1, 0.5F * y2 + 0.5F * y1))
        array = array.plus(getNodeFromXY(0.3F * x2 + 0.7F * x1, 0.2F * y2 + 0.8F * y1))
        array = array.plus(getNodeFromXY(0.7F * x2 + 0.3F * x1, 0.2F * y2 + 0.8F * y1))

        return array
    }

    private fun getNodeFromXY(x: Float, y: Float): Node {
        val z: Float = -(a * x + b * y + d) / c
        return Node(x, y, z)
    }

    fun draw(canvas: Canvas, n: Int) {
        val path = Path().apply {
            //fillType = Path.FillType.EVEN_ODD
            rMoveTo(nodes[3].x, nodes[3].y)
        }
        for (node in nodes) {
            path.lineTo(node.x, node.y)
        }
        path.close()
        canvas.drawPath(path, CubeActivity.paint)
        drawNumber(canvas, n)
    }

    private fun drawNumber(canvas: Canvas, n: Int) {
        val path = Path()
        when(n) {
            1 -> {
                path.rMoveTo(numNodes[1].x, numNodes[1].y)
                path.lineTo(numNodes[3].x, numNodes[3].y)
                path.lineTo(numNodes[5].x, numNodes[5].y)
            }
        }
        path.close()
        canvas.drawPath(path, CubeActivity.paint)
    }

    fun isVisible(): Boolean {
        var isVisible: Boolean = c >= 0
        if (a + b + c + d > 0) isVisible = !isVisible

        return isVisible
    }
}