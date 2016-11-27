/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.tools;

import graphtest.exceptions.ParsingException;
import graphtest.exceptions.UnexpectedException;
import graphtest.parsed.ParsedToken;
import java.util.ArrayList;

/**
 *
 * @author A643012
 */
public class TokensTools {

    /**
     * Returns true if tokens are all already processed.
     *
     * @param parsedTokenList
     * @return
     */
    public static boolean areAllProcessed(ArrayList<ParsedToken> parsedTokenList) {
        return areAllProcessed(parsedTokenList, 0, parsedTokenList.size() - 1);
    }

    /**
     * Returns true if tokens are all already processed.
     *
     * @param parsedTokenList
     * @param left
     * @param right
     * @return
     */
    public static boolean areAllProcessed(ArrayList<ParsedToken> parsedTokenList, int left, int right) {
        for (int i = left, t = Math.min(right, parsedTokenList.size()); i < t; i++) {
            ParsedToken parsedToken = parsedTokenList.get(i);
            if (!parsedToken.isProcessed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * sets all token processed to false
     *
     * @param parsedTokenList
     */
    public static void eraseProcessed(ArrayList<ParsedToken> parsedTokenList) {
        for (ParsedToken parsedToken : parsedTokenList) {
            parsedToken.setProcessed(false);
        }
    }

    /**
     * Returns the operator with the highest priority
     *
     * @param parsedTokenList
     * @return
     */
    public static ParsedToken getMostOperatorPriority(ArrayList<ParsedToken> parsedTokenList) {
        return getMostOperatorPriority(parsedTokenList, 0, parsedTokenList.size());
    }

    /**
     * Gets the operator between some limits.
     *
     * @param parsedTokenList
     * @param minLimit
     * @param maxLimit
     * @return
     */
    public static ParsedToken getMostOperatorPriority(ArrayList<ParsedToken> parsedTokenList, int minLimit, int maxLimit) {
        ParsedToken mostOperator = null;

        for (int i = minLimit, t = maxLimit; i < t; i++) {
            ParsedToken parsedToken = parsedTokenList.get(i);

            // function or operator...
            if (!(parsedToken.isOperator()) || parsedToken.isProcessed()) {
                continue;
            }
            if (mostOperator == null || mostOperator.getPriority() < parsedToken.getPriority()) {
                mostOperator = parsedToken;
            }
        }

        return mostOperator;
    }

    /**
     * Gets the left operand.
     *
     * @param parsedTokenList
     * @param operatorPosition
     * @return
     */
    public static ParsedToken getLeftOperand(ArrayList<ParsedToken> parsedTokenList, int operatorPosition) throws ParsingException {
        System.out.println("looking for left operand: " + operatorPosition);
        operatorPosition--;
        if (operatorPosition < 0) {
            throw new ParsingException("Problème d'index d'opérateur.");
        }

        return parsedTokenList.get(operatorPosition);
    }

    /**
     * Gets the right operand.
     *
     * @param parsedTokenList
     * @param operatorPosition
     * @return
     */
    public static ParsedToken getRightOperand(ArrayList<ParsedToken> parsedTokenList, int operatorPosition) throws ParsingException {
        operatorPosition++;
        if (parsedTokenList.size() <= operatorPosition) {
            throw new ParsingException("Problème d'index d'opérateur.");
        }
        return parsedTokenList.get(operatorPosition);
    }

    /**
     * Computes two operands with an operator.
     *
     * @param operator
     * @param leftR
     * @param rightR
     * @return
     */
    public static double compute(ParsedToken operator, double leftR, double rightR) throws UnexpectedException {
        double result = 0;
        switch (operator.getParsedType()) {
            case OPERATOR_PLUS:
                result += leftR + rightR;
                break;
            case OPERATOR_DIVIDE:
                result += leftR / rightR;
                break;
            case OPERATOR_MULTIPLY:
                result += leftR * rightR;
                break;
            case OPERATOR_MINUS:
                result += leftR - rightR;
                break;
            case OPERATOR_MODULO:
                result += leftR % rightR;
                break;
            case OPERATOR_POWER:
                result += Math.pow(leftR, rightR);
                break;

            default:
                throw new UnexpectedException("Un opérateur est inconnu.");
        }
        return result;

    }

    public static ParsedToken getMostFunctionPriority(ArrayList<ParsedToken> parsedTokenList) {
        ParsedToken mostOperator = null;

        for (int i = 0, t = parsedTokenList.size(); i < t; i++) {
            ParsedToken parsedToken = parsedTokenList.get(i);

            // function or operator...
            if (!(parsedToken.isFunction()) || parsedToken.isProcessed()) {
                continue;
            }
            if (mostOperator == null || mostOperator.getPriority() < parsedToken.getPriority()) {
                mostOperator = parsedToken;
            }
        }

        return mostOperator;
    }

    public static boolean allFunctionsAreProcessed(ArrayList<ParsedToken> parsedTokenList) {
        ParsedToken mostOperator = null;

        for (int i = 0, t = parsedTokenList.size(); i < t; i++) {
            ParsedToken parsedToken = parsedTokenList.get(i);

            // function or operator...
            if (parsedToken.isFunction() && !parsedToken.isProcessed()) {
                return false;
            }
        }

        return true;
    }

}
