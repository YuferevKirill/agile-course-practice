package ru.unn.agile.huffman.model.node;

import ru.unn.agile.huffman.model.Huffman;

public class InternalNode extends Node {
    private final Node left;
    private final Node right;

    public InternalNode(final Node left, final Node right) {
        super(left.getSum() + right.getSum());
        this.left = left;
        this.right = right;
    }

    @Override
    public void buildCode(final String code) {
        super.buildCode(code);
        left.buildCode(code + Huffman.CODE_0);
        right.buildCode(code + Huffman.CODE_1);
    }
}
