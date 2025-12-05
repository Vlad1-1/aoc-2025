package aockt.y2025

import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec

@AdventDay(2025, 5)
class Y2025D05Test : AdventSpec<Y2025D05>({
    partOne {
        """
            3-5
            10-14
            16-20
            12-18

            1
            5
            8
            11
            17
            32
        """.trimIndent() shouldOutput 3
    }

    partTwo()
})
