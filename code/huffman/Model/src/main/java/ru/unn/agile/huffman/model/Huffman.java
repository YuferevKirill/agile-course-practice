package ru.unn.agile.huffman.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import ru.unn.agile.huffman.model.node.*;

public final class Huffman {
    private static String encodedString = "";
    private static String decodedString = "";

    public static final String CODE_0 = "0";
    public static final String CODE_1 = "1";

    private Huffman() { }

    private static Map<Character, Node> buildAlphabet() {
        Map<Character, Integer> numberOfAppearances = new HashMap<>();
        Map<Character, Node> alphabet = new HashMap<>();

        decodedString.chars().forEach(charElem -> {
            if (numberOfAppearances.containsKey((char) charElem)) {
                numberOfAppearances.put((char) charElem,
                        numberOfAppearances.get((char) charElem) + 1);
            } else {
                numberOfAppearances.put((char) charElem, 1);
            }
        });

        numberOfAppearances.entrySet().forEach(entry -> {
            alphabet.put(entry.getKey(), new Node(entry.getValue()));
        });

        return alphabet;
    }

    public static String encodeString(final String str) {
        decodedString = str;
        if ("".equals(decodedString)) {
            encodedString = "";
        } else {
            Map<Character, Node> alphabet = buildAlphabet();

            PriorityQueue<Node> sortedAlphabet = new PriorityQueue<>();

            sortedAlphabet.addAll(alphabet.values().stream().collect(Collectors.toList()));

            while (sortedAlphabet.size() > 1) {
                Node first = sortedAlphabet.poll();
                Node second = sortedAlphabet.poll();
                sortedAlphabet.add(new InternalNode(first, second));
            }

            if (alphabet.size() == 1) {
                sortedAlphabet.poll().buildCode(CODE_0);
            } else {
                sortedAlphabet.poll().buildCode("");
            }

            encodedString = decodedString
                    .chars()
                    .mapToObj(ch -> alphabet.get((char) ch).getCode())
                    .collect(Collectors.joining(""));
        }

        return encodedString;
    }
}
