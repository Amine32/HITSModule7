package com.example.imageapp3

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
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
            // CREATE A MATRIX FOR THE MANIPULATION
            val matrix = Matrix()
            // RESIZE THE BIT MAP
            matrix.postScale(coef, coef)

            // "RECREATE" THE NEW BITMAP
            val resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
            bm.recycle()

            ImageEditingActivity.imageView?.setImageBitmap(resizedBitmap)
        }
    }
}