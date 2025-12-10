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

    private const val GRID_SIZE = 100000

    override fun partTwo(input: String): Any {
        val corners = input.lines().map {
            val numbers = it.split(',').map(String::toInt)
            Pair(numbers.first(), numbers.last())
        }
        val grid = Array(GRID_SIZE) { BooleanArray(GRID_SIZE) { false } }
        for ((start, end) in corners.zipWithNext()) {
            grid[start.second][start.first] = true
            if (start.first == end.first) {
                for (y in minOf(start.second, end.second)..maxOf(start.second, end.second)) {
                    grid[y][start.first] = true
                }
            } else {
                for (x in minOf(start.first, end.first)..maxOf(start.first, end.first)) {
                    grid[start.second][x] = true
                }
            }
        }
        return Unit
    }
}