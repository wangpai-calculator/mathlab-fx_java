package org.wangpai.mathlab.exp.model;

public enum CalculatorState {
    INIT("init"),
    END("end"),
    NORMAL("normal"),
    ERROR("error");

    private final String state;

    CalculatorState(String state) {
        this.state = state;
    }
}
