package com.example.imageapp3.imageEditing

import android.graphics.Bitmap
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.imageapp3.R
import com.example.imageapp3.databinding.FragmentSharpeningBinding

class SharpeningFragment : Fragment(R.layout.fragment_sharpening) {
    private val binding by viewBinding(
        FragmentSharpeningBinding::bind,
        R.id.sharpeningFragmentLayout
    )
    var amount: Float = 0.0f
    var radius: Float = 0.0f
    var threshold: Int = 0

    companion object {
        val TAG: String = SharpeningFragment::class.java.simpleName

        fun newInstance() = SharpeningFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.seekBar3.min=1     //requires API level 26
        binding.seekBar1.max = 255
        binding.seekBar3.max = 20
        binding.seekBar1.progress = 0
        binding.seekBar2.progress = 100
        binding.seekBar3.progress = 10
        binding.thresholdtextview.text = binding.seekBar1.progress.toString()
        binding.amounttextview.text = binding.seekBar2.progress.toString()
        binding.radiustextview.text = binding.seekBar3.progress.toString()


        binding.sharpenimage.setOnClickListener {
            amount = binding.amounttextview.text.toString().toFloat()
            radius = binding.radiustextview.text.toString().toFloat()
            threshold = binding.thresholdtextview.text.toString().toInt()
            var duplicate = ImageEditingActivity.bitmap
            var cpy = ImageEditingActivity.bitmap
            var blurredImage = blur(duplicate!!, radius)
            duplicate = filteredImage(duplicate, blurredImage, amount, threshold)
            duplicate = addImages(duplicate, cpy!!)
            ImageEditingActivity.imageView?.setImageBitmap(duplicate)

        }


        binding.seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.thresholdtextview.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        binding.seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.amounttextview.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        binding.seekBar3.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress != 0) {
                    binding.radiustextview.text = progress.toString()
                } else {
                    binding.radiustextview.text = "1"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    fun blur(original: Bitmap, radius: Float): Bitmap {
        var bitmap: Bitmap =
            Bitmap.createBitmap(original.width, original.height, Bitmap.Config.ARGB_8888)
        var rs = RenderScript.create(activity)
        var allocIn = Allocation.createFromBitmap(rs, original)
        var allocOut = Allocation.createFromBitmap(rs, bitmap)
        var boxblur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        boxblur.setInput(allocIn)
        boxblur.setRadius(radius)
        boxblur.forEach(allocOut)
        allocOut.copyTo(bitmap)
        rs.destroy()
        return bitmap

    }

    fun filteredImage(original: Bitmap, blurred: Bitmap, amount: Float, threshold: Int): Bitmap {
        var bitmap: Bitmap = original.copy(Bitmap.Config.ARGB_8888, true)
        for (x in 0 until original.width) {
            for (y in 0 until original.height) {
                val argb1: Int = original.getPixel(x, y)
                val argb2: Int = blurred.getPixel(x, y)


                val r1 = argb1 shr 16 and 0xFF
                val g1 = argb1 shr 8 and 0xFF
                val b1 = argb1 and 0xFF

                val r2 = argb2 shr 16 and 0xFF
                val g2 = argb2 shr 8 and 0xFF
                val b2 = argb2 and 0xFF

                val rDiff = if (Math.abs(r2 - r1) >= threshold) Math.abs(r2 - r1) else 0
                val gDiff = if (Math.abs(g2 - g1) >= threshold) Math.abs(g2 - g1) else 0
                val bDiff = if (Math.abs(b2 - b1) >= threshold) Math.abs(b2 - b1) else 0
                val alpha = ((amount / 100) * 255).toInt()
                val diff = (alpha shl 24) or (rDiff shl 16) or (gDiff shl 8) or bDiff
                bitmap.setPixel(x, y, diff)

            }
        }


        return bitmap
    }

    fun addImages(original: Bitmap, filtered: Bitmap): Bitmap {
        var bitmap: Bitmap = original.copy(Bitmap.Config.ARGB_8888, true)
        for (x in 0 until original.width) {
            for (y in 0 until original.height) {
                val argb1: Int = original.getPixel(x, y)
                val argb2: Int = filtered.getPixel(x, y)


                val r1 = argb1 shr 16 and 0xFF
                val g1 = argb1 shr 8 and 0xFF
                val b1 = argb1 and 0xFF

                val r2 = argb2 shr 16 and 0xFF
                val g2 = argb2 shr 8 and 0xFF
                val b2 = argb2 and 0xFF

                val rDiff = if (r2 + r1 > 255) 255 else r2 + r1
                val gDiff = if (g2 + g1 > 255) 255 else g2 + g1
                val bDiff = if (b2 + b1 > 255) 255 else b2 + b1
                val diff = (255 shl 24) or (rDiff shl 16) or (gDiff shl 8) or bDiff
                bitmap.setPixel(x, y, diff)
            }
        }
        return bitmap


    }

}