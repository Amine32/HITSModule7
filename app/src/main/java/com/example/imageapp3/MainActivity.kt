package com.example.imageapp3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imageapp3.cube.CubeActivity
import com.example.imageapp3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind, R.id.rootLayout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() // hide the title bar

        setContentView(R.layout.activity_main)

        binding.selectImageEditBtn.setOnClickListener {
            val intent = Intent(this, ImageSelectionActivity::class.java)
            startActivity(intent)
        }
        binding.selectSplineBtn.setOnClickListener{
            val intent = Intent(this, SplineActivity::class.java)
            startActivity(intent)
        }
        binding.selectCubeBtn.setOnClickListener{
            val intent = Intent(this, CubeActivity::class.java)
            startActivity(intent)
        }
    }
}