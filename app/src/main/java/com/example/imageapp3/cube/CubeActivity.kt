package com.example.imageapp3.cube

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

class CubeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        supportActionBar?.hide(); // hide the title bar

        setContentView(MySurfaceView(this))
    }
}