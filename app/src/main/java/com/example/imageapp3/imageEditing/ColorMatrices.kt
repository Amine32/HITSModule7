package com.example.imageapp3.imageEditing

enum class ColorMatrices(val matrix: FloatArray) {
    Inverted(floatArrayOf(
        1F, 0F, 0F, 0F, 0F,
        0F, -1F, 0F, 0F, 255F,
        0F, 0F, -1F, 0F, 255F,
        0F, 0F, 0F, -1F, 255F,
    )),
    BlackAndWhite(floatArrayOf(
        1F, 0F, 0F, 0F, 0F,
        0F, 0.33F, 0.33F, 0.33F, 0F,
        0F, 0.33F, 0.33F, 0.33F, 0F,
        0F, 0.33F, 0.33F, 0.33F, 0F
    )),
    AGBR(floatArrayOf(
        1F, 0F, 0F, 0F, 0F,
        0F, 0F, 1F, 0F, 0F,
        0F, 0F, 0F, 1F, 0F,
        0F, 1F, 0F, 0F, 0F,
    ))
}