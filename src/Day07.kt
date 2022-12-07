fun main() {

    open class Node(val name: String, open val size: Int = 0)

    class Dir(name: String, val parent: Dir? = null, val nodes: MutableList<Node> = mutableListOf()) : Node(name, 0) {
        override val size get() = nodes.sumOf { it.size }

        val dirs get() = nodes.filterIsInstance<Dir>()

        val deepDirs get(): List<Dir> = dirs.flatMap { it.deepDirs } + this
    }

    fun traverse(input: List<String>): Dir {
        val root = Dir("/")
        var currentDir = root
        input.drop(1).forEach { line ->
            val split = line.split(" ")
            when (split[0]) {
                "$" -> if (split[1] == "cd") {
                    currentDir = if (split[2] == "..") {
                        currentDir.parent!!
                    } else {
                        currentDir.dirs.first { it.name == split[2] }
                    }
                }
                "dir" -> currentDir.nodes.add(Dir(name = split[1], parent = currentDir))
                else -> currentDir.nodes.add(Node(split[1], split[0].toInt()))
            }
        }
        return root
    }

    fun part1(input: List<String>) = traverse(input).deepDirs.map { it.size }.filter { it <= 100000 }.sum()

    fun part2(input: List<String>): Int {
        val root = traverse(input)
        val delta = 30000000 - (70000000 - root.size)
        return root.deepDirs.map { it.size }.filter { it >= delta }.min()
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    val part1 = part1(testInput)
    check(part1 == 95437) { part1 }
    println(part1(input))

    val part2 = part2(testInput)
    check(part2 == 24933642) { part2 }
    println(part2(input))
}
