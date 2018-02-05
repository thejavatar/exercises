package com.thejavatar

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by theJavatar.com on 04/02/2018.
 */
class NumberSwapper extends Specification {

    @Unroll
    def "should swap numbers (#initA and #initB) without tmp variable"() {
        given:
            int a = initA
            int b = initB

        when:
            a = a - b
            b = a + b
        and:
            a = b - a

        then:
            a == initB
            b == initA

        where:
            initA             | initB
            2                 | 1
            -2                | -10
            -10               | 2
            Integer.MAX_VALUE | 2
            Integer.MIN_VALUE | Integer.MAX_VALUE
            Integer.MIN_VALUE | Integer.MIN_VALUE
            Integer.MAX_VALUE | Integer.MAX_VALUE
    }

}
