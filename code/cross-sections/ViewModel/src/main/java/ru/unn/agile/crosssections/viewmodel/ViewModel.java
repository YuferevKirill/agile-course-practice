package ru.unn.agile.crosssections.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.crosssections.model.CrossChecker;
import ru.unn.agile.crosssections.model.Dot;
import ru.unn.agile.crosssections.model.Section;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final StringProperty fstSectionStartX = new SimpleStringProperty();
    private final StringProperty fstSectionStartY = new SimpleStringProperty();
    private final StringProperty fstSectionFinishX = new SimpleStringProperty();
    private final StringProperty fstSectionFinishY = new SimpleStringProperty();
    private final StringProperty sndSectionStartX = new SimpleStringProperty();
    private final StringProperty sndSectionStartY = new SimpleStringProperty();
    private final StringProperty sndSectionFinishX = new SimpleStringProperty();
    private final StringProperty sndSectionFinishY = new SimpleStringProperty();
    private Logger logger;

    private final List<StringProperty> fields = new ArrayList<StringProperty>() {
        {
            add(fstSectionStartX);
            add(fstSectionStartY);
            add(fstSectionFinishX);
            add(fstSectionFinishY);
            add(sndSectionStartX);
            add(sndSectionStartY);
            add(sndSectionFinishX);
            add(sndSectionFinishY);
        }
    };

    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    private final BooleanProperty checkDisabled = new SimpleBooleanProperty();

    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    public ViewModel() {
        initialize();
    }

    public ViewModel(final Logger logger) {
        setLogger(logger);
        initialize();
    }

    public final void setLogger(final Logger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    private void initialize() {
        for (StringProperty field : fields) {
            field.set("");
        }
        result.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding couldCheck = new BooleanBinding() {
            {
                for (StringProperty field : fields) {
                    super.bind(field);
                }
            }

            @Override
            protected boolean computeValue() {
                return getNewStatus() == Status.READY;
            }
        };
        checkDisabled.bind(couldCheck.not());

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void check() {
        logger.log(LogMessages.CHECK_PRESSED);
        if (isCheckDisabled()) {
            return;
        }

        Dot a = new Dot(fstSectionStartX.get(), fstSectionStartY.get());
        Dot b = new Dot(fstSectionFinishX.get(), fstSectionFinishY.get());
        Dot c = new Dot(sndSectionStartX.get(), sndSectionStartY.get());
        Dot d = new Dot(sndSectionFinishX.get(), sndSectionFinishY.get());

        try {
            Section section1 = new Section(a, b);
            Section section2 = new Section(c, d);
            String newResult = CrossChecker.check(section1, section2) ? "Crossed" : "Don't Crossed";
            result.set(newResult);
            logger.log(LogMessages.RESULT_WAS_PRINTED + newResult);
            status.set(Status.SUCCESS.toString());
        } catch (Exception ex) {
            status.set(ex.getMessage());
        }
    }

    public BooleanProperty checkDisabledProperty() {
        return checkDisabled;
    }

    public final boolean isCheckDisabled() {
        return checkDisabled.get();
    }

    public StringProperty fstSectionStartXProperty() {
        return fstSectionStartX;
    }

    public StringProperty fstSectionStartYProperty() {
        return fstSectionStartY;
    }

    public StringProperty fstSectionFinishXProperty() {
        return fstSectionFinishX;
    }

    public StringProperty fstSectionFinishYProperty() {
        return fstSectionFinishY;
    }

    public StringProperty sndSectionStartXProperty() {
        return sndSectionStartX;
    }

    public StringProperty sndSectionStartYProperty() {
        return sndSectionStartY;
    }

    public StringProperty sndSectionFinishXProperty() {
        return sndSectionFinishX;
    }

    public StringProperty sndSectionFinishYProperty() {
        return sndSectionFinishY;
    }

    public StringProperty resultProperty() {
        return result;
    }

    public final String getResult() {
        return result.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getStatus() {
        return status.get();
    }

    private Status getNewStatus() {
        Status newStatus = Status.READY;
        for (StringProperty field : fields) {
            if (field.get().isEmpty()) {
                newStatus = Status.WAITING;
                break;
            }
        }
        for (StringProperty field : fields) {
            if (!isInputCorrect(field)) {
                newStatus = Status.BAD_FORMAT;
                break;
            }
        }
        return newStatus;
    }

    private boolean isInputCorrect(final StringProperty property) {
        String input = property.get();
        try {
            if (!property.get().isEmpty()) {
                Integer.parseInt(input);
            }
        } catch (NumberFormatException nfe) {
            logger.log(LogMessages.BAD_FORMAT + input);
            return false;
        }
        return true;
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            logger.log(LogMessages.NEW_INPUT + newValue);
            String newStatus = getNewStatus().toString();
            status.set(newStatus);
            logger.log(LogMessages.STATUS_WAS_CHANGED + newStatus);
        }
    }

    public List<String> getLog() {
        List<String> log = new ArrayList<>();
        try {
            log = logger.getLog();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return log;
    }

    static final class LogMessages {
        private LogMessages() {
        }

        static final String CHECK_PRESSED = "Check button pressed";
        static final String STATUS_WAS_CHANGED = "Status was changed to ";
        static final String BAD_FORMAT = "Incorrect input was entered: ";
        static final String NEW_INPUT = "New input was entered: ";
        static final String RESULT_WAS_PRINTED = "Result was printed: ";
    }
}


enum Status {
    WAITING("Please enter data"),
    READY("Press 'Check' or Enter"),
    BAD_FORMAT("Incorrect input"),
    SUCCESS("Success");

    private final String name;

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
