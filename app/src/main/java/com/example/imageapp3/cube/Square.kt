package com.example.imageapp3.cube

import kotlin.math.sqrt

object Square: Figure() {
    val squareDiagonal = Visualiser.distFromCenter * sqrt(2F) / 2
    override var nodes = arrayOf(
        Node(
            -Visualiser.distFromCenter,
            Visualiser.distFromCenter,
            0F
        ),
        Node(
            -Visualiser.distFromCenter,
            -Visualiser.distFromCenter,
            0F
        ),
        Node(
            Visualiser.distFromCenter,
            -Visualiser.distFromCenter,
            0F
        ),
        Node(
            Visualiser.distFromCenter,
            Visualiser.distFromCenter,
            0F
        ),
    )
    override var faces = arrayOf(Face(arrayOf(nodes[0],nodes[1],nodes[2],nodes[3],)))
}