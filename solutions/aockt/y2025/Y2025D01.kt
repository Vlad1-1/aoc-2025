package aockt.y2025

import io.github.jadarma.aockt.core.Solution
import kotlin.math.absoluteValue

object Y2025D01 : Solution {
    private fun parseInput(input: String): List<Int> =
        input.lines().map {
            it.substring(1).toInt() * when (it[0]) {
                'L' -> -1
                'R' -> 1
                else -> throw IllegalArgumentException("Invalid sign: ${it[0]}")
            }
        }

    override fun partOne(input: String): Int {
        var ans = 0
        var cursor = 50
        val input = parseInput(input)
        for (move in input) {
            cursor += move
            if (cursor % 100 == 0) ans += 1
        }
        return ans
    }



    override fun partTwo(input: String): Any {
        var ans = 0
        var oldCursor = 50
        var cursor = 50
        val input = parseInput(input)
        for (move in input) {
            cursor += move
            cursor %= 100
            if (cursor < 0) cursor += 100
            val overflows =
                if (move > 0)
                    (move + oldCursor) / 100
                else
                    (move + oldCursor - 99).absoluteValue / 100 - if (oldCursor == 0) 1 else 0
            ans += overflows
            if (move < 0 && cursor == 0) ans += 1
            oldCursor = cursor
        }
        return ans
    }
}