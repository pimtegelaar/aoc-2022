fun main() {

    data class Item(var fear: Long)

    class Monkey(
        val items: MutableList<Item>,
        val operation: (Long) -> Long,
        val test: (Long) -> Boolean,
        val divisor: Long,
        val positive: Int,
        val negative: Int,
        var inspections: Long = 0
    )

    fun List<String>.toMonkey(): Monkey {
        val (operator, rhs) = this[2].substring(23).split(" ")
        return Monkey(
            items = this[1].substring(18)
                .split(", ")
                .map { Item(it.toLong()) }
                .toMutableList(),
            operation = if (operator == "+") { old ->
                old + if (rhs == "old") old else rhs.toLong()
            } else { old ->
                old * if (rhs == "old") old else rhs.toLong()
            },
            divisor = this[3].substring(21).toLong(),
            test = { item -> item % this[3].substring(21).toLong() == 0L },
            positive = this[4].substring(29).toInt(),
            negative = this[5].substring(30).toInt()
        )
    }

    fun shenanigans(input: List<String>, rounds: Int, relief: Boolean): Long {
        val monkeys = input.chunked(7).map { it.toMonkey() }
        val lcm = monkeys.map { it.divisor }.reduce { a, b -> a * b }
        for (i in 1..rounds) {
            monkeys.forEach { monkey ->
                while (monkey.items.isNotEmpty()) {
                    monkey.inspections++
                    val item = monkey.items.removeFirst()
                    item.fear = monkey.operation.invoke(item.fear)
                    if (relief) {
                        item.fear = item.fear / 3
                    }
                    item.fear = item.fear % lcm
                    if (monkey.test.invoke(item.fear)) {
                        monkeys[monkey.positive].items.add(item)
                    } else {
                        monkeys[monkey.negative].items.add(item)
                    }
                }
            }
        }
        val (a, b) = monkeys.map { it.inspections }.sorted().takeLast(2)
        return a * b
    }

    fun part1(input: List<String>) = shenanigans(input, 20, true)

    fun part2(input: List<String>) = shenanigans(input, 10000, false)

    val testInput = readInput("Day11_test")
    val input = readInput("Day11")

    val part1 = part1(testInput)
    check(part1 == 10605L) { part1 }
    println(part1(input))

    val part2 = part2(testInput)
    check(part2 == 2713310158L) { part2 }
    println(part2(input))
}
