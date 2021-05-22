package com.example.imageapp3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class Point(val x: Float, val y: Float)

var knotsArr: Array<Point> = emptyArray()
var paint = Paint()
var p = Path()

class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val point = Point(event!!.x, event.y)
        knotsArr = append(knotsArr, point)

        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        for(i in knotsArr.indices) {
            canvas?.drawCircle(knotsArr[i].x, knotsArr[i].y, 10f, paint)
        }

        paint.style = Paint.Style.STROKE
        Log.d("TEST", "path drawn")
        canvas?.drawPath(p, paint)

        invalidate()
    }
}

fun importPath(q: Path) {
    Log.d("TEST", "path imported")
    p = q
}

fun append(arr: Array<Point>, element: Point): Array<Point> {
    val list: MutableList<Point> = arr.toMutableList()
    list.add(element)
    return list.toTypedArray()
}

object BezierSplineUtil {

    fun getCurveControlPoints(knots: Array<Point>?): Array<Array<Point?>> {
        require(!(knots == null || knots.size < 2)) { "At least two knot points are required" }
        val n = knots.size - 1
        val firstControlPoints = arrayOfNulls<Point>(n)
        val secondControlPoints = arrayOfNulls<Point>(n)

        // Special case: bezier curve should be a straight line
        if (n == 1) {
            // 3P1 = 2P0 + P3
            var x = (2 * knots[0].x + knots[1].x) / 3
            var y = (2 * knots[0].y + knots[1].y) / 3
            firstControlPoints[0] = Point(x, y)

            // P2 = 2P1 - P0
            x = 2 * firstControlPoints[0]!!.x - knots[0].x
            y = 2 * firstControlPoints[0]!!.y - knots[0].y
            secondControlPoints[0] = Point(x, y)
            return arrayOf(firstControlPoints, secondControlPoints)
        }

        // Calculate first bezier control points
        // Right hand side vector
        val rhs = FloatArray(n)

        // Set right hand side X values
        for (i in 1 until n - 1) {
            rhs[i] = 4 * knots[i].x + 2 * knots[i + 1].x
        }
        rhs[0] = knots[0].x + 2 * knots[1].x
        rhs[n - 1] = (8 * knots[n - 1].x + knots[n].x) / 2f

        // Get first control points X-values
        val x = getFirstControlPoints(rhs)

        // Set right hand side Y values
        for (i in 1 until n - 1) {
            rhs[i] = 4 * knots[i].y + 2 * knots[i + 1].y
        }
        rhs[0] = knots[0].y + 2 * knots[1].y
        rhs[n - 1] = (8 * knots[n - 1].y + knots[n].y) / 2f

        // Get first control points Y-values
        val y = getFirstControlPoints(rhs)
        for (i in 0 until n) {
            // First control point
            firstControlPoints[i] = Point(x[i], y[i])

            // Second control point
            if (i < n - 1) {
                val xx = 2 * knots[i + 1].x - x[i + 1]
                val yy = 2 * knots[i + 1].y - y[i + 1]
                secondControlPoints[i] = Point(xx, yy)
            } else {
                val xx = (knots[n].x + x[n - 1]) / 2
                val yy = (knots[n].y + y[n - 1]) / 2
                secondControlPoints[i] = Point(xx, yy)
            }
        }
        return arrayOf(firstControlPoints, secondControlPoints)
    }

    private fun getFirstControlPoints(rhs: FloatArray): FloatArray {
        val n = rhs.size
        val x = FloatArray(n) // Solution vector
        val tmp = FloatArray(n) // Temp workspace
        var b = 2.0f
        x[0] = rhs[0] / b

        // Decomposition and forward substitution
        for (i in 1 until n) {
            tmp[i] = 1 / b
            b = (if (i < n - 1) 4.0f else 3.5f) - tmp[i]
            x[i] = (rhs[i] - x[i - 1]) / b
        }

        // Backsubstitution
        for (i in 1 until n) {
            x[n - i - 1] -= tmp[n - i] * x[n - i]
        }
        return x
    }
}





