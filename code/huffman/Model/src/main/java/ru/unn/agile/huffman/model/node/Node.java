package ru.unn.agile.huffman.model.node;

public class Node implements Comparable<Node> {
    private final int sum;
    private String code;

    public Node(final int sum) {
        this.sum = sum;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public int compareTo(final Node o) {
        return Integer.compare(this.sum, o.sum);
    }

    public void buildCode(final String code) {
        this.code = code;
    }

    public int getSum() {
        return sum;
    }
}
