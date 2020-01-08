package  ru.unn.agile.Matrix.viewmodel;

import java.util.List;

public interface ILogger {
    void logMessage(String s);

    List<String> getLog();
}
