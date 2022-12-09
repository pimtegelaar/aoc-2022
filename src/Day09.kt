fun main() {

    data class Knot(var x: Int, var y: Int, val follower: Knot? = null) {

        val tail: Knot = follower?.tail ?: this

        fun move(direction: String) {
            when (direction) {
                "R" -> x++
                "L" -> x--
                "U" -> y--
                "D" -> y++
            }
            follower?.follow(this)
        }

        fun followDiagonalY(other: Knot) {
            if (y - other.y > 0) {
                y--
            } else if (other.y - y > 0) {
                y++
            }
        }

        fun followDiagonalX(other: Knot) {
            if (x - other.x > 0) {
                x--
            } else if (other.x - x > 0) {
                x++
            }
        }

        fun follow(other: Knot) {
            when {
                x - other.x > 1 -> {
                    x--
                    followDiagonalY(other)
                }
                other.x - x > 1 -> {
                    x++
                    followDiagonalY(other)
                }
                y - other.y > 1 -> {
                    y--
                    followDiagonalX(other)
                }
                other.y - y > 1 -> {
                    y++
                    followDiagonalX(other)
                }
            }
            follower?.follow(this)
        }
    }

    fun move(input: List<String>, head: Knot): Int {
        val tail = head.tail
        val visited = mutableSetOf(Pair(tail.x, tail.y))
        input.forEach {
            val (direction, steps) = it.split(" ")
            for (i in 1..steps.toInt()) {
                head.move(direction)
                visited.add(Pair(tail.x, tail.y))
            }
        }
        return visited.size
    }

    fun rope(length: Int, startX: Int, startY: Int): Knot {
        var current = Knot(startX, startY)
        repeat(length - 1) {
            current = Knot(startX, startY, follower = current)
        }
        return current
    }

    fun part1(input: List<String>) = move(input, rope(length = 2, startX = 30, startY = 30))

    fun part2(input: List<String>) = move(input, rope(length = 10, startX = 30, startY = 30))

    val testInput = readInput("Day09_test")
    val input = readInput("Day09")

    val part1 = part1(testInput)
    check(part1 == 13) { part1 }
    println(part1(input))

    val part2 = part2(testInput)
    check(part2 == 1) { part2 }
    println(part2(input))
}
