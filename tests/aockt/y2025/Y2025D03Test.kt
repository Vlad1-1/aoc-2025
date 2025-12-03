package aockt.y2025

import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec
import io.kotest.matchers.shouldBe
import jdk.internal.vm.vector.VectorSupport.test

@AdventDay(2025, 3)
class Y2025D03Test : AdventSpec<Y2025D03>({
    partOne {
        """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
        """.trimIndent() shouldOutput 357
    }

    partTwo {
        """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
        """.trimIndent() shouldOutput 3121910778619
    }
})
