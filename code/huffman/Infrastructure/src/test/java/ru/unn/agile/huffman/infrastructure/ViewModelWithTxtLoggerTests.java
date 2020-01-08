package ru.unn.agile.huffman.infrastructure;

import ru.unn.agile.huffman.viewmodel.HuffmanViewModel;
import ru.unn.agile.huffman.viewmodel.HuffmanViewModelTest;

public class ViewModelWithTxtLoggerTests extends HuffmanViewModelTest {

    @Override
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("./ViewModelWithTxtLoggerTests-huffman-lab3.log");
        super.setViewModel(new HuffmanViewModel(realLogger));
    }
}
