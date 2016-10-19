/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.evaluator;

import graphtest.TreeNode;
import graphtest.parsed.TokenType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author A643012
 */
public class Evaluator {

    /**
     * Contains the node tree.
     */
    private final TreeNode root;
    private List<Variable> variables;

    public Evaluator(TreeNode root) {
        this.root = root;

        resetScope();
    }

    public void resetScope() {
        variables = new ArrayList<>();
    }

    /**
     *
     * @param variable
     */
    public void addVariable(Variable variable) {
        this.variables.add(variable);
    }

    double evaluate() {
        double result = 0;

        /**
         * ALGO
         */
        result += process(root);

        return result;
    }

    public double process(TreeNode node) {
        double result = 0;

        double leftR, rightR;

        if (node.getToken().isOperator()) {
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
        } // return number
        else if (node.getToken().getParsedType().equals(TokenType.NUMBER)) {
            return node.getToken().getValue();
        }

        System.out.println("returns=> " + result);
        return result;
    }

}
