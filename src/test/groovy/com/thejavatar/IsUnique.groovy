package com.thejavatar

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by theJavatar.com on 04/02/2018.
 */
class IsUnique extends Specification {

    @Unroll
    def "statement #word has all unique characters is #result"() {
        expect:
            hasUniqueCharactersHashSet(word) == result
            hasUniqueCharacters(word) == result
            hasUniqueCharactersBitManipulaion(word) == result
        where:
            word     || result
            "cat"    || true
            "unique" || false
            "sauce"  || true
            "Samson" || false
    }

    /**
     * O(N)
     */
    boolean hasUniqueCharactersBitManipulaion(String word) {
        int changeMe = 0
        for(Character letter : word.toLowerCase().toLowerCase()) {
            int mask = 1 << letter - 'a'.charAt(0)
            if((changeMe & mask) > 0) {
                return false
            } else {
                changeMe |= mask
            }
        }
        return true
    }

    /**
     * O(N)
     *
     * Details:
     * O(N * O(1)) assuming the amortized time cost of contains is O(1) since it's backed up by a hash table.
     */
    boolean hasUniqueCharactersHashSet(String word) {
        Set<Character> usedCharacters = new HashSet<>()
        for (Character character : word.toLowerCase().toCharArray()) {
            if (usedCharacters.contains(character)) {
                return false
            } else {
                usedCharacters.add(character)
            }
        }
        return true
    }

    /**
     * O(N*log(N)
     *
     * Details:
     * O(N*log(N) + N) - where N*log(N) is the merge cost).
     */
    boolean hasUniqueCharacters(String word) {
        char[] letters = mergeSort(word.toLowerCase().toCharArray(), 0, word.length() - 1)
        for (int i = 0; i < letters.length - 1; i++) {
            if (letters[i].equals(letters[i + 1])) {
                return false
            }
        }
        return true
    }

    char[] mergeSort(char[] array, int start, int end) {
        if (start == end) {
            return
        }

        int middle = (start + end) / 2
        mergeSort(array, start, middle)
        mergeSort(array, middle + 1, end)

        int leftIndex = start
        int rightIndex = middle + 1
        int index = start

        char[] helper = Arrays.copyOf(array, array.length)

        while (leftIndex <= middle && rightIndex <= end) {
            if (helper[leftIndex] < helper[rightIndex]) {
                array[index++] = helper[leftIndex++]
            } else {
                array[index++] = helper[rightIndex++]
            }
        }

        while (leftIndex <= middle) {
            array[index++] = helper[leftIndex++]
        }

        return array
    }

    /*char[] mergeSort(char[] array, int start, int end) {
        if (start == end) {
            return array[start]
        }

        int middle = (start + end) / 2
        char[] left = mergeSort(array, start, middle)
        char[] right = mergeSort(array, middle + 1, end)

        int leftIndex = 0
        int rightIndex = 0
        char[] result = new char[left.length + right.length]
        int index = 0

        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] < right[rightIndex]) {
                result[index++] = left[leftIndex++]
            } else {
                result[index++] = right[rightIndex++]
            }
        }

        while (leftIndex < left.length) {
            result[index++] = left[leftIndex++]
        }

        while (rightIndex < right.length) {
            result[index++] = right[rightIndex++]
        }

        return result
    }*/
}
