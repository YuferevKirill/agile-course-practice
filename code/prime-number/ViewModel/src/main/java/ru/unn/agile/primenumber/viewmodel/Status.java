package ru.unn.agile.primenumber.viewmodel;

enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Find' or Enter"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success");

    private final String name;

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
