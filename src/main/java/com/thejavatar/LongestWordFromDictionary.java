package com.thejavatar;

import java.util.*;

/**
 * Created by theJavatar.com on 03/02/2018.
 */
public class LongestWordFromDictionary {

    /*
     * Use dictionary.printNodesPotential() to nodes's potential.
     * Use dictionary.printWords() to print dictionary.
     * Use dictionary.printTreeWords() to print dictionary as tree.
     */
    public static void main(String[] args) {
        Dictionary dictionary = buildDictionary("like", "most", "spring", "getting", "started", "guides", "you",
                "can", "start", "from", "scratch", "and", "complete", "each", "step", "or", "bypass", "basic", "setup",
                "steps", "that", "are", "already", "familiar", "to", "either", "way", "end", "up", "with", "working",
                "code", "provides", "a", "comprehensive", "programming", "and", "configuration", "model", "for",
                "modern", "Java", "based", "enterprise", "applications");
        List<String> longestWords = dictionary.findLongestWordsThatCanBeBuiltFrom("grodsfdagweesddmvypoldthni");
        System.out.println("Longest words: " + Arrays.toString(longestWords.toArray()));
    }

    public static Dictionary buildDictionary(String ... words) {
        Dictionary dictionary = new Dictionary();
        for (String word : words) {
            dictionary.add(word);
        }
        return dictionary;
    }

    public static class Dictionary {

        private final Node root = new Node();
        private final Boolean caseSensitive;

        public Dictionary(Boolean caseSensitive) {
            this.caseSensitive = caseSensitive;
        }

        public Dictionary() {
            this(false);
        }

        public void add(String word) {
            root.decomposeWord(applyCaseSensitive(word));
        }

        public List<String> findLongestWordsThatCanBeBuiltFrom(String availableLetters) {
            FindLongestWordTreeTraverser traverser =
                    new FindLongestWordTreeTraverser(applyCaseSensitive(availableLetters));
            traverser.traverse(root);
            return traverser.getLongestWords();
        }

        public void printNodesPotential() {
            new PrintNodePotentialTreeTraverser().traverse(root);
        }

        public void printWords() {
            new PrintNodesTreeTraverser().traverse(root);
        }

        public void printTreeWords() {
            new PrintNodesAsTreeTreeTraverser().traverse(root);
        }

        private String applyCaseSensitive(String letters) {
            return caseSensitive ? letters : letters.toLowerCase();
        }
    }

    private static class Node {

        private static final Character WORD_START = '~';
        private static final Character WORD_TERMINATION = '*';
        private final Character letter;
        private final Map<Character, Node> children;
        /**
         * If this node is a leaf this is the max length of a word that can be build from root of the tree up to this
         * leaf.
         * <p>
         * If this node is a not a leaf this is the max length of a word that can be build from root of the tree up to
         * the deepest children of this node.
         * <p>
         * Long story short this is a potential of the node to produce an n length word. If maxLength < n we know for
         * sure that there is no point of going further down this path if we want to produce an n length word.
         */
        private Integer maxLength;

        public Node(Character letter, Integer maxLength) {
            this.letter = letter;
            this.children = new HashMap<>();
            this.maxLength = maxLength;
        }

        public Node() {
            this(WORD_START, 0);
        }

        public Character getLetter() {
            return letter;
        }

        public Map<Character, Node> getChildren() {
            return children;
        }

        public void decomposeWord(String word) {
            this.decomposeWord(word, 1);
        }

        /**
         * We decompose words letter by letter so that each letter X has its own node and the parent of this node is a
         * node that stores the letter preceding the letter X. So for instance for words: dog, dogs, doe we would build
         * following tree:
         * <p>
         * <pre>
         * ~
         *  - D
         *      - O
         *          - G
         *              - *
         *              - S
         *                  - *
         *          - E
         *              - *
         * </pre>
         * Please notice that the letter G has two children: a word termination (*) and as well link to  letter S. On
         * the way down When building the tree we track the height of nodes. Thanks to that when we reach the word
         * termination (*) we know the length of the word that could be build when using this particular path of the
         * tree. Since we use recursion, on the way up we update parent nodes so that now they store that value as well.
         * We call this a potential of the node to produce an n length word {@link #maxLength}.
         */
        public Integer decomposeWord(String word, Integer length) {
            if (!children.containsKey(word.charAt(0))) {
                children.put(word.charAt(0), new Node(word.charAt(0), length));
            }
            if (word.length() > 1) {
                Integer possibleMaxHeight = children.get(word.charAt(0)).decomposeWord(word.substring(1), ++length);
                this.maxLength = Math.max(possibleMaxHeight, this.maxLength);
            } else {
                children.get(word.charAt(0)).endWord(length);
                this.maxLength = length;
            }
            return this.maxLength;
        }

