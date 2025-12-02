package aockt.y2025

import io.github.jadarma.aockt.core.Solution

object Y2025D02 : Solution {
    fun parseInput(input: String) = input.split(',').map {
        val numbers = it.split('-')
        assert(numbers.size == 2)
        numbers[0].toULong()..numbers[1].toULong()
    }

    override fun partOne(input: String) =
        parseInput(input).flatMap { range ->
            range.filter {
                val str = it.toString()
                val half = str.length / 2
                str.take(half) == str.substring(half)
            }
        }.sum()

    override fun partTwo(input: String) =
        parseInput(input).flatMap { range ->
            range.filter {
                val str = it.toString()
                val len = str.length
                for (i in len - len / 2 until len) {
                    if (len % (len - i) != 0) continue
                    if (str.take(i) == str.substring(len - i)) {
                        return@filter true
                    }
                }
                return@filter false
            }
        }.sum()
}