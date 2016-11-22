/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.treeverter;

import graphtest.BTreePrinter;
import graphtest.TreeNode;
import graphtest.evaluator.Evaluator;
import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_PAR_CLOSE;
import graphtest.parsed.TOK_PAR_OPEN;
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
    private TreeNode root = null;

    private BTreePrinter bTreePrinter = new BTreePrinter();

    /**
     *
     * @param parsedTokenList
     */
    public TreeConverter(ArrayList<ParsedToken> parsedTokenList) {
        this.parsedTokenList = parsedTokenList;

        // add the plus
        TokenSimplifier.simplify(parsedTokenList);
//        Evaluator.simplifyCompute(parsedTokenList);
        List<TreeNode> createTree = this.createTree();

        if (createTree.size() == 1) {
            root = createTree.iterator().next();
        } else {
            System.out.println("How to process??");
        
            for (TreeNode treeNode : createTree) {
                bTreePrinter.printNode(treeNode);
            }
        
        }
        

    }

    private List<TreeNode> createTree() {

        // Priorities
        processPriorities();

        // not needed
        removeParenthesis();

        List<TreeNode> allNodes = new ArrayList<>();

        int nbProcessed = 0;
        int listSize = parsedTokenList.size();

        // création de tout les noeuds 
        while (listSize != nbProcessed && true != TokensTools.areAllProcessed(parsedTokenList)) {

            System.out.println("[NOUVEAU TOUR ] ");

            ParsedToken operator = TokensTools.getMostOperatorPriority(parsedTokenList);

// stop the loop, no operator
            if (operator == null) {
                break;
            }

            int operatorPosition = parsedTokenList.indexOf(operator);
            System.out.println("[NOUVEAU TOUR ] ");

            // left operand
            ParsedToken leftOperand = TokensTools.getLeftOperand(parsedTokenList, operatorPosition);

            // right
            ParsedToken rightOperand = TokensTools.getRightOperand(parsedTokenList, operatorPosition);

            // check not processed
            if (leftOperand.isProcessed() || rightOperand.isProcessed()) {
                nbProcessed++;

                System.out.println("IM THE OPERATOR " + operator.toString());

                if (leftOperand.isProcessed() && !rightOperand.isProcessed()) {
                    System.out.println("left only processed...");

                    System.out.println("findind parent for leftOper: " + leftOperand.toString());
                    TreeNode leftTree = findParent(allNodes, leftOperand);
                    System.out.println("-> " + leftTree);

                    TreeNode newNode = new TreeNode(operator, leftTree, rightOperand);

                    operator.setProcessed(true);

                    // suppression de la liste
                    allNodes.remove(leftTree);

                    // add new tree
                    allNodes.add(newNode);

                    // printing the new node
                    bTreePrinter.printNode(newNode);

                } else if (!leftOperand.isProcessed() && rightOperand.isProcessed()) {
                    System.out.println("right only processed...");

                    System.out.println("findind parent for rightOper: " + rightOperand.toString());
                    TreeNode rightTree = findParent(allNodes, rightOperand);
                    System.out.println("-> " + rightTree);

                    TreeNode newNode = new TreeNode(operator, leftOperand, rightTree);

                    operator.setProcessed(true);

                    // suppression de la liste
                    allNodes.remove(rightTree);

                    // add new tree
                    allNodes.add(newNode);

                    // printing the new node
                    bTreePrinter.printNode(newNode);

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

                    // suppression de la liste
                    allNodes.remove(leftTree);
                    allNodes.remove(rightTree);

                    // add new tree
                    allNodes.add(newNode);

                    // printing the new node
                    bTreePrinter.printNode(newNode);
                }

                System.out.println("SIZE=" + allNodes.size());

                continue;
            }

            TreeNode thisNode = new TreeNode(operator, leftOperand, rightOperand);
            // push in the list
            allNodes.add(thisNode);
            System.out.println("Adding to node list:");
            bTreePrinter.printNode(thisNode);

            // set processed
            operator.setProcessed(true);
            leftOperand.setProcessed(true);
            rightOperand.setProcessed(true);
            nbProcessed += 3;
        }

        return allNodes;
    }

    private TreeNode findParent(List<TreeNode> list, ParsedToken operand) {
        for (TreeNode treeNode : list) {
            if (treeNode.childContains(operand)) {
                return treeNode;
            }
        }
        return null;
    }

    public ArrayList<ParsedToken> getParsedTokenList() {
        return parsedTokenList;
    }

    public TreeNode getRoot() {
        return root;
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
}
