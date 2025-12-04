package aockt.y2025

import io.github.jadarma.aockt.core.Solution

object Y2025D03 : Solution {
    private fun parseInput(input: String) = input.lines().map { it.map(Char::digitToInt) }
    override fun partOne(input: String) =
        parseInput(input).sumOf {
            var maxIndex = 0
            for (i in 1 until it.size - 1) {
                if (it[i] > it[maxIndex]) {
                    maxIndex = i
                }
            }
            var maxSecondIndex = maxIndex + 1
            for (i in maxIndex + 2 until it.size) {
                if (it[i] > it[maxSecondIndex]) {
                    maxSecondIndex = i
                }
            }
            it[maxIndex] * 10 + it[maxSecondIndex]
        }

    override fun partTwo(input: String) =
        parseInput(input).sumOf {
            var goodIndices = 0..it.size - 12
            var result: Long = 0
            while (goodIndices.last < it.size) {
                var maxIndex = goodIndices.first
                for (j in goodIndices)
                    if (it[j] > it[maxIndex])
                        maxIndex = j
                result = result * 10 + it[maxIndex]
                goodIndices = maxIndex + 1..goodIndices.last + 1
            }
            result
        }
}