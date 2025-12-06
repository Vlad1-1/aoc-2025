package aockt.y2025

import io.github.jadarma.aockt.core.Solution

object Y2025D06 : Solution {
    private fun parseInput(input: String): Pair<List<List<Int>>, CharSequence> {
        val lines = input.lines()
        val numbers = lines.take(lines.size - 1).map { it.split(' ').filter(String::isNotEmpty).map {
            val nr = it.toIntOrNull()
            if (nr == null) {
                println("Invalid number: $it")
                error("Invalid number: $it")
            } else {
                nr
            }
        }}
        val ops = lines.last().filter { it != ' ' }
        return Pair(numbers, ops)
    }

    override fun partOne(input: String): Any {
        val (numbers, ops) = parseInput(input)
        var ans = 0L
        for (i in ops.indices) {
            var acc: Long = when (ops[i]) {
                '+' -> 0
                '*' -> 1
                else -> {
                    println(ops[i])
                    error("Unknown operation: ${ops[i]}")
                }
            }
            val op: (Long, Int) -> Long = when (ops[i]) {
                '+' -> { a, b -> a + b }
                '*' -> { a, b -> a * b }
                else -> error("Unknown operation: ${ops[i]}")
            }
            for (j in numbers.indices) {
                acc = op(acc, numbers[j][i])
            }
            ans += acc
        }
        return ans
    }

    override fun partTwo(input: String): Any {
        val lines = input.lines()
        val numbers = lines.take(lines.size - 1)
        val ops = lines.last()
        var currentAns = 0L
        var ans = 0L
        var op: (Long, Int) -> Long = { _, _ -> error("Unknown operation") }
        for (i in ops.indices) {
            if (ops[i] != ' ' && ops[i] != '.') { // If your IDE removes spaces at the end of the line use dots for padding
                op = when (ops[i]) {
                    '+' -> { a, b -> a + b }
                    '*' -> { a, b -> a * b }
                    else -> {
                        error("Unknown operation: ${ops[i]}")
                    }
                }
                currentAns = when (ops[i]) {
                    '+' -> 0
                    '*' -> 1
                    else -> {
                        println(ops[i])
                        error("Unknown operation: ${ops[i]}")
                    }
                }
            }
            val verticalNumber = numbers.indices.map { numbers[it][i] }.filter { it != ' ' }.fold(0) { acc, c ->
                acc * 10 + c.digitToInt()
            }
            if (verticalNumber != 0) {
                currentAns = op(currentAns, verticalNumber)
            } else {
                ans += currentAns
            }
        }
        return ans + currentAns
    }
}