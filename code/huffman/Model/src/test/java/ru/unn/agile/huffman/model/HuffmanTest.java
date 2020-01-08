package ru.unn.agile.huffman.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class HuffmanTest {
    @Test
    public void encodedStringIsEmptyIfHuffmanCreatedWithEmptyString() {
        assertTrue(Huffman.encodeString("").equals(""));
    }

    @Test
    public void encodedStringIsCorrectWith1AlphabeticSymbolInput() {
        assertEquals(Huffman.encodeString("a"), "0");
    }

    @Test
    public void encodedStringIsCorrectWith2AlphabeticSymbolsInput() {
        assertEquals(Huffman.encodeString("ab"), "01");
    }

    @Test
    public void encodedStringIsCorrectWithRepeatedAlphabeticSymbolInput() {
        assertEquals(Huffman.encodeString("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"),
        "000000000000000000000000000000");
    }

    @Test
    public void encodedStringIsCorrectWith10DifferentAlphabeticSymbolsInput() {
        assertEquals(Huffman.encodeString("abhfklopmn"), "0010111011110010100110111001111000");
    }

    @Test
    public void encodedStringIsCorrectWith10CoupleSymbolsInput() {
        assertEquals(Huffman.encodeString("ababababab"), "0101010101");
    }

    @Test
    public void encodedStringIsCorrectWith10TripletSymbolsInput() {
        assertEquals(Huffman.encodeString("abcabcabcabcabc"), "1001110011100111001110011");
    }

    @Test
    public void encodedStringIsCorrectWithAlphabeticSymbolsAndWhitespaceInput() {
        assertEquals(Huffman.encodeString("hello world"), "01011101010110000111111000110011");
    }

    @Test
    public void encodedStringIsCorrectWithTheSameUpperAndLowerSymbolsInput() {
        assertEquals(Huffman.encodeString("Ww"), "01");
    }

    @Test
    public void encodedStringIsCorrectWithNumericSymbolsInput() {
        assertEquals(Huffman.encodeString("1029384756"), "0011100011110111100001011111010100");
    }

    @Test
    public void encodedStringIsCorrectWithNumericAndAlphabeticSymbolsInput() {
        assertEquals(Huffman.encodeString("111aaa 9e9e9dm  ke3mm 2333 004040404"),
            "111011101110000000000101110011111100111111001101"
            + "00011011011101111111011001001101110110011011011101100100010100010100010100010");
    }

    @Test
    public void encodedStringIsCorrectWithNumericAndAlphabeticAndSpecialSymbolsInput() {
        assertEquals(Huffman.encodeString("!!! dddd ?? dd ? !! ddd333 99999UUUUUcwqdfjdkpw"),
        "0100100101101010101011001110111110101011001111100"
        + "100101101010100110011001101100010010010010010000000000000001"
        + "1110011101111001101111011111101011111111100011101");
    }

    @Test
    public void canCompareTwoTheSameStringsWithTheSameAlphabets() {
        String str1 = Huffman.encodeString("170995o0unn");
        String str2 = Huffman.encodeString("170995o0unn");
        assertTrue(str1.equals(str2));
    }

    @Test
    public void decodedStringsAreDifferentForTwoDifferentStringsWithDifferentAlphabets() {
        String str1 = Huffman.encodeString("abbb");
        String str2 = Huffman.encodeString("cabbb");
        assertFalse(str1.equals(str2));
    }
}
