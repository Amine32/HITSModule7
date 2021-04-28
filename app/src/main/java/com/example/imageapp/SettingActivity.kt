package com.example.imageapp

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.InputStream


class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)




        val resId = intent.getStringExtra("image_from_gallery")
        //val imgThumbnail: ImageView = findViewById(R.id.imageView)

        val imageStream: InputStream? = contentResolver.openInputStream(Uri.parse(resId))
        val bitmap = BitmapFactory.decodeStream(imageStream)

        imageView.setImageBitmap(bitmap)

       // var img = Uri.parse(intent.getStringExtra("img"))
        //imageView.setImageURI(img)







    }
}


