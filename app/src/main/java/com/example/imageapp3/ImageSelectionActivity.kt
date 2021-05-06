package com.example.imageapp3

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Window
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imageapp3.databinding.ActivityImageSelectionBinding

class ImageSelectionActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityImageSelectionBinding::bind, R.id.imageSelectionLayout)
    private val PERMISSION_DENIED_MESSAGE: String = "Permission denied"

    private var imageUri: Uri? = null

    companion object {
        private val PERMISSION_CODE = 1000
        private val IMAGE_CAPTURE_CODE = 1001
        private val IMAGE_PICK_CODE=1002
        private val PERMISSION_CODE_GALLERY=1003
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()?.hide(); // hide the title bar

        setContentView(R.layout.activity_image_selection)

        binding.CameraButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_DENIED) {
                    //permission not enabled
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )

                    requestPermissions(permission, PERMISSION_CODE)

                } else {
                    //permission already granted
                    openCamera()
                }
            }
            else {
                //system os is < marshmallow
                openCamera()
            }
        }
        binding.GalleryButton.setOnClickListener {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    val permissions= arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE_GALLERY)
                }else{
                    pickImageFromGallery();
                }
            }else{
                pickImageFromGallery();
            }
        }
    }

    private fun pickImageFromGallery() {
        val requestCode = 0
        val launchGalleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(launchGalleryIntent, requestCode)
        //val intent= Intent(Intent.ACTION_PICK)
        //intent.type="image/*"
        //startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    openCamera()
                } else {
                    //permission from popup was denied
                    Toast.makeText(this, PERMISSION_DENIED_MESSAGE, Toast.LENGTH_SHORT).show()
                }
            }
            PERMISSION_CODE_GALLERY -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    pickImageFromGallery()
                } else {
                    //permission from popup was denied
                    Toast.makeText(this, PERMISSION_DENIED_MESSAGE, Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //called when image was captured from camera intent
        if(resultCode == Activity.RESULT_OK && requestCode==IMAGE_CAPTURE_CODE) {
            //set image captured to image view
            val intent = Intent(this, ImageEditingActivity::class.java)
            intent.putExtra("img", imageUri.toString())
            startActivity(intent)
        }
        //adds obtained image from gallery to image view
        else if (requestCode == 0 && resultCode == Activity.RESULT_OK ) {
            imageUri = data?.data
            val intent = Intent(this, ImageEditingActivity::class.java)
            intent.putExtra("img", imageUri.toString())
            startActivity(intent)

        }
    }
}