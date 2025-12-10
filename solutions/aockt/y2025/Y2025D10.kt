package aockt.y2025

import io.github.jadarma.aockt.core.Solution

object Y2025D10 : Solution {
    private fun parseInput(input: String) = input.lines().map { lines ->
        val parts = lines.split(' ')
        var lightsRequired = 0
        val buttons = mutableListOf<Int>()
        parts[0].trim('[',']').reversed().forEach {
            lightsRequired = lightsRequired shl 1
            if (it == '#') lightsRequired = lightsRequired or 1
        }
        for (button in parts.drop(1).dropLast(1)) {
            var lights = 0
            button.trim('(',')').split(',').map(String::toInt).forEach { lights = lights or (1 shl it) }
            buttons += lights
        }
        val joltages = parts.last().trim('{', '}').split(',').map(String::toInt)
        Machine(lightsRequired, buttons, joltages)
    }

    override fun partOne(input: String) =
        parseInput(input).map { (lights, buttons) ->
            val visited = BooleanArray(1 shl 11) { false }
            val queue = ArrayDeque<Pair<Int, Int>>()
            queue.addLast(0 to 0)
            visited[0] = true
            while (queue.isNotEmpty()) {
                val (currentLights, presses) = queue.removeFirst()
                if (currentLights == lights) {
                    return@map presses
                }
                for (button in buttons) {
                    val newLights = currentLights xor button
                    if (!visited[newLights]) {
                        visited[newLights] = true
                        queue.addLast(newLights to presses + 1)
                    }
                }
            }
            error("No solution found")
        }.sum()

    private data class Machine(
        val lightsRequired: Int,
        val buttons: List<Int>,
        val joltages: List<Int>
    )
}

