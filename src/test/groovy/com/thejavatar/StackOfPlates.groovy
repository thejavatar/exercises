package com.thejavatar

import spock.lang.Specification

/**
 * Created by theJavatar.com on 05/02/2018.
 */
class StackOfPlates extends Specification {

    def "should behave like stack for push and pop"() {
        given:
            UberStack<Integer> stack = new UberStack<>(3)
        when:
            stack.push(1)
            stack.push(2)
            stack.push(3)
            stack.push(4)
            stack.push(5)
        then:
            stack.pop() == 5
            stack.pop() == 4
            stack.pop() == 3
            stack.pop() == 2
            stack.pop() == 1
    }

    def "should overflow from one to stack to another on popAt"() {
        given:
            UberStack<Integer> stack = new UberStack<>(3)
        when:
            stack.push(1)
            stack.push(2)
            stack.push(3)
            stack.push(4)
            stack.push(5)
        then:
            stack.popAt(0) == 3
            stack.popAt(0) == 4
            stack.pop() == 5
            stack.pop() == 2
            stack.pop() == 1
    }

    def "should overflow from one to stack to another on popAt 2"() {
        given:
            UberStack<Integer> stack = new UberStack<>(3)
        when:
            stack.push(1)
            stack.push(2)
            stack.push(3)
            stack.push(4)
            stack.push(5)
            stack.push(6)
            stack.push(7)
            stack.push(8)
            stack.push(9)
            stack.push(10)
            stack.push(11)
        then:
            stack.popAt(1) == 6
            stack.popAt(1) == 7
            stack.pop() == 11
            stack.popAt(0) == 3
            stack.popAt(0) == 4
            stack.pop() == 10
            stack.popAt(1) == 9
    }

    class UberStack<T> {

        final int threshold
        final List<Stack> stacks = new LinkedList<>()
        int stackCounter = 0

        UberStack(int threshold) {
            this.threshold = threshold
            this.stacks.add(new Stack(threshold))
        }

        void push(T element) {
            if (getCurrentStack().hasSpace()) {
                getCurrentStack().push(element)
            } else {
                stacks.add(new Stack(threshold))
                stackCounter++
                getCurrentStack().push(element)
            }
        }

        T pop() {
            if (!getCurrentStack().isEmpty()) {
                return getCurrentStack().pop()
            } else {
                stacks.remove(stackCounter)
                stackCounter--
                return getCurrentStack().pop()
            }
        }

        T popAt(int stackNumber) {
            if (stacks.size() > stackNumber) {
                T result = stacks.get(stackNumber).pop()
                int moveBetweenStacksCounter = stackNumber
                int maxStackIndex = stacks.size() - stackNumber - 1
                while (moveBetweenStacksCounter < maxStackIndex) {
                    stacks.get(moveBetweenStacksCounter).push(stacks.get(moveBetweenStacksCounter + 1).popFirstElement())
                    if(stacks.get(moveBetweenStacksCounter + 1).isEmpty()) {
                        stacks.remove(moveBetweenStacksCounter + 1)
                        stackCounter--
                        maxStackIndex--
                    }
                    moveBetweenStacksCounter++
                }
                return result
            } else {
                return null
            }
        }

        private Stack getCurrentStack() {
            return this.stacks.get(stackCounter)
        }

        private class Stack<T> {

            final int threshold
            final List<T> space
            int counter = 0

            Stack(int threshold) {
                this.threshold = threshold
                this.space = new LinkedList<>()
            }

            void push(T element) {
                this.space.push(element)
                counter++
            }

            boolean hasSpace() {
                return counter < threshold
            }

            boolean isEmpty() {
                return counter == 0
            }

            T pop() {
                counter--
                this.space.pop()
            }

            T popFirstElement() {
                counter--
                T result = this.space.get(0)
                this.space.removeAt(0)
                return result
            }
        }
    }

}
