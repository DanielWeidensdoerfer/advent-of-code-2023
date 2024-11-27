fun main() {

    readInput("Day01_test_1").run { println(part1() == 142) }
    readInput("Day01_test_2").run { println(part2() == 281) }
    readInput("Day01").run {
        part1().println()
        part2().println()
    }
}

private fun List<String>.part1() = sumOf {
    val first = it.find { char -> char.isDigit() }
    val last = it.findLast { char -> char.isDigit() }
    "$first$last".toInt()
}

private val numbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "1", "2", "3", "4", "5", "6", "7", "8", "9")

private fun String.textToInt() = when (this) {
    "one" -> 1
    "two" -> 2
    "three" -> 3
    "four" -> 4
    "five" -> 5
    "six" -> 6
    "seven" -> 7
    "eight" -> 8
    "nine" -> 9
    else -> toInt()
}

private fun List<String>.part2() = sumOf {
    val first = it.findAnyOf(numbers)?.second?.textToInt()
    val last = it.findLastAnyOf(numbers)?.second?.textToInt()
    "$first$last".toInt()
}