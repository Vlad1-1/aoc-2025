package aockt.y2025

import io.github.jadarma.aockt.core.Solution
import kotlin.text.indexOf

object Y2025D07 : Solution {
    private fun parseInput(input: String) =
        Pair(
            input.lines().first().indexOf('S'),
            input.lines().drop(2).filterIndexed { index, _ -> index % 2 == 0 }.map {
                it.withIndex()
                    .filter { (_, c) -> c == '^' }
                    .map(IndexedValue<*>::index)
            }
        )

    override fun partOne(input: String): Int {
        val (start, layers) = parseInput(input)
        var beams = mutableSetOf(start)
        var ans = 0
        for (layer in layers) {
            val newBeams = mutableSetOf<Int>()
            for (beam in beams) {
                newBeams.addAll(
                    if (layer.contains(beam)) {
                        ans += 1
                        listOf(beam - 1, beam + 1)
                    } else {
                        listOf(beam)
                    }
                )
            }
            beams = newBeams
        }
        return ans
    }

    private fun MutableMap<Int, Long>.addCount(key: Int, count: Long) {
        this[key] = (this[key] ?: 0) + count
    }

    override fun partTwo(input: String): Long {
        val (start, layers) = parseInput(input)
        var beams = mutableMapOf(start to 1L)
        for (layer in layers) {
            println(beams.size)
            val newBeams = mutableMapOf<Int, Long>()
            for ((beam, times) in beams) {
                if (beam in layer) {
                    newBeams.addCount(beam - 1, times)
                    newBeams.addCount(beam + 1, times)
                } else {
                    newBeams.addCount(beam, times)
                }
            }
            beams = newBeams
        }
        return beams.values.sum()
    }
}