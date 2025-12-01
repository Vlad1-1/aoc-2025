package aockt.y2025

import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec

@AdventDay(2025, 1)
class Y2025D01Test : AdventSpec<Y2025D01>({
    partOne {
        "L55\n" +
                "R5" shouldOutput 1
        """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
        """.trimIndent() shouldOutput 3
    }

    partTwo {
        """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
        """.trimIndent() shouldOutput 6
    }
})
