fun main() {

    fun Char.priority() = if (isLowerCase()) code - 96 else code - 38

    fun part1(input: List<String>) = input.sumOf { rucksack ->
        val (first, second) = rucksack.chunked(rucksack.length / 2)
        second.first { first.contains(it) }.priority()
    }

    fun part2(input: List<String>) = input.chunked(3).sumOf { group ->
        group[0].first { group[1].contains(it) && group[2].contains(it) }.priority()
    }

    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    val part1 = part1(testInput)
    check(part1 == 157) { part1 }
    println(part1(input))

    val part2 = part2(testInput)
    check(part2 == 70) { part2 }
    println(part2(input))
}
