package org.wangpai.mathlab.exp.exposed;

import org.wangpai.mathlab.exp.model.CalculationResultOutput;
import org.wangpai.mathlab.symbol.operand.Operand;

/**
 * @since 2021-8-1
 */
public class CalculatorApi {
    public static CalculationResultOutput calculateExpression(String expression) {
        CalculationResultOutput result = new CalculationResultOutput();
        CalculatorBackgroundFx calculator = new CalculatorBackgroundFx()
                .setAutoCalculateAction(data -> {
                    result.setPromptMsg((String) data);
                })
                .setErrorOccurredAction(data -> {
                    result.setStateMsg((String) data);
                    result.setPromptMsg((String) data);
                })
                .setDefaultPromptMsgAction(data -> {
                    result.setPromptMsg((String) data);
                })
                .setProcessUpdatedAction(data -> {
                    result.setCalculationProcess((String) data);
                })
                .setResultCameOutAction(data -> {
                    result.setResult((Operand) data);
                });
        calculator.readExpression(expression);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(calculateExpression("2334.623*6345-234/1234+234*(254-45.242)="));
    }
}
