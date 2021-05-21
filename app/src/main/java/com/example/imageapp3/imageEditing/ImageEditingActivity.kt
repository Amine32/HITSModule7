package com.example.imageapp3.imageEditing

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imageapp3.R
import com.example.imageapp3.databinding.ActivityImageEditingBinding

class ImageEditingActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityImageEditingBinding::bind, R.id.imageEditingLayout)

    companion object {
        var pixelArray: IntArray? = null
        var imageUri: Uri? = null
        var bitmap: Bitmap? = null
        var imageView: ImageView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() // hide the title bar
        setContentView(R.layout.activity_image_editing)


        imageView = binding.uploadedPhotoImageView
        imageUri = Uri.parse(intent.getStringExtra("img"))
        imageView?.setImageURI(imageUri)
        bitmap = imageView?.drawable?.toBitmap()
        bitmap = bitmap?.copy(Bitmap.Config.ARGB_8888, true)

        bitmap?.let {
            pixelArray = IntArray(it.width * it.height)
            it.getPixels(pixelArray, 0, it.width, 0, 0, it.width, it.height)
        }

        binding.imageEditingBottomNavView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.rotateFragSelect -> {
                    selectScreen(RotateFragment.TAG, RotateFragment.newInstance())
                    true
                }
                R.id.filterFragSelect -> {
                    selectScreen(FilterFragment.TAG, FilterFragment.newInstance())
                    true
                }
                R.id.scaleFragSelect -> {
                    selectScreen(ScaleFragment.TAG, ScaleFragment.newInstance())
                    true
                }
                R.id.sharpeningFragSelect -> {
                    selectScreen(SharpeningFragment.TAG, SharpeningFragment.newInstance())
                    true
                }
                else -> false
            }
        }


    }

    private fun selectScreen(tag: String, fragment: Fragment) {
        supportFragmentManager.commit {
            val active = findActiveFragment()
            val target = supportFragmentManager.findFragmentByTag(tag)

            if (active != null && target != null && active == target) return@commit

            if (active != null) {
                hide(active)
            }

            if (target == null) {
                replace(R.id.fragmentContainer, fragment, tag)
            } else {
                show(target)
            }
        }
    }

    private fun findActiveFragment() = supportFragmentManager.fragments.find {it.isVisible}
}