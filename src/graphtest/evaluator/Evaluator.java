/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.evaluator;

import graphtest.BTreePrinter;
import graphtest.TreeNode;
import graphtest.exceptions.UnexpectedException;
import graphtest.exceptions.VariableException;
import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TokenType;
import graphtest.tools.TokensTools;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author A643012
 */
public class Evaluator {

    /**
     * Contains the node tree.
     */
    private final TreeNode root;
    private Map<String, Variable> variables;

    public Evaluator(TreeNode root) throws Exception {
        this.root = root;

        this.simplifyCompute(root);

        BTreePrinter bTreePrinter = new BTreePrinter();
        System.out.println("simpliocation result");
        bTreePrinter.printNode(root);
        
        resetScope();
    }

    /**
     * Reset the scope
     */
    public void resetScope() {
        variables = new HashMap<>();

        // ADD CONSTANT
        addVariable(new Variable("pi", Math.PI));

    }

    /**
     *
     * @param variable
     */
    public void addVariable(Variable variable) {
        this.variables.put(variable.getName(), variable);
    }

    /**
     * Call this method to evaluate with the current scope.
     * @return
     * @throws Exception 
     */
    public double evaluate() throws Exception {
        double result = 0;

        /**
         * ALGO
         */
        result += process(root);

        return result;
    }

    /**
     * Process
     *
     * @param node
     * @return
     * @throws java.lang.Exception
     */
    protected double process(TreeNode node) throws Exception {
        double result = 0;

        if (node.getToken().isOperator()) {
            result += processOperators(node);

        } else if (node.getToken().isFunction()) {
            result += processFunction(node);
        } // return number
        else if (node.getToken().getParsedType().equals(TokenType.NUMBER)) {
            return node.getToken().getValue();
        } // add variables
        else if (node.getToken().getParsedType().equals(TokenType.VARIABLE)) {
            // check if variable is set
            if (!variables.containsKey(node.getToken().getVariableName())) {
                throw new VariableException(node.getToken().getVariableName() + " <= is not set!");
            }
            return variables.get(node.getToken().getVariableName()).getValue();
        }

        System.out.println("returns=> " + result);
        return result;
    }

    /**
     * Operators
     *
     * @param node
     * @return
     */
    private double processOperators(TreeNode node) throws Exception {
        double leftR, rightR, result = 0;

        leftR = process(node.getLeft());
        rightR = process(node.getRight());

        return TokensTools.compute(node.getToken(), leftR, rightR);
    }

    /**
     * Functions
     *
     * @param node
     * @return
     */
    private double processFunction(TreeNode node) throws Exception {
        double leftR, rightR, result = 0;

        switch (node.getToken().getParsedType()) {
            case FCT_COS:
                leftR = Math.cos(process(node.getLeft()));
                result += leftR;
                System.out.println("cos(" + leftR + ")");
                break;
            case FCT_SIN:
                leftR = Math.sin(process(node.getLeft()));
                result += leftR;
                System.out.println("sin(" + leftR + ")");
                break;
            case FCT_TAN:
                leftR = Math.tan(process(node.getLeft()));
                result += leftR;
                System.out.println("tan(" + leftR + ")");
                break;

        }

        return result;
    }

    /**
     *
     * @param parsedTokenList
     */
    public static void simplifyCompute(ArrayList<ParsedToken> parsedTokenList) throws UnexpectedException {

        // foreach each token, simplify
        while (true != TokensTools.areAllProcessed(parsedTokenList)) {

            ParsedToken operator = TokensTools.getMostOperatorPriority(parsedTokenList);
            int operatorPosition = parsedTokenList.indexOf(operator);

            ParsedToken leftOperand = TokensTools.getLeftOperand(parsedTokenList, operatorPosition);
            ParsedToken rightOperand = TokensTools.getRightOperand(parsedTokenList, operatorPosition);

            /*
           If both operand are numbers, we can compute them
             */
            // check processed
            if (operator != null) {
                operator.setProcessed(true);
            }

            if (leftOperand != null) {
                leftOperand.setProcessed(true);
            }

            if (rightOperand != null) {
                rightOperand.setProcessed(true);
            }

            // if one of them is null, continue
            if (operator == null || leftOperand == null || rightOperand == null) {
                continue;
            } else if (!(leftOperand instanceof TOK_NUMBER) // none of the operand are number
                    || !(rightOperand instanceof TOK_NUMBER)) {
                continue;
            }

            // simplify
            TOK_NUMBER simplification = new TOK_NUMBER(TokensTools.compute(operator, leftOperand.getValue(), rightOperand.getValue()));
            System.out.println("Simplification: " + leftOperand.toString() + " " + operator.toString() + " " + rightOperand.toString() + " => " + simplification.toString());

        }

        TokensTools.eraseProcessed(parsedTokenList);
    }

    private boolean simplifyCompute(TreeNode node) throws Exception {

        // LEFT
        if (node.getLeft() != null && node.getLeft().getLeft() != null) {
            // process child...
            if (false == simplifyCompute(node.getLeft())) {
                return false;
            }
        }

        // RIGHT
        if (node.getRight() != null && node.getRight() != null && node.getRight().getRight() != null) {
            if (false == simplifyCompute(node.getRight())) {
                return false;
            }
        }

        simplifyComputeProcess(node);

        return true;
    }

    /**
     * In order to simplify the tree.
     * @param node
     * @return
     * @throws Exception 
     */
    private boolean simplifyComputeProcess(TreeNode node) throws Exception {

        if (node.getLeft() == null || node.getRight() == null) {
            return false;
        }

        // if not numbers, easy to simplify
        if (!(node.getLeft().getToken() instanceof TOK_NUMBER && node.getRight().getToken() instanceof TOK_NUMBER)) {
            return false;
        }

        // can compute both
        double result = process(node);
        node.setToken(new TOK_NUMBER(result));
        node.setLeft(null);
        node.setRight(null);
        
        
        return true;
    }

}
