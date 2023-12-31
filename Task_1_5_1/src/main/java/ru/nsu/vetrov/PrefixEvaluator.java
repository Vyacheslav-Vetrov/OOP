package ru.nsu.vetrov;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class for parsing and evaluating expressions in prefix notation.
 */
class PrefixEvaluator {
    /**
     * Evaluates a given expression in prefix notation.
     *
     * @param expression The expression to evaluate.
     * @return The result of the evaluated expression.
     */
    public static double evaluate(String expression)
            throws UnsupportedCalculationException, DivisionByZeroException {
        Stack<Double> stack = new Stack<>();
        String[] tokens = expression.split(" ");
        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            try {
                double number = Double.parseDouble(token);
                stack.push(number);
            } catch (NumberFormatException e) {
                try {
                    Operation op = OperationFactory.getOperation(token);
                    ArrayList<Double> operands = new ArrayList<>();
                    while (!stack.isEmpty() && operands.size() < 2) {
                        operands.add(stack.pop());
                    }
                    double result = op.apply(operands.toArray(new Double[0]));
                    stack.push(result);
                } catch (UnsupportedCalculationException  ex) {
                    throw new UnsupportedCalculationException(token);
                } catch (DivisionByZeroException  ex) {
                    throw new DivisionByZeroException();
                }
            }
        }
        return stack.pop();
    }
}