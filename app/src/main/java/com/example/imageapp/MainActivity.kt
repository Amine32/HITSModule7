package com.example.imageapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imageapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CameraButton.setOnClickListener{

        }

    }
}