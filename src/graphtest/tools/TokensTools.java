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
        for (ParsedToken parsedToken : parsedTokenList) {
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
        ParsedToken mostOperator = null;

        for (ParsedToken parsedToken : parsedTokenList) {
            if (!parsedToken.isOperator() || parsedToken.isProcessed()) {
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
        if(operatorPosition<0) throw new ParsingException("Problème d'index d'opérateur.");
        
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
        if(parsedTokenList.size()<operatorPosition) throw new ParsingException("Problème d'index d'opérateur.");
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
                System.out.println(leftR + "+" + rightR);
                break;
            case OPERATOR_DIVIDE:
                result += leftR / rightR;
                System.out.println(leftR + "/" + rightR);
                break;
            case OPERATOR_MULTIPLY:
                result += leftR * rightR;
                System.out.println(leftR + "*" + rightR);
                break;
            case OPERATOR_MINUS:
                result += leftR - rightR;
                System.out.println(leftR + "-" + rightR);
                break;
            case OPERATOR_MODULO:
                result += leftR % rightR;
                System.out.println(leftR + "%" + rightR);
                break;

            default:
                throw new UnexpectedException("Un opérateur est inconnu.");
        }
        return result;

    }

}
