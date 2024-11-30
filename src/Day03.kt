fun main() {
    readInput("Day03_test").run { println(part1() == 4361) }
    readInput("Day03_test").run { println(part2() == 467835) }
    readInput("Day03").run {
        part1().println()
        part2().println()
    }
}

private val numberRegex = Regex("\\d+")

private fun List<String>.part1() = mapIndexed { i, line ->
    numberRegex.findAll(line).map {
        val number = it.value.toInt()
        val indxs = it.range
        val lineIndexes = ((indxs.first - 1).coerceIn(indices)..(indxs.last + 1).coerceIn(indices))

        val relevantChars =
            this[(i - 1).coerceIn(indices)].substring(lineIndexes) + line.substring(lineIndexes) + this[(i + 1).coerceIn(
                indices
            )].substring(lineIndexes)
        val symbols = relevantChars.filterNot { char -> char.isDigit() || char == '.' }
        if (symbols.isEmpty()) 0 else number
    }.sum()
}.sum()

data class Position(val lineIndex: Int, val charIndex: Int)

data class GearNumber(val gearPosition: Position, val number: Int)

private fun List<String>.findGear(lineIndex: Int, charIndex: Int): Position? {
    val lineIndices = this[lineIndex].indices
    ((lineIndex - 1).coerceIn(indices)..(lineIndex + 1).coerceIn(indices)).forEach { lineDex ->
        ((charIndex - 1).coerceIn(lineIndices)..(charIndex + 1).coerceIn(lineIndices)).forEach { charDex ->
            val char = this[lineDex][charDex]
            if (char == '*') return Position(lineDex, charDex)
        }
    }
    return null
}

private fun List<String>.part2() = asSequence().mapIndexed { lineIndex, line ->
    numberRegex.findAll(line).map { match ->
        val number = match.value.toInt()
        val gearPosition = match.range.map { findGear(lineIndex, it) }.find { it != null }
        gearPosition?.let { GearNumber(gearPosition, number) }
    }.filterNotNull().toList()
}.flatten().groupBy { it.gearPosition }.map { (_, value) ->
    if (value.size >= 2) value.fold(1) { acc, gearNumber -> acc * gearNumber.number } else 0
}.sum()