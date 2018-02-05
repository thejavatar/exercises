package com.thejavatar

import spock.lang.Specification

/**
 * Created by theJavatar.com on 05/02/2018.
 */
class RemoveDups extends Specification {

    def "should remove duplicates from list (hashmap)"() {
        given:
            Node list = initialList
        when:
            removeDuplicates(list)
        then:
            list == expectedList
        where:
            initialList                       || expectedList
            buildList(1, 2, 3, 4, 5, 1, 6, 2) || buildList(1, 2, 3, 4, 5, 6)
            buildList(1, 1, 1)                || buildList(1)
    }

    def "should remove duplicates from list (no hashmap)"() {
        given:
            Node list = initialList
        when:
            removeDuplicatesNoHashMap(list)
        then:
            list == expectedList
        where:
            initialList                       || expectedList
            buildList(1, 2, 3, 4, 5, 1, 6, 2) || buildList(1, 2, 3, 4, 5, 6)
            buildList(1, 1, 1)                || buildList(1)
    }

    def removeDuplicates(Node node) {
        HashSet<Integer> counter = new HashSet<>()
        Node previousNode
        while (node != null) {
            if (counter.contains(node.value)) {
                previousNode.next = node.next
            } else {
                counter.add(node.value)
                previousNode = node
            }
            node = node.next
        }
    }

    def removeDuplicatesNoHashMap(Node node) {
        Node currentNode = node
        while (currentNode != null) {
            Node possibleDuplicateNode = currentNode
            while(possibleDuplicateNode.next != null) {
                if(currentNode.value == possibleDuplicateNode.next.value) {
                    possibleDuplicateNode.next = possibleDuplicateNode.next.next
                } else {
                    possibleDuplicateNode = possibleDuplicateNode.next
                }
            }
            currentNode = currentNode.next
        }
    }

    Node buildList(int ... values) {
        Node rootNode
        Node previousNode
        for (int value : values) {
            if (previousNode == null) {
                previousNode = new Node(value: value)
                rootNode = previousNode
            } else {
                previousNode.next = new Node(value: value)
                previousNode = previousNode.next
            }
        }
        return rootNode
    }

    class Node {
        int value
        Node next

        @Override
        String toString() {
            List<Integer> values = []
            Node node = this
            while (node != null) {
                values.add(node.value)
                node = node.next
            }
            return values.join(",")
        }

        @Override
        boolean equals(Object otherNode) {
            if (otherNode == null) {
                return false
            }
            if (!(otherNode instanceof Node)) {
                return false
            }
            if (this.is(otherNode)) {
                return true
            }
            otherNode = (Node) otherNode
            Node currentNode = this
            while (currentNode != null && otherNode != null) {
                if (currentNode.value != otherNode.value) {
                    return false
                }
                currentNode = currentNode.next
                otherNode = otherNode.next
            }
            if (currentNode != null || otherNode != null) {
                return false
            }
            return true
        }
    }

}
