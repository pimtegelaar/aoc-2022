fun main() {

    fun String.range(): IntRange {
        val (start, end) = split("-")
        return IntRange(start.toInt(), end.toInt())
    }

    fun String.rangePair(): Pair<IntRange, IntRange> {
        val (first, second) = split(",")
        return Pair(first.range(), second.range())
    }

    fun IntRange.envelops(other: IntRange) = first <= other.first && last >= other.last

    fun part1(input: List<String>) = input.count {
        val (first, second) = it.rangePair()
        first.envelops(second) || second.envelops(first)
    }


    fun part2(input: List<String>) = input.count {
        val (first, second) = it.rangePair()
        first.intersect(second).isNotEmpty()
    }


    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    val part1 = part1(testInput)
    check(part1 == 2) { part1 }
    println(part1(input))

    val part2 = part2(testInput)
    check(part2 == 4) { part2 }
    println(part2(input))
}
