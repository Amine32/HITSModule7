package com.example.imageapp

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.fragment_rotate.*


class SettingActivity : AppCompatActivity() {

    var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        var img = Uri.parse(intent.getStringExtra("img"))
        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, img)
        imageView.setImageBitmap(bitmap)

        val rotateFragment = RotateFragment()



        rotatebtn.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, rotateFragment)
                commit()
            }
        }

    }

    fun resizeImage(view: View) {
        val resized = Bitmap.createScaledBitmap(bitmap!!, 400,400,true)
        imageView.setImageBitmap(resized)
    }

    fun rotateImage(view: View) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            imageView.rotation = imageView.rotation + 90F
        }
    }



}


