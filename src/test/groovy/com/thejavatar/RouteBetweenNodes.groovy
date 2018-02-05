package com.thejavatar

import spock.lang.Specification

/**
 * Created by theJavatar.com on 05/02/2018.
 */
class RouteBetweenNodes extends  Specification {

    def "should identify that there is a route between two linked nodes"() {
        given: "two nodes"
            Node node1 = new Node()
            Node node2 = new Node()
        and: "links between nodes"
            node1.addLinkTo(node2)
        expect:
            isThereRouteBetween(node1, node2) == true
            isThereRouteBetweenBidirectionalSearch(node1, node2) == true
    }

    def "should identify that there is a route between two nodes separated by one node"() {
        given: "two nodes"
            Node node1 = new Node()
            Node node2 = new Node()
            Node node3 = new Node()
        and: "links between nodes"
            node1.addLinkTo(node2)
            node2.addLinkTo(node3)
        expect:
            isThereRouteBetween(node1, node3) == true
            isThereRouteBetweenBidirectionalSearch(node1, node3) == true
    }

    def "should identify that there is a route between two nodes"() {
        given: "two nodes"
            Node node1 = new Node()
            Node node2 = new Node()
            Node node3 = new Node()
            Node node4 = new Node()
            Node node5 = new Node()
            Node node6 = new Node()
            Node node7 = new Node()
        and: "links between nodes"
            node1.addLinkTo(node2)
            node2.addLinkTo(node3)
            node2.addLinkTo(node4)
            node4.addLinkTo(node3)
            node4.addLinkTo(node6)
            node5.addLinkTo(node7)
            node5.addLinkTo(node2)
            node6.addLinkTo(node5)
            node7.addLinkTo(node1)
            node7.addLinkTo(node5)
        expect:
            isThereRouteBetween(node1, node7) == true
            isThereRouteBetweenBidirectionalSearch(node1, node7) == true
    }

    def "should identify that there is a no route between two nodes"() {
        given: "two nodes"
            Node node1 = new Node()
            Node node2 = new Node()
            Node node3 = new Node()
            Node node4 = new Node()
            Node node5 = new Node()
            Node node6 = new Node()
            Node node7 = new Node()
        and: "links between nodes"
            node1.addLinkTo(node2)
            node2.addLinkTo(node3)
            node2.addLinkTo(node4)
            node4.addLinkTo(node3)
            node4.addLinkTo(node5)
            node6.addLinkTo(node5)
            node7.addLinkTo(node5)
        expect:
            isThereRouteBetween(node1, node7) == false
            isThereRouteBetweenBidirectionalSearch(node1, node7) == false
    }

    boolean isThereRouteBetween(Node node1, Node node2) {
        int counter = 0
        Set<Node> visited = new HashSet<>()
        Queue<Node> toBeVisited = new LinkedList<>()
        toBeVisited.add(node1)

        while(!toBeVisited.isEmpty()) {
            counter++
            Node currentNode = toBeVisited.poll()
            visited.add(currentNode)

            if(currentNode == node2) {
                println "U " + counter
                return true
            }

            for(Node neighbour : currentNode.getNeighbours()) {
                if(!visited.contains(neighbour)) {
                    toBeVisited.add(neighbour)
                }
            }
        }
        println "U " + counter
        return false
    }

    boolean isThereRouteBetweenBidirectionalSearch(Node startingNode, Node endNode) {
        int counter = 0
        Set<Node> visitedFromStartingNode = new HashSet<>()
        Queue<Node> toBeVisitedFromStartingNode = new LinkedList<>()
        Set<Node> visitedFromEndNode = new HashSet<>()
        Queue<Node> toBeVisitedFromEndNode = new LinkedList<>()
        toBeVisitedFromStartingNode.add(startingNode)
        toBeVisitedFromEndNode.add(endNode)

        while(!toBeVisitedFromStartingNode.isEmpty() || !toBeVisitedFromEndNode.isEmpty()) {
            counter++
            boolean success
            success = visitNode(toBeVisitedFromStartingNode, visitedFromStartingNode, endNode)
            success = success || visitNode(toBeVisitedFromEndNode, visitedFromEndNode, startingNode)
            if(success) {
                println "B " + counter
                return true
            }
        }
        println "B " + counter
        return false
    }

    def visitNode(LinkedList<Node> toBeVisited, HashSet<Node> visitedFromThisSide, Node searchNode) {
        if(!toBeVisited.isEmpty()) {
            Node currentNode = toBeVisited.poll()
            if(currentNode == searchNode) {
                return true
            } else {
                visitedFromThisSide.add(currentNode)
                for(Node neighbour : currentNode.getNeighbours()) {
                    if(!visitedFromThisSide.contains(neighbour)) {
                        toBeVisited.add(neighbour)
                    }
                }
            }
        }
        return false
    }

    class Node {
        List<Node> neighbours = []

        void addLinkTo(Node node) {
            neighbours.add(node)
        }

    }

}
