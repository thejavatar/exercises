package com.thejavatar

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by theJavatar.com on 15/02/2018.
 */
class FindLowestPrice extends Specification {

    @Unroll
    def "should find lowest prices"() {
        when:
            List<Interval> minPriceIntervals = calculateMinPrice(inputIntervals)
        then:
            Arrays.equals(minPriceIntervals.toArray(), expectedIntervals.toArray())
        where:
            inputIntervals << [
                    [
                            new Interval(min: 9, max: 10, price: 5),
                            new Interval(min: 1, max: 2, price: 1),
                            new Interval(min: 5, max: 6, price: 3),
                            new Interval(min: 7, max: 8, price: 4),
                            new Interval(min: 3, max: 4, price: 2)
                    ],
                    [
                            new Interval(min: 1, max: 10, price: 9),
                            new Interval(min: 3, max: 5, price: 3),
                            new Interval(min: 5, max: 5, price: 1)
                    ],
                    [
                            new Interval(min: 1, max: 10, price: 1),
                            new Interval(min: 3, max: 5, price: 3),
                            new Interval(min: 5, max: 5, price: 1),
                    ],
                    [
                            new Interval(min: 1, max: 5, price: 4),
                            new Interval(min: 3, max: 5, price: 3),
                            new Interval(min: 10, max: 20, price: 10),
                            new Interval(min: 24, max: 30, price: 5)
                    ],

            ]
            expectedIntervals << [
                    [
                            new Interval(min: 1, max: 2, price: 1),
                            new Interval(min: 3, max: 4, price: 2),
                            new Interval(min: 5, max: 6, price: 3),
                            new Interval(min: 7, max: 8, price: 4),
                            new Interval(min: 9, max: 10, price: 5)
                    ],
                    [
                            new Interval(min: 1, max: 2, price: 9),
                            new Interval(min: 3, max: 4, price: 3),
                            new Interval(min: 5, max: 5, price: 1),
                            new Interval(min: 6, max: 10, price: 9)
                    ],
                    [
                            new Interval(min: 1, max: 10, price: 1)
                    ],
                    [
                            new Interval(min: 1, max: 2, price: 4),
                            new Interval(min: 3, max: 5, price: 3),
                            new Interval(min: 10, max: 20, price: 10),
                            new Interval(min: 24, max: 30, price: 5)
                    ],
            ]
    }

    private List<Interval> calculateMinPrice(List<Interval> intervals) {
        Map<Integer, Integer> lowestPriceForHour = new TreeMap<>()
        for (Interval interval : intervals) {
            for (int hour = interval.min; hour <= interval.max; hour++) {
                if (!lowestPriceForHour.containsKey(hour)) {
                    lowestPriceForHour.put(hour, interval.price)
                } else {
                    Integer currentPrice = lowestPriceForHour.get(hour)
                    if (currentPrice > interval.price) {
                        lowestPriceForHour.put(hour, interval.price)
                    }
                }
            }
        }
        List<Interval> result = []
        Integer currentMin
        Integer currentPrice
        Integer previousHour
        for (Integer hour : lowestPriceForHour.keySet()) {
            if (currentMin == null) {
                currentMin = hour
                currentPrice = lowestPriceForHour.get(hour)
                previousHour = hour
            } else if (hour > previousHour + 1 || currentPrice != lowestPriceForHour.get(hour)) {
                result.add(new Interval(min: currentMin, max: previousHour, price: currentPrice))
                currentMin = hour
                currentPrice = lowestPriceForHour.get(hour)
                previousHour = hour
            } else {
                previousHour = hour
            }
        }
        if (currentMin != null) {
            result.add(new Interval(min: currentMin, max: previousHour, price: currentPrice))
        }
        return result
    }

    class Interval {
        Integer min
        Integer max
        Integer price

        @Override
        boolean equals(Object obj) {
            if (obj == null) {
                return false
            }
            if (!(obj instanceof Interval)) {
                return false
            }
            if (this.is(obj)) {
                return true
            }
            Interval otherObject = (Interval) obj
            return otherObject.max == this.max && otherObject.min == this.min && otherObject.price == this.price
        }

        @Override
        String toString() {
            return "min: " + this.min + " max: " + this.max + " price: " + this.price + ""
        }

    }

}
