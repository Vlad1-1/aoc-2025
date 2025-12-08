package aockt.y2025

import io.github.jadarma.aockt.core.Solution

object Y2025D08 : Solution {
    private data class Vector3D(val x: Long, val y: Long, val z: Long) {
        fun distance(other: Vector3D) = (x - other.x) * (x - other.x) +
                (y - other.y) * (y - other.y) +
                (z - other.z) * (z - other.z)
    }

    private data class Edge(val nodes: Pair<Int, Int>, val distance: Long)

    private fun parseInput(input: String) =
        input.lines().map {
            val (x, y, z) = it.split(',').map(String::toLong)
            Vector3D(x, y, z)
        }

    private object DSU {
        lateinit var dsu: Array<Int>
        lateinit var dsuSize: Array<Int>

        fun initialize(n: Int) {
            dsu = Array(n) { it }
            dsuSize = Array(n) { 1 }
        }
        fun findRoot(x: Int): Int {
            if (x != dsu[x])
                dsu[x] = findRoot(dsu[x])
            return dsu[x]
        }

        fun unite(nodes: Pair<Int, Int>): Boolean {
            var (a, b) = nodes.toList().map(DSU::findRoot)
            if (a == b) return false
            if (dsuSize[a] < dsuSize[b]) {
                a = a xor b
                b = a xor b
                a = a xor b
            }
            dsu[b] = a
            dsuSize[a] += dsuSize[b]
            return true
        }
    }

    override fun partOne(input: String): Any {
        val boxes = parseInput(input)
        DSU.initialize(boxes.size)
        boxes.withIndex()
            .flatMap { a -> boxes.withIndex().map { b -> a to b } }
            .filter { (a, b) -> a.index < b.index }
            .map { (a, b) -> Edge(a.index to b.index, a.value.distance(b.value)) }
            .sortedBy(Edge::distance)
            .take(1000)
            .forEach { DSU.unite(it.nodes) }

        return boxes.indices.filter { DSU.dsu[it] == it }.map(DSU.dsuSize::get).sortedDescending().take(3).reduce(Int::times)
    }

    override fun partTwo(input: String): Any {
        val boxes = parseInput(input)
        DSU.initialize(boxes.size)
        var components = boxes.size
        return boxes.withIndex()
            .flatMap { a -> boxes.withIndex().map { b -> a to b } }
            .filter { (a, b) -> a.index < b.index }
            .map { (a, b) -> Edge(a.index to b.index, a.value.distance(b.value)) }
            .sortedBy(Edge::distance)
            .takeWhile { (if (DSU.unite(it.nodes)) components-- else components) > 1 }
            .last().let { boxes[it.nodes.first].x * boxes[it.nodes.second].x }
    }
}