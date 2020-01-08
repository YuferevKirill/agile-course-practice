package ru.unn.agile.bitarray.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BitArrayTest {

    @Test
    public void canInitBitArray() {
        // arrange
        BitArray btr = new BitArray(10);

        // act
        byte[] btrRawArray = btr.getRawArray();

        // assert
        assertEquals(btrRawArray.length, 2);
    }

    @Test(expected = NegativeArraySizeException.class)
    public void throwInitBitArrayWithNegative() {
        new BitArray(-10);
    }

    @Test
    public void isZeroAfterInitBitArray() {
        // arrange
        BitArray btr = new BitArray(101);

        // act
        byte[] btrRawArray = btr.getRawArray();

        // assert
        for (byte elem : btrRawArray) {
            assertEquals(elem, 0);
        }
    }

    @Test
    public void canUseGetCountBitFunc() {
        // arrange
        final int countElements = 279;
        BitArray btr = new BitArray(countElements);

        // act
        int countBit = btr.getCountBit();

        // assert
        assertEquals(countBit, countElements);
    }

    @Test
    public void canSetBitInBitArray() {
        // arrange
        final int countBits = 41;
        final int sizeByte = 8;
        BitArray btr = new BitArray(countBits);

        // act
        final int actualBit = 19;
        btr.setBit(actualBit);

        // assert
        final byte reference = 1 << (actualBit % sizeByte);
        byte actual = btr.getRawArray()[actualBit / sizeByte];
        assertEquals(reference, actual);
    }

    @Test
    public void canSetZeroBit() {
        // arrange
        BitArray btr = new BitArray(8);

        // act
        btr.setBit(0);

        // assert
        byte[] actual = btr.getRawArray();
        byte[] reference = {1};
        assertArrayEquals(reference, actual);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void canSetNegativeBitInBitArray() {
        // arrange
        BitArray btr = new BitArray(41);

        // act & assert
        btr.setBit(-31);
    }

    @Test
    public void canUnsetBitInBitArray() {
        // arrange
        BitArray btr = new BitArray(41);

        // act
        btr.setBit(25); btr.unsetBit(25);

        // assert
        for (byte elem : btr.getRawArray()) {
            assertEquals(elem, 0);
        }
    }

    @Test
    public void canUnsetZeroBit() {
        // arrange
        BitArray btr = new BitArray(8);

        // act
        btr.setBit(0);
        btr.unsetBit(0);

        // assert
        byte[] actual = btr.getRawArray();
        byte[] reference = {0};
        assertArrayEquals(reference, actual);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void canUnsetNegativeBitInBitArray() {
        // arrange
        BitArray btr = new BitArray(41);

        // act & assert
        btr.unsetBit(-31);
    }

    @Test
    public void isSetBitInBitArray() {
        // arrange
        BitArray btr = new BitArray(41);

        // act
        btr.setBit(29);

        // assert
        assertTrue(btr.isBit(29));
    }

    @Test
    public void isSetBitEqualZeroInBitArray() {
        // arrange
        BitArray btr = new BitArray(41);

        // act
        btr.setBit(0);

        // assert
        assertTrue(btr.isBit(0));
    }

    @Test
    public void isUnsetBitInBitArray() {
        // arrange
        BitArray btr = new BitArray(41);

        // act
        btr.setBit(29);

        // assert
        assertFalse(btr.isBit(28));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void isSetNegativeBitInBitArray() {
        // arrange
        BitArray btr = new BitArray(41);

        // act & assert
        btr.unsetBit(-31);
    }

    @Test
    public void isEqualBitArrays() {
        // arrange
        BitArray bitArray1 = new BitArray(41);
        BitArray bitArray2 = new BitArray(41);

        bitArray1.setBit(7);   bitArray2.setBit(7);
        bitArray1.setBit(11);  bitArray2.setBit(11);
        bitArray1.setBit(19);  bitArray2.setBit(19);
        bitArray1.setBit(26);  bitArray2.setBit(26);
        bitArray1.setBit(34);  bitArray2.setBit(34);
        bitArray1.setBit(40);  bitArray2.setBit(40);

        // act && assert
        assertEquals(bitArray1, bitArray2);
    }

    @Test
    public void isNotEqualBitArrays() {
        // arrange
        BitArray btr1 = new BitArray(41);
        BitArray btr2 = new BitArray(41);

        btr1.setBit(7);   btr2.setBit(7);
        btr1.setBit(11);  btr2.setBit(11);
        btr1.setBit(19);  btr2.setBit(19);
        btr1.setBit(26);  btr2.setBit(27);
        btr1.setBit(34);  btr2.setBit(34);
        btr1.setBit(40);  btr2.setBit(40);

        // act && assert
        assertNotEquals(btr1, btr2);
    }

    @Test
    public void canCopyBitArray() {
        // arrange
        BitArray btr = new BitArray(41);

        btr.setBit(4);
        btr.setBit(13);
        btr.setBit(19);
        btr.setBit(25);
        btr.setBit(34);
        btr.setBit(41);

        // act
        BitArray actualBtr = new BitArray(btr);

        // assert
        assertEquals(btr, actualBtr);
    }

    @Test
    public void canUseUnionBitArrays() {
        // arrange
        BitArray btr = new BitArray(8);
        BitArray invBtr = new BitArray(btr.getCountBit());
        btr.setBit(0); invBtr.setBit(1);
        btr.setBit(2); invBtr.setBit(3);
        btr.setBit(4); invBtr.setBit(5);
        btr.setBit(6); invBtr.setBit(7);
        btr.setBit(8);

        // act
        BitArray actualBtr = btr.union(invBtr);

        // assert
        BitArray ref = new BitArray(btr.getCountBit());
        for (int i = 0; i < ref.getCountBit(); i++) {
            ref.setBit(i);
        }
        assertEquals(ref, actualBtr);
    }
}
