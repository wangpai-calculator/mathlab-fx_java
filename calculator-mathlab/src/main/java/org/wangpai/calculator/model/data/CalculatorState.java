package org.wangpai.calculator.model.data;

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
