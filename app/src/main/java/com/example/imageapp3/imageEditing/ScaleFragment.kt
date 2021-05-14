package com.example.imageapp3.imageEditing

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imageapp3.R
import com.example.imageapp3.databinding.FragmentScaleBinding

class ScaleFragment : Fragment(R.layout.fragment_scale) {
    private val binding by viewBinding(FragmentScaleBinding::bind, R.id.scaleFrameLayout)

    companion object {
        val TAG = ScaleFragment::class.java.simpleName

        fun newInstance() = ScaleFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resizeButton.setOnClickListener {
            val coef = binding.scaleCoef.text.toString().toFloat()
            val bm = (ImageEditingActivity.imageView?.drawable as BitmapDrawable).bitmap
            val width = bm.width
            val height = bm.height

            //display new width and height for testing purpose
            binding.testHeight.text = (height*coef).toString()
            binding.testWidth.text = (width*coef).toString()

            //gate keeping memory leak
            if(height * coef <= 5750 && width * coef >= 0.5) {
                // CREATE A MATRIX FOR THE MANIPULATION
                val matrix = Matrix()
                // RESIZE THE BIT MAP
                matrix.postScale(coef, coef)

                // "RECREATE" THE NEW BITMAP
                val resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
                bm.recycle()

                ImageEditingActivity.imageView?.setImageBitmap(resizedBitmap)
            }
            //show error message
            else {
                if(coef > 1) {
                    val MEMORY_EXCEEDED: String = "Choose scaling coefficient smaller than " + (5750 / height)
                    Toast.makeText(activity, MEMORY_EXCEEDED, Toast.LENGTH_LONG).show()
                }
                else {
                    val MEMORY_EXCEEDED: String = "Choose scaling coefficient bigger than $coef"
                    Toast.makeText(activity, MEMORY_EXCEEDED, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}