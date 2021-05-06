package com.example.imageapp3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imageapp3.databinding.FragmentFilterBinding

class FilterFragment : Fragment(R.layout.fragment_filter) {
    private val binding by viewBinding(FragmentFilterBinding::bind, R.id.filterFragmentLayout)

    companion object {
        val TAG = FilterFragment::class.java.simpleName

        fun newInstance() = FilterFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.filterRedBtn.setOnClickListener {
            changePixelsToRed()
        }
    }

    private fun changePixelsToRed() {
        ImageEditingActivity.pixelArray?.let {
            for (pixelIndex in it.indices) {
                it[pixelIndex] = changePixelColor(it[pixelIndex], ColorMatrices.FullRed.matrix)
            }

        }
    }

    private fun changePixelColor(pixel:Int, matrix: FloatArray): Int {
        var ARGB: IntArray = intArrayOf(pixel and 0xFF000000.toInt(), pixel and 0x00FF0000, pixel and 0x0000FF00, pixel and 0x000000FF)

        var newA: Float = ARGB[0] * matrix[0*5 + 0] + ARGB[1] * matrix[0*5 + 1] + ARGB[2] * matrix[0*5 + 2] + ARGB[3] * matrix[0*5 + 3] + matrix[0*5 + 4]
        var newR: Float = ARGB[0] * matrix[1*5 + 0] + ARGB[1] * matrix[1*5 + 1] + ARGB[2] * matrix[1*5 + 2] + ARGB[3] * matrix[1*5 + 3] + matrix[1*5 + 4]
        var newG: Float = ARGB[0] * matrix[2*5 + 0] + ARGB[1] * matrix[2*5 + 1] + ARGB[2] * matrix[2*5 + 2] + ARGB[3] * matrix[2*5 + 3] + matrix[2*5 + 4]
        var newB: Float = ARGB[0] * matrix[3*5 + 0] + ARGB[1] * matrix[3*5 + 1] + ARGB[2] * matrix[3*5 + 2] + ARGB[3] * matrix[3*5 + 3] + matrix[3*5 + 4]

        var newARGB: Int = newA.toInt() shl 24 + newR.toInt() shl 16 + newG.toInt() shl 8 + newB.toInt()
        return newARGB
    }
}