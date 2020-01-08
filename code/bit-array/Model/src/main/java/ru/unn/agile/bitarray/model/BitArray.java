package ru.unn.agile.bitarray.model;


import java.util.Arrays;
import java.util.Objects;

public class BitArray {
    private byte[] rawArray;
    private final int sizeByte = 8;
    private int countBit;

    public BitArray(final int quantityOfBits) {
        countBit = quantityOfBits;
        int lengthRawArray = -1;
        if (quantityOfBits > 0) {
            lengthRawArray = getNumElem(quantityOfBits);
        }
        rawArray = new byte[lengthRawArray];

        Arrays.fill(rawArray, (byte) 0);
    }

    public BitArray(final BitArray rawArray) {
        this.rawArray = rawArray.getRawArray();
        this.countBit = rawArray.getCountBit();
    }

    public byte[] getRawArray() {
        return rawArray.clone();
    }

    public int getCountBit() {
        return countBit;
    }

    private int getNumElem(final int bit) {
        int numRawArray = bit / sizeByte;
        if (bit % sizeByte != 0) {
            numRawArray++;
        }
        return numRawArray;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BitArray)) {
            return false;
        }
        BitArray bitArray = (BitArray) obj;
        return Arrays.equals(getRawArray(), bitArray.getRawArray());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sizeByte);
        final int hashConst = 31;
        result = hashConst * result + Arrays.hashCode(getRawArray());
        return result;
    }

    public boolean isBit(final int bit) {
        if (bit == 0) {
            return (byte) (rawArray[0] & 1) > 0;
        }

        int numElem = -1;
        if (bit > 0) {
            numElem = getNumElem(bit);
        }

        byte mask = 0;
        mask |= 1 << (bit % sizeByte);
        byte answer = (byte) (rawArray[numElem - 1] & mask);
        return answer > 0;
    }

    public void setBit(final int bit) {
        if (bit != 0) {
            int numElem = getNumElem(bit);
            rawArray[numElem - 1] |= 1 << (bit % sizeByte);
        } else {
            rawArray[0] |= 1;
        }
    }

    public void unsetBit(final int bit) {
        if (bit != 0) {
            int numElem = getNumElem(bit);
            rawArray[numElem - 1] &= ~(1 << (bit % sizeByte));
        } else {
            rawArray[0] &= ~1;
        }
    }

    public BitArray union(final BitArray invBtr) {
        BitArray res = new BitArray(countBit);
        for (int i = 0; i < getNumElem(countBit); i++) {
            res.rawArray[i] = (byte) (rawArray[i] + invBtr.rawArray[i]);
        }
        return res;
    }
}
