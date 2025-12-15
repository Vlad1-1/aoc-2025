package aockt.y2025

import io.github.jadarma.aockt.core.Solution

class Y2025D12 : Solution {

    private data class Field(val fieldSize: Pair<Int, Int>, val requiredShapes: List<Int>)

    private fun parseInput(input: String): Pair<List<Int>, List<Field>> {
        val parts = input.split("\n\n")
        val shapes = parts.dropLast(1).map { it.count { c -> c == '#' } }
        val fields = parts.last().lines().map {
            val numbers = it.split(Regex("(x)|(:? )")).map(String::toInt)
            Field(numbers[0] to numbers[1], numbers.drop(2))
        }
        return shapes to fields
    }

    override fun partOne(input: String): Int {
        val (shapes, fields) = parseInput(input)
        return fields.count { (field, shape) ->
            val area = field.first * field.second
            shape.withIndex().sumOf { shapes[it.index] * it.value } <= area
        }
    }
}