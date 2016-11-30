/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.evaluator;

import graphtest.BTreePrinter;
import graphtest.TreeNode;
import graphtest.exceptions.MathException;
import graphtest.exceptions.ParsingException;
import graphtest.exceptions.UnexpectedException;
import graphtest.exceptions.VariableException;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TokenType;
import graphtest.tools.TokensTools;
import java.util.HashMap;
import java.util.Map;

/**
 * The evaluator needs a tree to work.
 *
 * @author Pierre BOURGEOIS
 */
public class EvaluatorV2 {

    /**
     * Contains the node tree.
     */
    private final TreeNode root;
    private Map<String, Variable> variables;

    /**
     * Constructor.
     *
     * @param root
     * @throws Exception
     */
    public EvaluatorV2(TreeNode root) throws Exception {
        this.root = root;

        BTreePrinter bTreePrinter = new BTreePrinter();
        //System.out.println("Simplified tree:");
        //bTreePrinter.printNode(root);

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
     * Add a variable into the scope.
     *
     * @param variable
     */
    public void addVariable(Variable variable) {
        this.variables.put(variable.getName(), variable);
    }

    /**
     * Call this method to evaluate with the current scope.
     *
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

    public double evaluateV2() {
        double result = 0;

        result += processV2(root);

        return result;
    }

    /**
     *
     * @param root
     * @return
     */
    public double processV2(TreeNode root) {
        double result = 0;

        System.out.println("ROOT=" + root.toString());
        // no child, only one element
        if (root.getLeft() == null) {
            return root.getToken().getValue();
        }
        System.out.println("ROOT___2=" + root.toString());

        // default value
        result = root.getToken().getValue();

        TreeNode currentNode = root.getLeft();

        while (currentNode != null) {
            //  System.out.println("CURRENT, je suis=" + currentNode.toString());

            if (currentNode.getToken().isOperator()) {
                System.out.println("Je passe par " + currentNode.toString());

                result = processOperatorsV2(currentNode, result, currentNode.getLeft().getToken().getValue());
            }
            
            
            if (currentNode.getLeft() != null && currentNode.getLeft().getLeft() != null) {
                currentNode = currentNode.getLeft().getLeft();
            } else {
                currentNode = currentNode.getLeft();
            }
        }

        return result;
    }

    protected double processOperatorsV2(TreeNode operator, double left, double right) {

        switch (operator.getToken().getParsedType()) {
            case OPERATOR_PLUS:
                return left + right;
            case OPERATOR_MULTIPLY:
                return left * right;
            case OPERATOR_DIVIDE:
                return left / right;
            case OPERATOR_MINUS:
                return left - right;

        }

        return 0;
    }

    /**
     * Process by recursive method.
     *
     * @param node
     * @return
     * @throws java.lang.Exception
     */
    protected double process(TreeNode node) throws ParsingException, VariableException, MathException, UnexpectedException {
        double result = 0;

        // NO OPERATOR? KILLL
        if (node == null || node.getToken() == null) {
            throw new ParsingException("Token nul...");
        }

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

        /**
         * Throw MathException
         */
        if (!Double.isFinite(result)) {
            throw new MathException("Résultat non fini...");
        }

        //  System.out.println("returns=> " + result);
        return result;
    }

    /**
     * Operators.
     *
     * @param node
     * @return
     */
    private double processOperators(TreeNode node) throws UnexpectedException, ParsingException, VariableException, MathException {
        double leftR, rightR;

        leftR = process(node.getLeft());
        rightR = process(node.getRight());

        return TokensTools.compute(node.getToken(), leftR, rightR);
    }

    /**
     * Functions.
     *
     * @param node
     * @return
     */
    private double processFunction(TreeNode node) throws ParsingException, VariableException, MathException, UnexpectedException {
        double leftR, result = 0;

        switch (node.getToken().getParsedType()) {
            case FCT_SQRT:
                leftR = Math.sqrt(process(node.getLeft()));
                result += leftR;
                break;
            case FCT_COS:
                leftR = Math.cos(process(node.getLeft()));
                result += leftR;
                break;
            case FCT_SIN:
                leftR = Math.sin(process(node.getLeft()));
                result += leftR;
                break;
            case FCT_TAN:
                leftR = Math.tan(process(node.getLeft()));
                result += leftR;
                break;

        }

        return result;
    }

    /**
     * Allows the simplification of a tree.
     *
     * @param node
     * @return
     * @throws Exception
     */
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
     *
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
