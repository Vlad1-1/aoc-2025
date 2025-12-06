package aockt.y2025

import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec

@AdventDay(2025, 6)
class Y2025D06Test : AdventSpec<Y2025D06>({
    partTwo {
        """
            123 328  51 64 
             45 64  387 23 
              6 98  215 314
            *   +   *   +  
        """.trimIndent() shouldOutput 3263827
    }
})
