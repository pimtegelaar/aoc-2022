fun main() {

    class Command(
        val amount: Int,
        val from: Int,
        val to: Int
    )

    class Puzzle(
        val commands: List<Command>,
        val stacks: Array<ArrayDeque<Char>>
    )

    fun parseCommands(input: List<String>) = input.map {
        val split = it.split(" ")
        Command(split[1].toInt(), split[3].toInt(), split[5].toInt())
    }

    fun parseStacks(input: List<String>, separatorIndex: Int): Array<ArrayDeque<Char>> {
        val size = input[separatorIndex - 1].takeLast(3).replace(" ", "").toInt()
        val stacks = Array<ArrayDeque<Char>>(size) { ArrayDeque() }
        input.take(separatorIndex - 1).forEach {
            for (i in 0 until size) {
                val crate = it[(i + 1) * 4 - 3]
                if (crate.isLetter()) {
                    stacks[i].add(crate)
                }
            }
        }
        return stacks
    }

    fun parse(input: List<String>): Puzzle {
        val separatorIndex = input.indexOfFirst { it.isBlank() }
        return Puzzle(parseCommands(input.drop(separatorIndex + 1)), parseStacks(input, separatorIndex))
    }

    fun part1(input: List<String>): String {
        val puzzle = parse(input)
        val stacks = puzzle.stacks
        puzzle.commands.forEach { command ->
            repeat(command.amount) {
                val crate = stacks[command.from - 1].removeFirst()
                stacks[command.to - 1].addFirst(crate)
            }
        }
        return stacks.map { it.first() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val puzzle = parse(input)
        val stacks = puzzle.stacks
        puzzle.commands.forEach { command ->
            val from = stacks[command.from - 1]
            val toAdd = from.take(command.amount).toMutableList()
            repeat(command.amount) {
                stacks[command.from - 1].removeFirst()
                stacks[command.to - 1].addFirst(toAdd.removeLast())
            }
        }
        return stacks.map { it.first() }.joinToString("")
    }

    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    val part1 = part1(testInput)
    check(part1 == "CMZ") { part1 }
    println(part1(input))

    val part2 = part2(testInput)
    check(part2 == "MCD") { part2 }
    println(part2(input))
}
