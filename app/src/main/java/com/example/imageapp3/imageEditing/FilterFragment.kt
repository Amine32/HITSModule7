package com.example.imageapp3.imageEditing

import android.graphics.Color.*
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imageapp3.R
import com.example.imageapp3.databinding.FragmentFilterBinding


class FilterFragment : Fragment(R.layout.fragment_filter) {
    private val binding by viewBinding(FragmentFilterBinding::bind, R.id.filterFragmentLayout)

    companion object {
        val TAG:String = FilterFragment::class.java.simpleName

        fun newInstance() = FilterFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.invertedFilterBtn.setOnClickListener {
            applyFilter(ColorMatrices.Inverted)
        }
        binding.grayFilterBtn.setOnClickListener {
            applyFilter(ColorMatrices.BlackAndWhite)
        }
        binding.agbrFilterBtn.setOnClickListener {
            applyFilter(ColorMatrices.AGBR)
        }
    }

    private fun applyFilter(colorMatrix: ColorMatrices) {
        ImageEditingActivity.pixelArray?.let {
            for (pixelIndex in it.indices) {
                it[pixelIndex] = changePixelColor(it[pixelIndex], colorMatrix.matrix)
            }
        }
        ImageEditingActivity.bitmap?.let {
            it.setPixels(ImageEditingActivity.pixelArray, 0, it.width, 0,0, it.width, it.height)
            ImageEditingActivity.imageView?.setImageBitmap(it)
        }

    }

    private fun changePixelColor(pixel: Int, matrix: FloatArray): Int {

        val a: Int = alpha(pixel)
        val r: Int = red(pixel)
        val g: Int = green(pixel)
        val b: Int = blue(pixel)

        val newA: Int =
            (a * matrix[0 * 5 + 0] + r * matrix[0 * 5 + 1] + g * matrix[0 * 5 + 2] + b * matrix[0 * 5 + 3] + matrix[0 * 5 + 4]).toInt()
        val newR: Int =
            (a * matrix[1 * 5 + 0] + r * matrix[1 * 5 + 1] + g * matrix[1 * 5 + 2] + b * matrix[1 * 5 + 3] + matrix[1 * 5 + 4]).toInt()
        val newG: Int =
            (a * matrix[2 * 5 + 0] + r * matrix[2 * 5 + 1] + g * matrix[2 * 5 + 2] + b * matrix[2 * 5 + 3] + matrix[2 * 5 + 4]).toInt()
        val newB: Int =
            (a * matrix[3 * 5 + 0] + r * matrix[3 * 5 + 1] + g * matrix[3 * 5 + 2] + b * matrix[3 * 5 + 3] + matrix[3 * 5 + 4]).toInt()

        return argb(newA, newR, newG, newB)
    }
}