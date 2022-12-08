import kotlin.math.max

fun main() {

    data class Tree(
        val size: Int,
        var visible: Boolean = false,
        var up: Int = 0,
        var down: Int = 0,
        var left: Int = 0,
        var right: Int = 0,
    ) {
        val scenicScore get() = up * down * left * right
    }

    fun part1(input: List<String>): Int {
        val forest = input.map { line -> line.map { Tree(it.digitToInt()) } }
        forest.forEach { line ->
            var highest = -1
            line.forEach { tree ->
                if (tree.size > highest)
                    tree.visible = true
                highest = max(tree.size, highest)
            }
            highest = -1
            line.reversed().forEach { tree ->
                if (tree.size > highest)
                    tree.visible = true
                highest = max(tree.size, highest)
            }
        }
        val length = input.first().length
        for (x in 0 until length) {
            var highest = -1
            forest.forEach { line ->
                val tree = line[x]
                if (tree.size > highest)
                    tree.visible = true
                highest = max(tree.size, highest)
            }
            highest = -1
            forest.reversed().forEach { line ->
                val tree = line[x]
                if (tree.size > highest)
                    tree.visible = true
                highest = max(tree.size, highest)
            }
        }
        return forest.sumOf { line -> line.count { it.visible } }
    }

    fun calculateScenicScore(x: Int, y: Int, tree: Tree, forest: List<List<Tree>>) {
        for (i in x - 1 downTo 0) {
            val other = forest[y][i]
            tree.left++
            if (tree.size <= other.size) {
                break
            }
        }
        val length = forest.first().size
        for (i in x + 1 until length) {
            val other = forest[y][i]
            tree.right++
            if (tree.size <= other.size) {
                break
            }
        }
        for (i in y - 1 downTo 0) {
            val other = forest[i][x]
            tree.up++
            if (tree.size <= other.size) {
                break
            }
        }
        for (i in y + 1 until forest.size) {
            val other = forest[i][x]
            tree.down++
            if (tree.size <= other.size) {
                break
            }
        }
    }

    fun part2(input: List<String>): Int {
        val forest = input.map { line -> line.map { Tree(it.digitToInt()) } }

        forest.forEachIndexed { y, line ->
            line.forEachIndexed { x, tree ->
                calculateScenicScore(x, y, tree, forest)
            }
        }
        return forest.maxOf { it.maxOf { tree -> tree.scenicScore } }
    }

    val testInput = readInput("Day08_test")
    val input = readInput("Day08")

    val part1 = part1(testInput)
    check(part1 == 21) { part1 }
    println(part1(input))

    val part2 = part2(testInput)
    check(part2 == 8) { part2 }
    println(part2(input))
}
