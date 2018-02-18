package com.thejavatar

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by theJavatar.com on 18/02/2018.
 */
class SortedMerge extends Specification {

    @Unroll
    def "should merge sorted arrays"() {
        expect:
            Arrays.equals(merge(a, b), expectedResult)
        where:
            a                 | b                    || expectedResult
            [1, 2, 3]         | [4, 5, 6]            || [1, 2, 3, 4, 5, 6].toArray(new int[6])
            [4, 5, 6]         | [1, 2, 3]            || [1, 2, 3, 4, 5, 6].toArray(new int[6])
            [1, 4, 7, 12, 20] | [2, 3, 5, 6, 10, 31] || [1, 2, 3, 4, 5, 6, 7, 10, 12, 20, 31].toArray(new int[11])
    }

    private int[] merge(def a, def b) {
        int resultCounter = a.size() + b.size() - 1
        int aCounter = a.size() - 1
        int bCounter = b.size - 1
        a = a.toArray(new int[a.size() + b.size()])
        b = b.toArray()
        while (resultCounter >= 0) {
            if (bCounter < 0 || (aCounter >= 0 && a[aCounter] > b[bCounter])) {
                a[resultCounter] = a[aCounter]
                aCounter--
            } else {
                a[resultCounter] = b[bCounter]
                bCounter--
            }
            resultCounter--
        }
        return a
    }
}
