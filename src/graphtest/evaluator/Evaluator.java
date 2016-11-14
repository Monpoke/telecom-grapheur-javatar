/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.evaluator;

import graphtest.TreeNode;
import graphtest.exceptions.VariableException;
import graphtest.parsed.TokenType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public Evaluator(TreeNode root) {
        this.root = root;

        resetScope();
    }

    public void resetScope() {
        variables = new HashMap<>();
        
        // ADD CONSTANT
        addVariable(new Variable("pi",Math.PI));
        
        
        
    }

    /**
     *
     * @param variable
     */
    public void addVariable(Variable variable) {
        this.variables.put(variable.getName(), variable);
    }

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
     */
    protected double process(TreeNode node) throws Exception {
        double result = 0;
        
        if (node.getToken().isOperator()) {
            result += processOperators(node);

        } else if (node.getToken().isFunction()) {
            result += processFunction(node);
        } 

        // return number
        else if (node.getToken().getParsedType().equals(TokenType.NUMBER)) {
            return node.getToken().getValue();
        }
        // add variables
        else if (node.getToken().getParsedType().equals(TokenType.VARIABLE)) {
            // check if variable is set
            if(!variables.containsKey(node.getToken().getVariableName())){
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
    private double processOperators(TreeNode node) throws Exception{
        double leftR, rightR, result = 0;

        switch (node.getToken().getParsedType()) {
            case OPERATOR_PLUS:
                leftR = process(node.getLeft());
                rightR = process(node.getRight());
                result += leftR + rightR;
                System.out.println(leftR + "+" + rightR);
                break;
            case OPERATOR_DIVIDE:
                leftR = process(node.getLeft());
                rightR = process(node.getRight());
                result += leftR / rightR;
                System.out.println(leftR + "/" + rightR);
                break;
            case OPERATOR_MULTIPLY:
                leftR = process(node.getLeft());
                rightR = process(node.getRight());
                result += leftR * rightR;
                System.out.println(leftR + "*" + rightR);
                break;
            case OPERATOR_MINUS:
                leftR = process(node.getLeft());
                rightR = process(node.getRight());
                result += leftR - rightR;
                System.out.println(leftR + "-" + rightR);
                break;
        }

        return result;
    }

    /**
     * Operators
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
}
