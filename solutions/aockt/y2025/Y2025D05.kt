package aockt.y2025

import io.github.jadarma.aockt.core.Solution
import java.util.*

object Y2025D05 : Solution {
    private fun parseInput(input: String): Pair<List<LongRange>, List<Long>> {
        val (ranges, ingredients) = input.split("\n\n").map(String::lines)
        return Pair(
            ranges.map {
                val (first, last) = it.split('-')
                first.toLong()..last.toLong()
            },
            ingredients.map(String::toLong)
        )
    }

    private fun computeSet(ranges: List<LongRange>): TreeSet<Pair<Long, Boolean>> {
        val sortedByFirst = ranges.sortedBy { it.first }
        val sortedByLast = ranges.sortedBy { it.last }
        val set = sortedSetOf<Pair<Long, Boolean>>(comparator = compareBy { it.first })
        val firstIt = sortedByFirst.iterator()
        val lastIt = sortedByLast.iterator()
        var currentNesting = 0
        var first = firstIt.next().first
        var last = lastIt.next().last
        while (first != Long.MAX_VALUE || last != Long.MAX_VALUE) {
            if (first <= last + 1) {
                if (currentNesting++ == 0)
                    set.add(Pair(first, true))
                first = if (firstIt.hasNext()) firstIt.next().first else Long.MAX_VALUE
            } else {
                if (--currentNesting == 0)
                    set.add(Pair(last + 1, false))
                last = if (lastIt.hasNext()) lastIt.next().last else Long.MAX_VALUE
            }
        }
        return set
    }

    override fun partOne(input: String): Any {
        val (ranges, ingredients) = parseInput(input)
        val set = computeSet(ranges)
        return ingredients.count { set.floor(Pair(it, true))?.second == true }
    }

    override fun partTwo(input: String) =
        computeSet(parseInput(input).first)
            .zipWithNext()
            .filter { it.first.second }
            .sumOf { it.second.first - it.first.first }
}