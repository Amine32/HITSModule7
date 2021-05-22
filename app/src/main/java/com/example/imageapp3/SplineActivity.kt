package com.example.imageapp3

import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SplineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spline)
    }

    fun spline(view: View) {
        Log.d("EVENT", "Spline function open")
        var controlPoints = BezierSplineUtil.getCurveControlPoints(knotsArr)
        var firstCP = controlPoints[0]
        var secondCP = controlPoints[1]

        var p = Path()
        p.moveTo(knotsArr[0].x, knotsArr[0].y)

        for (i in firstCP.indices) {
            p.cubicTo(
                firstCP[i]!!.x,
                firstCP[i]!!.y,
                secondCP[i]!!.x,
                secondCP[i]!!.y,
                knotsArr[i + 1].x,
                knotsArr[i + 1].y
            )
        }

        //YES THIS IS BIG BREN TIME
        importPath(p)
    }
}