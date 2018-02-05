package com.thejavatar

import spock.lang.Specification
import spock.lang.Unroll

import static LongestWordFromDictionary.buildDictionary

/**
 * Created by theJavatar.com on 05/02/2018.
 */
class LongestWordFromDictionaryTest extends Specification {

    @Unroll
    def "should find #expectLongestWords from dictionary using letters: '#availableLetters'"() {
        when:
            def foundLongestWords = dictionary.findLongestWordsThatCanBeBuiltFrom(availableLetters)
        then:
            println "Longest words: " + foundLongestWords
            expectLongestWords.size() == foundLongestWords.size()
            expectLongestWords.intersect(foundLongestWords).size() == expectLongestWords.size()
        where:
            dictionary                                     | availableLetters  || expectLongestWords
            buildDictionary("cat", "act", "bad")           | "act"             || ["cat", "act"]
            buildDictionary("cat", "act", "cats")          | "acts"            || ["cats"]
            buildDictionary("boom", "at", "a")             | "abtom"           || ["at"]
            buildDictionary("boom", "at", "a")             | "aobtom"          || ["boom"]
            buildDictionary()                              | "aobtom"          || []
            buildDictionary("boom", "at", "a")             | ""                || []
            buildDictionary("boom", "at", "a")             | "zzz"             || []
            buildDictionary("boom", "booms", "at")         | "abt?om"          || ["boom"]
            buildDictionary("boom", "booms", "dictionary") | "???bo????oms???" || ["dictionary"]
    }

}
