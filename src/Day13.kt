import Packet.PacketInteger
import Packet.PacketList

sealed class Packet : Comparable<Packet> {
    data class PacketList(val value: List<Packet>) : Packet() {
        override fun compareTo(other: Packet): Int = when (other) {
            is PacketInteger -> compareTo(PacketList(listOf(other)))
            is PacketList -> {
                value.zip(other.value) { left, right -> left compareTo right }.forEach { if (it != 0) return it }
                value.size - other.value.size
            }
        }
    }

    data class PacketInteger(val value: Int) : Packet() {
        override fun compareTo(other: Packet) = when (other) {
            is PacketInteger -> value.compareTo(other.value)
            is PacketList -> PacketList(listOf(this)).compareTo(other)
        }
    }
}

fun main() {

    fun String.toPacket(): Packet {
        if (isEmpty()) {
            return PacketList(emptyList())
        }
        if (length == 1) {
            return PacketInteger(toInt())
        }
        var braceCount = 0
        val packets = mutableListOf<Packet>()
        var subPacketStart = 0
        for (i in 0 until length) {
            val current = this[i]
            if (current == ',') {
                continue
            }
            if (current == '[') {
                braceCount++
                if (subPacketStart == 0) {
                    subPacketStart = i + 1
                }
                continue
            }
            if (braceCount == 0) {
                packets.add(PacketInteger(current.toString().toInt()))
                continue
            }
            if (current == ']') {
                braceCount--
                if (braceCount == 0) {
                    packets.add(substring(subPacketStart..i - 1).toPacket())
                    subPacketStart = 0
                }
            }
        }
        return PacketList(packets)
    }

    fun String.dropOuter() = drop(1).dropLast(1)

    fun part1(input: List<String>): Int {
        val pairs = input.chunked(3).map { Pair(it[0].dropOuter().toPacket(), it[1].dropOuter().toPacket()) }
        return pairs.map { (left, right) -> left <= right }.withIndex().filter { it.value }.sumOf { it.index + 1 }
    }

    fun part2(input: List<String>): Int {
        val divider2 = "[[2]]".toPacket()
        val divider6 = "[[6]]".toPacket()
        val sorted = input.asSequence()
            .filter { it.isNotBlank() }
            .map { it.toPacket() }
            .plus(divider2).plus(divider6)
            .sorted()
            .toList()
        return (sorted.indexOf(divider2) + 1) * (sorted.indexOf(divider6) + 1)
    }

    val testInput = readInput("Day13_test")
    val input = readInput("Day13")

    val part1 = part1(testInput)
    check(part1 == 13) { part1 }
    println(part1(input))

    val part2 = part2(testInput)
    check(part2 == 140) { part2 }
    println(part2(input))
}
