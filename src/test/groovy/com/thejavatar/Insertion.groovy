package com.thejavatar

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by theJavatar.com on 05/02/2018.
 */
class Insertion extends Specification {

    @Unroll
    def "should insert bits from #m to #n starting from position #i through #j"() {
        expect:
            insert(n, m, i, j) == result
        where:
            n   | m | i | j || result
            8   | 3 | 0 | 1 || 11
            9   | 2 | 0 | 1 || 10
            11  | 2 | 1 | 2 || 13
            499 | 9 | 3 | 6 || 459
    }

    int insert(int n, int m, int i, int j) {
        int left = ~0 << j + 1
        int right = (1 << i) - 1
        int mask = left | right
        n = n & mask
        m = m << i
        return n | m
    }
}
