package ru.unn.agile.huffman.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HuffmanViewModelTest {
    private HuffmanViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new HuffmanViewModel(new LoggerStub());
    }

    public void setViewModel(final HuffmanViewModel huffmanViewModel) {
        this.viewModel = huffmanViewModel;
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void checkRefLabelTextByDefaultText() {
        assertEquals("Reference string", viewModel.getInput().get());
    }

    @Test
    public void checkEncodeLabelTextByDefaultText() {
        assertEquals("Encode", viewModel.getOutputEncode().get());
    }

    @Test
    public void checkDecodeLabelTextByDefaultText() {
        assertEquals("Decode", viewModel.getOutputDecode().get());
    }

    @Test
    public void checkEncodeLabelAfterPushStartWithZeroString() {
        viewModel.setInput("");
        viewModel.startEncodeAndDecode();
        assertEquals("", viewModel.getOutputEncode().get());
    }

    @Test
    public void checkEncodeLabelAfterPushStartWithString() {
        viewModel.setInput("ab");
        viewModel.startEncodeAndDecode();
        assertEquals("01", viewModel.getOutputEncode().get());
    }

    @Test
    public void checkDecodeLabelAfterPushStartWithZeroString() {
        viewModel.setInput("");
        viewModel.startEncodeAndDecode();
        assertEquals("", viewModel.getOutputDecode().get());
    }
    @Test
    public void logIsEmptyAtTheBeginningOfWork() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void canSetLoggerInHuffmanViewModel() {
        viewModel.setLogger(new LoggerStub());
        assertNotNull(viewModel.getLogger());
    }

    @Test (expected = IllegalArgumentException.class)
    public void canNotCreateHuffmanViewModelWithNullableLogger() {
        new HuffmanViewModel(null);
    }

    @Test
    public void canLogInputData() {
        String s = "Not empty string";
        viewModel.setInput(s);
        viewModel.getInput();
        var logMessages = viewModel.getLog();
        var lastLogMessage = logMessages.get(logMessages.size() - 1);
        assertTrue(lastLogMessage.contains(HuffmanViewModel.LogMessages.USER_INPUT_DATA + s));
    }

    @Test
    public void canLogStartOfDecodingData() {
        String s = "example string";
        viewModel.setInput(s);
        viewModel.startEncodeAndDecode();
        var logMessages = viewModel.getLog();
        var lastLogMessages = logMessages.get(logMessages.size() - 1);
        assertTrue(lastLogMessages.contains(HuffmanViewModel.LogMessages.DECODE_DATA));
    }

    @Test
    public void canLogStartOfEncoding() {
        String testString = "test string";
        viewModel.setInput(testString);
        viewModel.startEncodeAndDecode();
        var logMessages = viewModel.getLog();
        var encodedMessage = logMessages.get(logMessages.size() - 3);
        assertTrue(encodedMessage.contains(HuffmanViewModel.LogMessages.START_ENCODE));
    }

    @Test
    public void canLogResultOfEncoding() {
        String toEncode = "stringToEncode";
        viewModel.setInput(toEncode);
        viewModel.startEncodeAndDecode();
        var logMessages = viewModel.getLog();
        var encodedMessage = logMessages.get(logMessages.size() - 2);
        assertTrue(encodedMessage.contains(HuffmanViewModel.LogMessages.ENCODE_DATA));
    }
}