        private void endWord(Integer length) {
            children.put(WORD_TERMINATION, new Node(WORD_TERMINATION, length));
        }

        public Integer getMaxLength() {
            return maxLength;
        }

        public boolean isFollowedByWordTermination() {
            return children.containsKey(WORD_TERMINATION);
        }

        public boolean isWordStart() {
            return letter.equals(WORD_START);
        }
    }

    private interface TreeTraverser {
        void traverse(Node root);
    }

    private static class FindLongestWordTreeTraverser implements TreeTraverser {

        private final List<String> potentialLongestWords = new ArrayList<>();
        private final String availableLetters;
        private Integer longestWordThreshold = 0;

        private FindLongestWordTreeTraverser(String availableLetters) {
            this.availableLetters = availableLetters;
        }

        @Override
        public void traverse(Node root) {
            processNode(root, availableLetters, "");
        }

        private void processNode(final Node node, final String availableLetters, final String accumulatedWord) {
            if (node.getMaxLength() >= longestWordThreshold) {
                if (node.isFollowedByWordTermination()) {
                    final String potentialLongestWord = accumulatedWord + node.getLetter();
                    if (potentialLongestWord.length() > longestWordThreshold) {
                        longestWordThreshold = potentialLongestWord.length();
                        potentialLongestWords.clear();
                    }
                    potentialLongestWords.add(potentialLongestWord);
                }
                for (Node children : node.getChildren().values()) {
                    if (availableLetters.indexOf(children.getLetter()) != -1) {
                        final String remainingLetters = availableLetters.replaceFirst(String.valueOf(children.getLetter()), "");
                        processNode(children, remainingLetters, accumulatedWord + (node.isWordStart() ? "" : node.getLetter()));
                    }
                }
            }
        }

        public List<String> getLongestWords() {
            return potentialLongestWords;
        }
    }

    private abstract static class AccumulatorTreeTraverser<T> implements TreeTraverser {

        public void traverse(Node root) {
            traverse(root, createAccumulator());
        }

        private void traverse(Node node, T accumulator) {
            accumulator = processNode(node, accumulator);
            if (node.getChildren().size() == 0) {
                onLeaf(node, accumulator);
            } else {
                for (Node children : node.getChildren().values()) {
                    traverse(children, accumulator);
                }
            }
        }

        abstract T processNode(Node node, T accumulator);

        abstract void onLeaf(Node node, T accumulator);

        abstract T createAccumulator();
    }

    private static class PrintNodePotentialTreeTraverser extends AccumulatorTreeTraverser<String> {

        private static final String SEPARATOR = "-";

        @Override
        public String processNode(Node node, String accumulator) {
            accumulator += SEPARATOR + node.getMaxLength();
            return accumulator;
        }

        @Override
        public void onLeaf(Node node, String accumulator) {
            System.out.println(accumulator);
        }

        @Override
        String createAccumulator() {
            return "";
        }

    }

    private static class PrintNodesTreeTraverser extends AccumulatorTreeTraverser<String> {

        @Override
        String processNode(Node node, String accumulator) {
            accumulator += node.getLetter();
            return accumulator;
        }

        @Override
        void onLeaf(Node node, String accumulator) {
            System.out.println(accumulator);
        }

        @Override
        String createAccumulator() {
            return "";
        }

    }

    private static class PrintNodesAsTreeTreeTraverser extends AccumulatorTreeTraverser<String> {

        private static final String SEPARATOR = "-";

        @Override
        String processNode(Node node, String accumulator) {
            System.out.println(accumulator + node.getLetter());
            accumulator += SEPARATOR;
            return accumulator;
        }

        @Override
        void onLeaf(Node node, String accumulator) {
        }

        @Override
        String createAccumulator() {
            return "";
        }

    }

}
