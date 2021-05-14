package com.example.imageapp3.imageEditing

enum class ColorMatrices(val matrix: FloatArray) {
    FullRed(floatArrayOf(
        0F, 0F, 0F, 0F, 255F,
        0F, 1F, 0F, 0F, 0F,
        0F, 0F, 0F, 0F, 0F,
        0F, 0F, 0F, 0F, 0F,
    )),
    Inverted(floatArrayOf(
        1F, 0F, 0F, 0F, 255F,
        0F, -1F, 0F, 0F, 255F,
        0F, 0F, -1F, 0F, 255F,
        0F, 0F, 0F, -1F, 255F,
    )),
    Gray(floatArrayOf(
        0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
        0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
        0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
        0.0f, 0.0f, 0.0f, 1.0f, 0.0f
    )),
    Default(floatArrayOf(
        1F, 0F, 0F, 0F, 0F,
        0F, 1F, 0F, 0F, 0F,
        0F, 0F, 1F, 0F, 0F,
        0F, 0F, 0F, 1F, 0F,
    )),
    FullBlack(floatArrayOf(
        0F, 0F, 0F, 0F, 0F,
        0F, 0F, 0F, 0F, 0F,
        0F, 0F, 0F, 0F, 0F,
        0F, 0F, 0F, 0F, 0F,
    ))
}