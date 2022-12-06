fun main() {

    fun String.isUnique() = length == toSet().size

    fun uniqueAfter(input: String, size: Int) = input.indexOf(input.windowed(size).first { it.isUnique() }) + size

    fun part1(input: String) = uniqueAfter(input, 4)

    fun part2(input: String) = uniqueAfter(input, 14)

    val testInput = readInputAsString("Day06_test")
    val input = readInputAsString("Day06")

    val part1 = part1(testInput)
    check(part1 == 7) { part1 }
    println(part1(input))

    val part2 = part2(testInput)
    check(part2 == 19) { part2 }
    println(part2(input))
}
