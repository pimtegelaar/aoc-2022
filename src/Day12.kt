import java.util.*

fun main() {
    data class Node(
        val x: Int,
        val y: Int,
        val value: String
    ) {
        var top: Node? = null
        var bottom: Node? = null
        var left: Node? = null
        var right: Node? = null
        val neighbors get() = mutableListOf(bottom, right, top, left).filterNotNull()

        var gScore: Int = 0
        var parent: Node? = null
        var visited = false
    }

    fun List<String>.parseNodes() = mapIndexed { y, line ->
        line.mapIndexed { x, c ->
            Node(x, y, c.toString())
        }.toTypedArray()
    }.toTypedArray()

    fun Array<Node>.safeGet(x: Int): Node? =
        if (x < 0 || x >= size) null else this[x]

    fun Array<Array<Node>>.safeGet(x: Int, y: Int): Node? =
        if (y < 0 || y >= size) null else this[y].safeGet(x)

    fun Array<Array<Node>>.assignNeighbors() =
        flatMap { row ->
            row.map { node ->
                node.apply {
                    left = row.safeGet(x - 1)
                    right = row.safeGet(x + 1)
                    top = safeGet(x, y - 1)
                    bottom = safeGet(x, y + 1)
                }
            }
        }

    fun bestScore(start: Node, nodes: List<Node>): Int {
        val openSet = PriorityQueue<Node>(Comparator.comparing { it.gScore })
        openSet.add(start)
        start.visited = true
        while (openSet.isNotEmpty()) {
            val node = openSet.poll()
            if (node.value == "E") {
                break
            }
            node.neighbors.forEach { neighbor ->
                val neighborCode = if (neighbor.value == "E") 'z'.code else neighbor.value.first().code
                val nodeCode = if (node.value == "S") 'a'.code else node.value.first().code
                if (!neighbor.visited && (neighborCode - nodeCode < 2)) {
                    neighbor.gScore = node.gScore + 1
                    neighbor.parent = node
                    neighbor.visited = true
                    openSet.add(neighbor)
                }
            }
        }
        return  nodes.first { node -> node.value == "E" }.gScore
    }

    fun part1(input: List<String>): Int {
        val nodes = input.parseNodes().assignNeighbors()
        val startNode = nodes.first { node -> node.value == "S" }
        return bestScore(startNode, nodes)
    }

    fun part2(input: List<String>): Int {
        val nodes = input.parseNodes().assignNeighbors()
        return nodes.filter { node -> node.value == "a" }
            .map { start ->
                bestScore(start, nodes.map { node ->
                    node.gScore = 0
                    node.visited = false
                    node.parent = null
                    node
                })
            }
            .filter { it > 0 }
            .min()
    }

    val testInput = readInput("Day12_test")
    val input = readInput("Day12")

    val part1 = part1(testInput)
    check(part1 == 31) { part1 }
    println(part1(input))

    val part2 = part2(testInput)
    check(part2 == 29) { part2 }
    println(part2(input))
}