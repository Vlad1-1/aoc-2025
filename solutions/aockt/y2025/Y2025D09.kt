package aockt.y2025

import io.github.jadarma.aockt.core.Solution
import kotlin.math.abs

object Y2025D09 : Solution {
    private fun Pair<Long, Long>.area(other: Pair<Long, Long>) =
        (abs(this.first - other.first) + 1) * (abs(this.second - other.second) + 1)

    override fun partOne(input: String): Any {
        val corners = input.lines().map {
            val numbers = it.split(',').map(String::toLong)
            Pair(numbers.first(), numbers.last())
        }
        return corners.flatMap { a -> corners.map { b -> a to b } }.maxOf { (a, b) -> a.area(b) }
    }

    private const val GRID_SIZE = 20

    override fun partTwo(input: String): Any {
        val corners = buildList {
            input.lines().forEach {
                val numbers = it.split(',').map(String::toInt)
                add(Pair(numbers[1], numbers[0]))
            }
            add(first())
        }

        val verticalEdges = Array(GRID_SIZE) { mutableListOf<Int>() }
        val horizontalEdges = Array(GRID_SIZE) { mutableListOf<Int>() }

        //TODO: Horizontal line in input results in wrong out gate pushed into the edges

        corners.zipWithNext().filter { (a, b) -> a.second == b.second }.map { (a, b) ->
            Pair(a.second, minOf(a.first, b.first)..maxOf(a.first, b.first))
        }.sortedBy { it.first }.forEach { (y, range) ->
            for (i in range) {
                if(verticalEdges[i].size % 2 == 0) {
                    if (verticalEdges[i].lastOrNull() == y)
                        verticalEdges[i].removeLast()
                    else
                        verticalEdges[i].add(y)
                }
                else {
                    verticalEdges[i].add(y + 1)
                }
            }
        }

        corners.zipWithNext().filter { (a, b) -> a.first == b.first }.map { (a, b) ->
            Pair(a.first, minOf(a.second, b.second)..maxOf(a.second, b.second))
        }.sortedBy { it.first }.forEach { (x, range) ->
            for (i in range) {
                if(horizontalEdges[i].size % 2 == 0) {
                    if (horizontalEdges[i].lastOrNull() == x)
                        horizontalEdges[i].removeLast()
                    else
                        horizontalEdges[i].add(x)
                }
                else {
                    horizontalEdges[i].add(x + 1)
                }
            }
        }
        val binSearchPositive = { it: Int ->
            if (it < 0) -it - 2 else it
            //TODO: Slightly wrong calculations
        }

        return corners.flatMap { a -> corners.map { b -> a to b } }.filter { (a, b) ->
            val upperLine = horizontalEdges[a.first].binarySearch(a.second).let(binSearchPositive)
            val upperLineSecond = horizontalEdges[a.first].binarySearch(b.second).let(binSearchPositive)
            if (upperLine % 2 == 0 || upperLine != upperLineSecond)
                return@filter false

            val leftLine = verticalEdges[a.second].binarySearch(a.first).let(binSearchPositive)
            val leftLineSecond = verticalEdges[a.second].binarySearch(b.first).let(binSearchPositive)
            if (leftLine % 2 == 0 || leftLine != leftLineSecond)
                return@filter false

            val lowerLine = horizontalEdges[b.first].binarySearch(a.second).let(binSearchPositive)
            val lowerLineSecond = horizontalEdges[b.first].binarySearch(b.second).let(binSearchPositive)
            if (lowerLine % 2 == 0 || lowerLine != lowerLineSecond)
                return@filter false

            val rightLine = verticalEdges[b.second].binarySearch(a.first).let(binSearchPositive)
            val rightLineSecond = verticalEdges[b.second].binarySearch(b.first).let(binSearchPositive)
            if (rightLine % 2 == 0 || rightLine != rightLineSecond)
                return@filter false
            true
        }.maxOf { (a, b) -> Pair(a.first.toLong(), a.second.toLong()).area(Pair(b.first.toLong(), b.second.toLong())) }
    }
}