package com.example.imageapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_main.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        var img = Uri.parse(intent.getStringExtra("img"))
        imageView.setImageURI(img)
    }
}