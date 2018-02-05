package com.thejavatar

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by theJavatar.com on 04/02/2018.
 *
 * a, b are integers and a, b > 0
 *
 */
class AddWithoutPlus extends Specification {

    @Unroll
    def "#a + #b = #sum"() {
        expect:
            addNumbers(a, b) == sum
        where:
            a                 | b || sum
            1                 | 2 || 3
            1                 | 1 || 2
            1                 | 3 || 4
            3                 | 3 || 6
            -2                | 3 || 1
            Integer.MAX_VALUE | 1 || Integer.MAX_VALUE + 1
    }

    def addNumbers(int a, int b) {
        if (b == 0) return a
        int sum = a ^ b
        int carry = (a & b) << 1
        return addNumbers(sum, carry)
    }

}
