fun main() {

    fun part1(input: List<String>): Int {
        var x = 1
        var result = 0
        var cycle = 1
        input.forEach { line ->
            if (cycle % 40 == 20) {
                result += cycle * x
            }
            if (line.startsWith("addx")) {
                cycle++
                if (cycle % 40 == 20) {
                    result += cycle * x
                }
                x += line.substring(5).toInt()
            }
            cycle++
        }
        return result
    }

    fun part2(input: List<String>): String {
        var x = 1
        val stack = ArrayDeque(elements = input)
        var current: Int? = null
        var processing = false
        val cl = mutableListOf<Char>()
        for (i in 0..250) {
            val sp = i % 40
            if (sp == 0) {
                cl.add('\n')
            }
            if (stack.isEmpty()) {
                break
            }
            if (processing) {
                processing = false
            } else {
                if (current != null) {
                    x += current
                }
                val line = stack.removeFirst()
                if (line.startsWith("addx")) {
                    current = line.substring(5).toInt()
                    processing = true
                } else {
                    current = null
                }
            }
            if (x in sp - 1..sp + 1) {
                cl.add('#')
            } else {
                cl.add('.')
            }
        }
        return cl.joinToString("")
    }

    val inputName = "Day10"
    val testInput = readInput(inputName + "_test")
    val input = readInput(inputName)

    val part1 = part1(testInput)
    check(part1 == 13140) { part1 }
    println(part1(input))

    val part2 = part2(testInput)
    println(part2)
    println(part2(input))
}
