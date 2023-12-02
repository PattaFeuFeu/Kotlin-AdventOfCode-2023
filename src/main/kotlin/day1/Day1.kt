package day1

import kotlin.time.measureTimedValue

/**
 * --- Day 1: Trebuchet?! ---
 *
 * Something is wrong with global snow production, and you've been selected to take a look. The Elves have even given you a map; on it, they've used stars to mark the top fifty locations that are likely to be having problems.
 *
 * You've been doing this long enough to know that to restore snow operations, you need to check all fifty stars by December 25th.
 *
 * Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
 *
 * You try to ask why they can't just use a weather machine ("not powerful enough") and where they're even sending you ("the sky") and why your map looks mostly blank ("you sure ask a lot of questions") and hang on did you just say the sky ("of course, where do you think snow comes from") when you realize that the Elves are already loading you into a trebuchet ("please hold still, we need to strap you in").
 */
object Day1 {

    private val fileContent: String = requireNotNull(javaClass.getResource("./day1_input.txt")?.readText()?.trim())

    @JvmStatic
    fun main(args: Array<String>) {
        val (valuePart1, durationPart1) = measureTimedValue { part1() }
        println("Solution part 1: $valuePart1 ($durationPart1)")

        val (valuePart2, durationPart2) = measureTimedValue { part2() }
        println("Solution part 2: $valuePart2 ($durationPart2)")
    }

    /**
     * As they're making the final adjustments, they discover that their calibration document (your puzzle input) has been amended by a very young Elf who was apparently just excited to show off her art skills. Consequently, the Elves are having trouble reading the values on the document.
     *
     * The newly-improved calibration document consists of lines of text; each line originally contained a specific calibration value that the Elves now need to recover. On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.
     *
     * For example:
     *
     * 1abc2
     * pqr3stu8vwx
     * a1b2c3d4e5f
     * treb7uchet
     *
     * In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.
     *
     * Consider your entire calibration document. What is the sum of all of the calibration values?
     */
    private fun part1(): Int {
        return fileContent
            .lineSequence()
            .map { line -> line.filter(Char::isDigit) }
            .sumOf { digits -> "${digits.first()}${digits.last()}".toInt() }
    }

    /**
     * --- Part Two ---
     *
     * Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".
     *
     * Equipped with this new information, you now need to find the real first and last digit on each line. For example:
     *
     * two1nine
     * eightwothree
     * abcone2threexyz
     * xtwone3four
     * 4nineeightseven2
     * zoneight234
     * 7pqrstsixteen
     *
     * In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76. Adding these together produces 281.
     *
     * What is the sum of all of the calibration values?
     */
    private fun part2(): Int {
        val namesToDigits = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )
        /*
        Some examples in the string contain "oneight" where potentially both "one" and "eight" could match.
        Depending on where they are in the string and if they could be the first or last "real" digits after conversion,
        we want to use either the "one" version or the "eight" version.
        That's why this is defined as a look-ahead to match _all_ versions, not just "one" OR "eight" like the regular
        (one|two|three|four|five|six|seven|eight|nine|[1-9]) would yield.
         */
        val nameOrDigitRegex = """(?=(one|two|three|four|five|six|seven|eight|nine|[1-9]))""".toRegex()
        return fileContent
            .lines()
            .map { line: String ->
                nameOrDigitRegex
                    .findAll(line)
                    .map { matchResult: MatchResult ->
                        val nameOrDigit: String = matchResult.groupValues[1]
                        namesToDigits.getOrDefault(key = nameOrDigit, defaultValue = nameOrDigit)
                    }
                    .joinToString("")
            }
            .sumOf { digits -> "${digits.first()}${digits.last()}".toInt() }
    }
}
