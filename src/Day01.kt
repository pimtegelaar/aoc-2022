fun main() {

    fun caloriesPerElf(input: List<String>) = mutableListOf<MutableList<Int>>(mutableListOf()).apply {
        input.forEach {
            if (it.isBlank()) {
                add(mutableListOf())
            } else {
                last().add(it.toInt())
            }
        }
    }

    fun part1(input: List<String>) = caloriesPerElf(input).maxOf { it.sum() }

    fun part2(input: List<String>) = caloriesPerElf(input).map { it.sum() }.sorted().takeLast(3).sum()

    val testInput = readInput("Day01_test")
    val input = readInput("Day01")

    check(part1(testInput) == 24000)
    println(part1(input))

    check(part2(testInput) == 45000)
    println(part2(input))
}
