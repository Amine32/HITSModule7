package com.example.imageapp3.cube

import kotlin.math.sqrt

object Cube: Figure() {
    private val cubeDiagonal = Visualiser.distFromCenter * sqrt(3F) / 2
    override var nodes = arrayOf(
        Node(
            -cubeDiagonal,
            cubeDiagonal,
            cubeDiagonal
        ),
        Node(
            -cubeDiagonal,
            -cubeDiagonal,
            cubeDiagonal
        ),
        Node(
            cubeDiagonal,
            -cubeDiagonal,
            cubeDiagonal
        ),
        Node(
            cubeDiagonal,
            cubeDiagonal,
            cubeDiagonal
        ),
        Node(
            -cubeDiagonal,
            cubeDiagonal,
            -cubeDiagonal
        ),
        Node(
            -cubeDiagonal,
            -cubeDiagonal,
            -cubeDiagonal
        ),
        Node(
            cubeDiagonal,
            -cubeDiagonal,
            -cubeDiagonal
        ),
        Node(
            cubeDiagonal,
            cubeDiagonal,
            -cubeDiagonal
        ),
    )
    override var faces = arrayOf(
        Face(arrayOf(nodes[0], nodes[1], nodes[2], nodes[3])),
        Face(arrayOf(nodes[0], nodes[1], nodes[5], nodes[4])),
        Face(arrayOf(nodes[4], nodes[5], nodes[6], nodes[7])),
        Face(arrayOf(nodes[2], nodes[3], nodes[7], nodes[6])),
        Face(arrayOf(nodes[1], nodes[2], nodes[6], nodes[5])),
        Face(arrayOf(nodes[0], nodes[3], nodes[7], nodes[4])),
    )
}