package com.thejavatar

import spock.lang.Specification

/**
 * Created by theJavatar.com on 18/02/2018.
 */
class TripleStep extends Specification {

    def "number of ways to climb n steps staircase"() {
        expect:
            numberOfWays(steps) == expectedNumberOfWays
            numberOfWaysCache(steps) == expectedNumberOfWays
        where:
            steps || expectedNumberOfWays
            1     || 1
            2     || 2
            3     || 4
            4     || 7
            5     || 13
    }

    private def numberOfWays(steps) {
        if (steps == 0) {
            return 1
        }
        if (steps <= 0) {
            return 0
        }
        return numberOfWays(steps - 1) + numberOfWays(steps - 2) + numberOfWays(steps - 3)
    }

    private def numberOfWaysCache(steps) {
        int[] memo = new int[steps + 1]
        Arrays.fill(memo, -1)
        return numberOfWaysCache(steps, memo)
    }

    private def numberOfWaysCache(steps, int[] memo) {
        if (steps == 0) {
            return 1
        }
        if (steps <= 0) {
            return 0
        }
        if (memo[steps] > -1) {
            return memo[steps]
        }
        memo[steps] = numberOfWaysCache(steps - 1, memo) + numberOfWaysCache(steps - 2, memo) + numberOfWaysCache(steps - 3, memo)
        return memo[steps]
    }

}
