package ru.unn.agile.statisticscalculation.viewmodel;

import java.util.ArrayList;
import java.util.List;

class FakeLogger implements ILogger {
    private ArrayList<String> log = new ArrayList<String>();

    @Override
    public void addLog(final String mes) {
        log.add(mes);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
