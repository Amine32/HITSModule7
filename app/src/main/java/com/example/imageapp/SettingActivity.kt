package com.example.imageapp

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.InputStream


class SettingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        var img = Uri.parse(intent.getStringExtra("img"))
        imageView.setImageURI(img)


    }

    override fun onStart() {
        super.onStart()
        rotatebtn.setOnClickListener{
            rotateiconbtn.visibility= View.VISIBLE

        }
        rotateiconbtn.setOnClickListener{
            imageView.rotation=imageView.rotation+ 90F
        }
    }

}


