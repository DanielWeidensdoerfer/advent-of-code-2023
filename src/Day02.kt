fun main() {

    readInput("Day02_test").run { println(part1() == 8) }
    readInput("Day02_test").run { println(part2() == 2286) }
    readInput("Day02").run {
        part1().println()
        part2().println()
    }
}

private val numberColorRegex = Regex("\\d+ (green|red|blue)")

fun CubeCount.isInConstraint() = when (cubeColor) {
    CubeColor.Red -> count <= 12
    CubeColor.Green -> count <= 13
    CubeColor.Blue -> count <= 14
}

enum class CubeColor(val value: String) {
    Red("red"), Green("green"), Blue("blue");

    companion object {
        fun resolve(text: String) = entries.find { it.value == text } ?: error("That color doesnt exist: $text")
    }
}

fun String.toCubeCount(): CubeCount {
    val color = CubeColor.resolve(substringAfter(" "))
    val count = substringBefore(" ").toInt()
    return CubeCount(color, count)
}

data class CubeCount(val cubeColor: CubeColor, val count: Int)

private fun List<String>.part1() = mapIndexed { index, line ->
    val possible = numberColorRegex.findAll(line).any { it.value.toCubeCount().isInConstraint().not() }.not()
    if (possible) index + 1 else 0
}.sum()

private fun List<String>.part2() = sumOf { line ->
    val groups = numberColorRegex.findAll(line).map { it.value.toCubeCount() }.groupBy { it.cubeColor }
    val minRed = groups[CubeColor.Red]?.maxOf { it.count } ?: 0
    val minBlue = groups[CubeColor.Blue]?.maxOf { it.count } ?: 0
    val minGreen = groups[CubeColor.Green]?.maxOf { it.count } ?: 0
    minRed * minBlue * minGreen
}