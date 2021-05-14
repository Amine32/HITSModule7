package com.example.imageapp3.cube

class Face(var nodes: Array<Node>) {
    //    val nubNodes = arrayOf(
//        Node()
//    )

    fun isVisible(): Boolean {
        val node1 = this.nodes[0]
        val node2 = this.nodes[1]
        val node3 = this.nodes[2]

        val a = (node2.y * node3.z - node2.z * node3.y) +
                (node1.z * node3.y - node1.y * node3.z) +
                (node1.y * node2.z - node1.z * node2.y)

        val b = (node2.z * node3.x - node2.x * node3.z) +
                (node1.x * node3.z - node1.z * node3.x) +
                (node1.z * node2.x - node1.x * node2.z)

        val c = (node2.x * node3.y - node2.y * node3.x) +
                (node1.y * node3.x - node1.x * node3.y) +
                (node1.x * node2.y - node1.y * node2.x)

        val d = - (a * node1.x + b * node1.y + c * node1.z)

        var isVisible: Boolean = c >= 0
        if (a + b + c + d > 0) isVisible = !isVisible

        return isVisible
    }
}