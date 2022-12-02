const val WIN = 6
const val LOSE = 0
const val DRAW = 3
const val ROCK = 1
const val PAPER = 2
const val SCISSORS = 3

fun main() {

    fun part1(input: List<String>) = input.sumOf {
        when (it) {
            "A X" -> DRAW + ROCK
            "A Y" -> WIN + PAPER
            "A Z" -> LOSE + SCISSORS
            "B X" -> LOSE + ROCK
            "B Y" -> DRAW + PAPER
            "B Z" -> WIN + SCISSORS
            "C X" -> WIN + ROCK
            "C Y" -> LOSE + PAPER
            "C Z" -> DRAW + SCISSORS
            else -> throw UnsupportedOperationException("Unknown case $it")
        }
    }

    fun part2(input: List<String>) = input.sumOf {
        when (it) {
            "A X" -> LOSE + SCISSORS
            "A Y" -> DRAW + ROCK
            "A Z" -> WIN + PAPER
            "B X" -> LOSE + ROCK
            "B Y" -> DRAW + PAPER
            "B Z" -> WIN + SCISSORS
            "C X" -> LOSE + PAPER
            "C Y" -> DRAW + SCISSORS
            "C Z" -> WIN + ROCK
            else -> throw UnsupportedOperationException("Unknown case $it")
        }
    }

    val testInput = readInput("Day02_test")
    val input = readInput("Day02")

    val part1 = part1(testInput)
    check(part1 == 15) { part1 }
    println(part1(input))

    val part2 = part2(testInput)
    check(part2 == 12) { part2 }
    println(part2(input))
}
