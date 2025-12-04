package aockt.y2025

import io.github.jadarma.aockt.core.Solution

object Y2025D04 : Solution {
    override fun partOne(input: String): Any {
        val input = input.lines()
        var ans = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                var counter = 0
                if (input[i][j] != '@') continue
                for (x in i-1..i+1)
                    for (y in j-1..j+1)
                        if (input.getOrNull(x)?.getOrNull(y) == '@')
                            counter++
                if (counter <= 4) // Accounts for the fact that the node counted itself
                    ans++
            }
        }
        return ans
    }

    override fun partTwo(input: String): Any {
        val input = input.lines()
        val nodes = mutableMapOf<Int, Node>()
        val m = input[0].length
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] != '@') continue
                val node = nodes.getOrPut(i * m + j) { Node(mutableListOf(), 0) }
                for (x in i-1..i+1) {
                    for (y in j-1..j+1) {
                        if (input.getOrNull(x)?.getOrNull(y) == '@') {
                            val currentNode = nodes.getOrPut(x * m + y) { Node(mutableListOf(), 0) }
                            node.neighbours += currentNode
                            node.degree++
                        }
                    }
                }
            }
        }
        val queue = ArrayDeque<Node>()
        for (node in nodes.values) {
            if (node.degree <= 4) {
                queue.addLast(node)
            }
        }
        var ans = 0
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            if (node.degree == -1) continue
            ans++
            node.degree = -1
            for (neighbour in node.neighbours) {
                if (neighbour.degree == -1) continue
                if (--neighbour.degree == 4)
                    queue.addLast(neighbour)
            }
        }
        return ans
    }

    private data class Node(val neighbours: MutableList<Node>, var degree: Int)
}