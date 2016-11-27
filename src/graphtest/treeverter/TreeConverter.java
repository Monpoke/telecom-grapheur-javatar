/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.treeverter;

import graphtest.BTreePrinter;
import graphtest.TreeNode;
import graphtest.exceptions.ParsingException;
import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_PAR_CLOSE;
import graphtest.parsed.TOK_PAR_OPEN;
import graphtest.parsed.TOK_VARIABLE;
import graphtest.tools.TokensTools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author A643012
 */
public class TreeConverter {

    private final ArrayList<ParsedToken> parsedTokenList;

    private List<TreeNode> allNodes;

    private TreeNode root = null;

    private BTreePrinter bTreePrinter = new BTreePrinter();

    private int leftLimit = 0;
    private int rightLimit = 0;

    /**
     *
     * @param parsedTokenList
     * @throws graphtest.exceptions.ParsingException
     */
    public TreeConverter(ArrayList<ParsedToken> parsedTokenList) throws ParsingException {
        this.parsedTokenList = parsedTokenList;

        // add the plus
        TokenSimplifier.simplify(parsedTokenList);

        // process limits
        resetLimits();

        // create a list of all processed nodes
        allNodes = new ArrayList<>();

        List<TreeNode> createTree = this.createTree();

        if (createTree.size() == 1) {
            root = createTree.iterator().next();
        } else {
            throw new ParsingException("Erreur de parsage.");
        }

    }

    /**
     * Resets limits
     */
    private void resetLimits() {
        leftLimit = 0;
        rightLimit = parsedTokenList.size();
    }

    /**
     * Returns the nb in the list, following the limits
     * @return 
     */
    private int nbElementsInList() {
        System.out.println((rightLimit - leftLimit));
        return (rightLimit - leftLimit) ;
    }

    /**
     * Creates a tree.
     *
     * @return
     * @throws ParsingException
     */
    private List<TreeNode> createTree() throws ParsingException {

        System.out.println("CREATE TREE FOR THESE LIMITS: " + leftLimit + " -> " + rightLimit);

        // for only one token
        if (nbElementsInList() == 1) {
            ParsedToken number = parsedTokenList.get(leftLimit);
            number.setProcessed(true);
            if (number instanceof TOK_NUMBER || number instanceof TOK_VARIABLE) {
                allNodes.add(new TreeNode(number));
            } else {
                throw new ParsingException("L'expression n'est pas valide. Expected: number|variable. Found: "+number.toString());
            }
            return allNodes;
        }

        if (leftLimit == 0) {

            // Priorities
            processPriorities();

            // FUNCTIONS
            processFunctions();

            // not needed -> USEFUL FOR FUNCTIONS
            removeParenthesis();

        }

        // création de tout les noeuds 
        while (true != TokensTools.areAllProcessed(parsedTokenList, leftLimit, rightLimit)) {

            System.out.println("[NOUVEAU TOUR -> nodes:" + allNodes.size() + "] with following limits: " + leftLimit + " -> " + rightLimit);

            ParsedToken operator = TokensTools.getMostOperatorPriority(parsedTokenList);

            // stop the loop, no operator
            if (operator == null) {
                System.out.println("Unknown operator! :o I STOP THE PROCESS");
                break;
            }

            int operatorPosition = parsedTokenList.indexOf(operator);

            System.out.println("Found operator: "+ operator.toString() + " at position: " + operatorPosition);
            
            // right
            ParsedToken rightOperand = TokensTools.getRightOperand(parsedTokenList, operatorPosition);

            // left operand
            ParsedToken leftOperand = TokensTools.getLeftOperand(parsedTokenList, operatorPosition);

            // IF THERE ARE TWO FOLLOWING OPERATORS
            if(leftOperand.isOperator() || rightOperand.isOperator()){
                throw new ParsingException("Deux opérateurs ne peuvent se suivre...");
            }
            
            
            System.out.println("Have to process: " + leftOperand.toString() + " " + operator.toString() + " " + rightOperand.toString());

            // check not processed
            if (leftOperand.isProcessed() || rightOperand.isProcessed()) {

                processLeftOrRight(operator, leftOperand, rightOperand);

                System.out.println("SIZE=" + allNodes.size());

                System.out.println("___________________________");
                continue;
            } else {
                System.out.println("Neither of operands are processed...");
            }

            TreeNode thisNode = new TreeNode(operator, leftOperand, rightOperand);
            // push in the list
            allNodes.add(thisNode);
            System.out.println("Adding to node list:");

            // set processed
            operator.setProcessed(true);
            leftOperand.setProcessed(true);
            rightOperand.setProcessed(true);

            System.out.println("___________________________");
        }

        // reset limits
        resetLimits();

        return allNodes;
    }

