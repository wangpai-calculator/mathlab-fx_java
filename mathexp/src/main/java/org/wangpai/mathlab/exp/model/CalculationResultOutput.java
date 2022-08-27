package org.wangpai.mathlab.exp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.mathlab.symbol.operand.Operand;

import static org.wangpai.mathlab.exp.model.CalculatorState.INIT;

/**
 * @since 2021-8-1
 */
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
