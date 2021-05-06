package com.example.imageapp3

enum class ColorMatrices(val matrix: FloatArray) {
    FullRed(floatArrayOf(
        0F, 0F, 0F, 1F, 0F,
        1F, 0F, 0F, 0F, 0F,
        0F, 0F, 0F, 0F, 0F,
        0F, 0F, 0F, 0F, 0F,
    ))
}