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
                var resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)

                //bilinear scaling
                if (coef > 1) {
                    resizedBitmap = bm.biscale(bm, resizedBitmap)
                }
                //trilinear scaling
                else{

                }
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

operator fun Int.get(n: Int) = (this shr (n * 8)) and 0xFF

fun lerp(s: Float, e: Float, t: Float) = s + (e - s) * t

fun blerp(c00: Float, c10: Float, c01: Float, c11: Float, tx:Float, ty: Float) =
    lerp(lerp(c00, c10, tx), lerp(c01, c11, tx), ty)

fun Bitmap.biscale(bm: Bitmap, newImage: Bitmap): Bitmap {
    for(x in 0 until newImage.width) {
        for(y in 0 until newImage.height) {
            val gx = x.toFloat() / (newImage.width) * (bm.width - 1)
            val gy = y.toFloat() / (newImage.height) * (bm.height - 1)
            val gxi = gx.toInt()
            val gyi = gy.toInt()
            var rgb = 0
            val c00 = bm.getPixel(gxi, gyi)
            val c10 = bm.getPixel(gxi + 1, gyi)
            val c01 = bm.getPixel(gxi, gyi + 1)
            val c11 = bm.getPixel(gxi + 1, gyi + 1)
            for(i in 0..3) {
                val b00 = c00[i].toFloat()
                val b10 = c10[i].toFloat()
                val b01 = c01[i].toFloat()
                val b11 = c11[i].toFloat()
                val ble = blerp(b00, b10, b01, b11, gx - gxi, gy - gyi).toInt() shl (8 * i)
                rgb = rgb or ble
            }
            newImage.setPixel(x, y, rgb)
        }
    }
    return newImage
}

fun Bitmap.triscale(bm: Bitmap, newImage: Bitmap) {

}