    /**
     * Find the parent of an operand.
     *
     * @param list
     * @param operand
     * @return
     */
    private TreeNode findParent(List<TreeNode> list, ParsedToken operand) {
        for (TreeNode treeNode : list) {
            if (treeNode.childContains(operand)) {
                return treeNode;
            } else if (treeNode.getToken() == operand) {
                return treeNode;
            }
        }

        return null;
    }

    /**
     * Find the parent.
     *
     * @param tree
     * @param tok
     * @return
     */
    private TreeNode findParent(TreeNode tree, ParsedToken tok) {
        if (tok == null || !tok.isProcessed()) {
            return null;
        }

        if (tree.getLeft() != null && tree.getLeft().getToken() == tok || tree.getRight() != null && tree.getRight().getToken() == tok) {
            return tree;
        }

        // left, then right
        TreeNode toR = null;

        if (tree.getLeft() != null) {
            findParent(tree.getLeft(), tok);
        }

        if (toR == null && tree.getRight() != null) {
            toR = findParent(tree.getRight(), tok);
        }

        return toR;
    }

    public ArrayList<ParsedToken> getParsedTokenList() {
        return parsedTokenList;
    }

    public TreeNode getRoot() {
        return root;
    }

    /**
     * Gets position
     *
     * @param firstPosition
     * @return
     * @throws ParsingException
     */
    private int findLastParenthesisPosition(int firstPosition) throws ParsingException {
        int level = 1;

        for (int i = firstPosition, t = parsedTokenList.size(); i < t; i++) {
            if (parsedTokenList.get(i) instanceof TOK_PAR_OPEN) {
                level++;
            }
            if (parsedTokenList.get(i) instanceof TOK_PAR_CLOSE) {
                level--;

                if (level == 0) {
                    return i;
                } else // niveau de parenthèse non correspondant
                {
                    if (level < 0) {
                        throw new ParsingException("Niveau de parenthèsage incohérent.");
                    }
                }
            }

        }

        throw new ParsingException("Niveau de parenthèsage incohérent.");
    }

    /**
     * This function computes the token priorities
     */
    private void processPriorities() {
        // count how many parenthesis they are
        int parenthesisLevel = 1;

        for (ParsedToken current : parsedTokenList) {
            if (current instanceof TOK_PAR_OPEN) {
                parenthesisLevel *= current.getParsedType().getDefaultPriority();
            } else if (current instanceof TOK_PAR_CLOSE) {
                parenthesisLevel /= current.getParsedType().getDefaultPriority();
            }

            // set priority for the current token
            current.setPriority(parenthesisLevel * current.getParsedType().getDefaultPriority());

        }
    }

    /**
     * Removes useless parenthesis, because priorities have been set.
     */
    private void removeParenthesis() {

        Iterator<ParsedToken> iterator = parsedTokenList.iterator();
        List<ParsedToken> toRemove = new ArrayList<>();
        while (iterator.hasNext()) {
            ParsedToken current = iterator.next();
            if (current instanceof TOK_PAR_CLOSE || current instanceof TOK_PAR_OPEN) {
                toRemove.add(current);
            }
        }

        // remove...
        for (ParsedToken parsedToken : toRemove) {
            parsedTokenList.remove(parsedToken);
        }

    }

