package com.example.imageapp3.imageEditing

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
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
            binding.testHeight.text = (height * coef).toString()
            binding.testWidth.text = (width * coef).toString()

            //gate keeping memory leak
            if (height * coef <= 5750 && width * coef >= 0.5) {
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
                else {
                    resizedBitmap = bm.triscale(bm, resizedBitmap, coef)
                }
                bm.recycle()
                ImageEditingActivity.imageView?.setImageBitmap(resizedBitmap)
            }

            //show error message
            else {
                if (coef > 1) {
                    val MEMORY_EXCEEDED: String =
                        "Choose scaling coefficient smaller than " + (5750 / height)
                    Toast.makeText(activity, MEMORY_EXCEEDED, Toast.LENGTH_LONG).show()
                } else {
                    val MEMORY_EXCEEDED: String = "Choose scaling coefficient bigger than $coef"
                    Toast.makeText(activity, MEMORY_EXCEEDED, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

operator fun Int.get(n: Int) = (this shr (n * 8)) and 0xFF

fun lerp(s: Float, e: Float, t: Float) = s + (e - s) * t

fun blerp(c00: Float, c10: Float, c01: Float, c11: Float, tx: Float, ty: Float) =
    lerp(lerp(c00, c10, tx), lerp(c01, c11, tx), ty)

fun Bitmap.biscale(bm: Bitmap, newImage: Bitmap): Bitmap {
    for (x in 0 until newImage.width) {
        for (y in 0 until newImage.height) {
            val gx = x.toFloat() / (newImage.width) * (bm.width - 1)
            val gy = y.toFloat() / (newImage.height) * (bm.height - 1)
            val gxi = gx.toInt()
            val gyi = gy.toInt()
            var rgb = 0
            val c00 = bm.getPixel(gxi, gyi)
            val c10 = bm.getPixel(gxi + 1, gyi)
            val c01 = bm.getPixel(gxi, gyi + 1)
            val c11 = bm.getPixel(gxi + 1, gyi + 1)
            for (i in 0..3) {
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

fun Bitmap.triscale(bm: Bitmap, newImage: Bitmap, coef: Float): Bitmap {
    val mipmap1 = mipmap(bm, coef * bm.width, coef * bm.height, "upper")
    var mipmap1Pixels: IntArray = IntArray(mipmap1.width * mipmap1.height)
    mipmap1.getPixels(mipmap1Pixels, 0, mipmap1.width, 0, 0, mipmap1.width, mipmap1.height)

    val mipmap2 = mipmap(mipmap1, coef * bm.width, coef * bm.height, "lower")
    var mipmap2Pixels: IntArray = IntArray(mipmap2.width * mipmap2.height)
    mipmap2.getPixels(mipmap2Pixels, 0, mipmap2.width, 0, 0, mipmap2.width, mipmap2.height)

    val PIXELS = trilinearImageScaling(
        mipmap1Pixels,
        mipmap1.width,
        mipmap1.height,
        mipmap2Pixels,
        mipmap2.width,
        mipmap2.height,
        newImage.width,
        newImage.height
    )

    newImage.setPixels(PIXELS, 0, newImage.width, 0, 0, newImage.width, newImage.height)

    return newImage
}

fun mipmap(bm: Bitmap, width: Float, height: Float, state: String): Bitmap {
    // CREATE A MATRIX FOR THE MANIPULATION
    val matrix = Matrix()
    // RESIZE THE BIT MAP
    matrix.postScale(0.5f, 0.5f)

    // "RECREATE" THE NEW BITMAP
    var resizedMipmap = Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, false)

    if (state == "upper") {
        if (resizedMipmap.height / 2 > height) {
            resizedMipmap = mipmap(resizedMipmap, width, height, "upper")
        }
    } else {
        //do nothing
    }

    return resizedMipmap
}


fun trilinearImageScaling(
    pixels: IntArray, w: Int, h: Int,  // larger image
    pixels2: IntArray, w2: Int, h2: Int,  // smaller image
    width: Int, height: Int
): IntArray? {
    val temp = IntArray(width * height)
    var index: Int
    var index2: Int
    var A: Int
    var B: Int
    var C: Int
    var D: Int
    var E: Int
    var F: Int
    var G: Int
    var H: Int
    var x: Float
    var y: Float
    var x2: Float
    var y2: Float
    var w_diff: Float
    var h_diff: Float
    var w2_diff: Float
    var h2_diff: Float
    var red: Float
    var green: Float
    var blue: Float
    // find ratio for larger image
    val w_ratio = (w - 1).toFloat() / width
    val h_ratio = (h - 1).toFloat() / height
    // find ratio for smaller image
    val w2_ratio = (w2 - 1).toFloat() / width
    val h2_ratio = (h2 - 1).toFloat() / height
    // estimate h3 distance
    val h3_diff = (w - width) / (w - w2).toFloat()
    var offset = 0
    for (i in 0 until height) {
        for (j in 0 until width) {
            // find w1 and h1 for larger image
            x = w_ratio * j
            y = h_ratio * i
            w_diff = x - x.toInt()
            h_diff = y - y.toInt()
            index = y.toInt() * w + x.toInt()
            A = pixels[index]
            B = pixels[index + 1]
            C = pixels[index + w]
            D = pixels[index + w + 1]
            // find w2 and h2 for smaller image
            x2 = w2_ratio * j
            y2 = h2_ratio * i
            w2_diff = x2 - x2.toInt()
            h2_diff = y2 - y2.toInt()
            index2 = y2.toInt() * w2 + x2.toInt()
            E = pixels2[index2]
            F = pixels2[index2 + 1]
            G = pixels2[index2 + w2]
            H = pixels2[index2 + w2 + 1]

            // trilinear interpolate blue element
            blue =
                (A and 0xff) * (1 - w_diff) * (1 - h_diff) * (1 - h3_diff) + (B and 0xff) * w_diff * (1 - h_diff) * (1 - h3_diff) + (C and 0xff) * h_diff * (1 - w_diff) * (1 - h3_diff) + (D and 0xff) * w_diff * h_diff * (1 - h3_diff) + (E and 0xff) * (1 - w2_diff) * (1 - h2_diff) * h3_diff + (F and 0xff) * w2_diff * (1 - h2_diff) * h3_diff + (G and 0xff) * h2_diff * (1 - w2_diff) * h3_diff + (H and 0xff) * w2_diff * h2_diff * h3_diff

            // trilinear interpolate green element
            green =
                (A shr 8 and 0xff) * (1 - w_diff) * (1 - h_diff) * (1 - h3_diff) + (B shr 8 and 0xff) * w_diff * (1 - h_diff) * (1 - h3_diff) + (C shr 8 and 0xff) * h_diff * (1 - w_diff) * (1 - h3_diff) + (D shr 8 and 0xff) * w_diff * h_diff * (1 - h3_diff) + (E shr 8 and 0xff) * (1 - w2_diff) * (1 - h2_diff) * h3_diff + (F shr 8 and 0xff) * w2_diff * (1 - h2_diff) * h3_diff + (G shr 8 and 0xff) * h2_diff * (1 - w2_diff) * h3_diff + (H shr 8 and 0xff) * w2_diff * h2_diff * h3_diff

            // trilinear interpolate red element
            red =
                (A shr 16 and 0xff) * (1 - w_diff) * (1 - h_diff) * (1 - h3_diff) + (B shr 16 and 0xff) * w_diff * (1 - h_diff) * (1 - h3_diff) + (C shr 16 and 0xff) * h_diff * (1 - w_diff) * (1 - h3_diff) + (D shr 16 and 0xff) * w_diff * h_diff * (1 - h3_diff) + (E shr 16 and 0xff) * (1 - w2_diff) * (1 - h2_diff) * h3_diff + (F shr 16 and 0xff) * w2_diff * (1 - h2_diff) * h3_diff + (G shr 16 and 0xff) * h2_diff * (1 - w2_diff) * h3_diff + (H shr 16 and 0xff) * w2_diff * h2_diff * h3_diff
            temp[offset++] = -0x1000000 or  // hardcode alpha
                    blue.toInt() or (
                    green.toInt() shl 8) or (
                    red.toInt() shl 16)
        }
    }
    return temp
}
