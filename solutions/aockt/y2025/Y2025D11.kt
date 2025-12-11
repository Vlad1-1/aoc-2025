package aockt.y2025

import io.github.jadarma.aockt.core.Solution

object Y2025D11 : Solution {
    private fun String.toNodeIndex() = this.fold(0) { acc, c -> acc * 26 + (c - 'a') }

    const val MAX_SIZE = 17576 // 26^3

    private fun dfs(
        node: Int,
        destination: Int,
        nodes: Array<List<Int>>,
        paths: IntArray = IntArray(MAX_SIZE) { -1 }
    ): Int {
        if (node == destination) return 1
        if (paths[node] != -1) return paths[node]
        paths[node] = nodes[node].sumOf { dfs(it, destination, nodes, paths) }
        return paths[node]
    }

    private fun parseInput(input: String): Array<List<Int>> {
        val nodes = Array(MAX_SIZE) { emptyList<Int>() }
        input.lines().forEach { line ->
            val parts = line.split(Regex(":? "))
            nodes[parts[0].toNodeIndex()] = parts.drop(1).map { it.toNodeIndex() }
        }
        return nodes
    }

    override fun partOne(input: String): Int {
        val nodes = parseInput(input)
        return dfs("you".toNodeIndex(), "out".toNodeIndex(), nodes)
    }

    override fun partTwo(input: String): Long {
        val nodes = parseInput(input)
        val svrToFft = dfs("svr".toNodeIndex(), "fft".toNodeIndex(), nodes)
        val fftToOut = dfs("fft".toNodeIndex(), "out".toNodeIndex(), nodes)
        val fftToDac = dfs("fft".toNodeIndex(), "dac".toNodeIndex(), nodes)
        val svrToDac = dfs("svr".toNodeIndex(), "dac".toNodeIndex(), nodes)
        val dacToOut = dfs("dac".toNodeIndex(), "out".toNodeIndex(), nodes)
        val dacToFft = dfs("dac".toNodeIndex(), "fft".toNodeIndex(), nodes)
        return (svrToFft.toLong() * fftToDac * dacToOut) +
               (svrToDac.toLong() * dacToFft * fftToOut)
    }
}