    /**
     * Process the operands.
     *
     * @param operator
     * @param leftOperand
     * @param rightOperand
     */
    private void processLeftOrRight(ParsedToken operator, ParsedToken leftOperand, ParsedToken rightOperand) {

        /**
         * L'opérande GAUCHE est traitée
         */
        if (leftOperand.isProcessed() && !rightOperand.isProcessed()) {
            System.out.println("left only processed...");

            System.out.println("findind parent for leftOper: " + leftOperand.toString());
            TreeNode leftTree = findParent(allNodes, leftOperand);
            System.out.println("-> " + leftTree);

            TreeNode newNode = new TreeNode(operator, leftTree, rightOperand);

            operator.setProcessed(true);
            rightOperand.setProcessed(true);

            // suppression de la liste
            allNodes.remove(leftTree);

            // add new tree
            allNodes.add(newNode);

        } else if (!leftOperand.isProcessed() && rightOperand.isProcessed()) {
            System.out.println("right only processed...");

            System.out.println("findind parent for rightOper: " + rightOperand.toString());
            TreeNode rightTree = findParent(allNodes, rightOperand);
            System.out.println("-> " + rightTree);

            TreeNode newNode = new TreeNode(operator, leftOperand, rightTree);

            operator.setProcessed(true);
            leftOperand.setProcessed(true);

            System.out.println("Before: " + allNodes.size());

            // suppression de la liste
            allNodes.remove(rightTree);
            System.out.println("After: " + allNodes.size());

            // add new tree
            allNodes.add(newNode);
        } else {

            System.out.println("both processed");

            System.out.println("findind parent for leftOper: " + leftOperand.toString());
            TreeNode leftTree = findParent(allNodes, leftOperand);
            System.out.println("-> " + leftTree);

            System.out.println("findind parent for rightOper: " + rightOperand.toString());
            TreeNode rightTree = findParent(allNodes, rightOperand);
            System.out.println("-> " + rightTree);

            TreeNode newNode = new TreeNode(operator, leftTree, rightTree);

            operator.setProcessed(true);
            leftOperand.setProcessed(true);
            rightOperand.setProcessed(true);

            // suppression de la liste
            allNodes.remove(leftTree);
            allNodes.remove(rightTree);

            // add new tree
            allNodes.add(newNode);

        }
    }

    private void processFunctions() throws ParsingException {

        while(true != TokensTools.allFunctionsAreProcessed(parsedTokenList)){
            ParsedToken function = TokensTools.getMostFunctionPriority(parsedTokenList);
            
            int functionPosition = parsedTokenList.indexOf(function);
            if (function.isFunction()) {

                ParsedToken nextToken = parsedTokenList.get(functionPosition + 1);

                /**
                 * Handle following syntax: - cos8 - cosx
                 */
                if (nextToken instanceof TOK_VARIABLE || nextToken instanceof TOK_NUMBER) {
                    // add node

                    // create the node
                    TreeNode functionNode = new TreeNode(function, nextToken);

                    // add node to list
                    allNodes.add(functionNode);

                    // set processed
                    function.setProcessed(true);
                    nextToken.setProcessed(true);

                   // i++; // skip the next token
                    continue;
                }

                /**
                 * Handle following syntax: - cos( 3+2+5+8 ...)
                 */
                if (nextToken instanceof TOK_PAR_OPEN) {

                    leftLimit = functionPosition + 2;
                    rightLimit = findLastParenthesisPosition(leftLimit);

                    System.out.println("DEFINING LIMITS " + leftLimit +" -> " + rightLimit +" for function " + function.toString());
                    
                    // for limits...
                    createTree();
                    System.out.println("BACK FROM CREATETREE!!");

                    // set processed
                    function.setProcessed(true);

                    // must find the parent of processed expression
                    ParsedToken randomTokenInLimits = parsedTokenList.get(functionPosition + 2);

                    System.out.println("Found this token inside the expression: " + randomTokenInLimits.toString());

                    TreeNode parentTree = findParent(allNodes, randomTokenInLimits);
                    System.out.println("Parent of this expression: ");
                    bTreePrinter.printNode(parentTree);

                    TreeNode functionTree = new TreeNode(function, parentTree);
                    allNodes.remove(parentTree);
                    allNodes.add(functionTree);

                    System.out.println("Final node for function " + function.toString());
                    bTreePrinter.printNode(functionTree);
                    
                    resetLimits();

                }
            }

        }
    }
}
