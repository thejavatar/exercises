package com.thejavatar

import spock.lang.Specification

/**
 * Created by theJavatar.com on 18/02/2018.
 */
class WordFrequencies extends Specification {

    def "should count word frequencies"() {
        given:
            String book = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"
            FrequencyCounter frequencyCounter = new FrequencyCounter(book)
        when:
            frequencyCounter.countFrequencies()
        then:
            frequencyCounter.getFrequency(word) == expectedFrequency
        where:
            word      || expectedFrequency
            "dolorem" || 2
            "vel"     || 2
            "enim"    || 3
            "in"      || 4
            "lucas"   || 0

    }

    private class FrequencyCounter {

        String book
        Map<String, Integer> frequencies = [:]

        FrequencyCounter(String book) {
            this.book = book
        }

        void countFrequencies() {
            for (String word : book.split(" ")) {
                word = word.toLowerCase()
                if (!frequencies.containsKey(word)) {
                    frequencies.put(word, 1)
                } else {
                    frequencies.put(word, frequencies.get(word) + 1)
                }
            }
        }

        int getFrequency(String word) {
            word = word.toLowerCase()
            if (frequencies.containsKey(word)) {
                return frequencies.get(word)
            } else {
                return 0
            }
        }
    }
}
