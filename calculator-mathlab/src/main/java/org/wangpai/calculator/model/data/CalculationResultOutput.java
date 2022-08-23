package org.wangpai.calculator.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.calculator.model.symbol.operand.Operand;

import static org.wangpai.calculator.model.data.CalculatorState.INIT;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CalculationResultOutput {
    CalculatorState state = INIT;

    String stateMsg = "";

    String promptMsg = "";

    String calculationProcess = "";

    Operand result;
}
