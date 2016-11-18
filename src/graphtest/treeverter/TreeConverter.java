/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.treeverter;

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

    /**
     *
     * @param parsedTokenList
     */
    public TreeConverter(ArrayList<ParsedToken> parsedTokenList) {
        this.parsedTokenList = parsedTokenList;

        // add the plus
        TokenSimplifier.simplify(parsedTokenList);
//        Evaluator.simplifyCompute(parsedTokenList);

        this.createTree();
    }

    private void createTree() {

        // Priorities
        processPriorities();

        // not needed
        removeParenthesis();

        /**
         * Tokens not processed yet
         */
        while (true != TokensTools.areAllProcessed(parsedTokenList)) {

            System.out.println("tour");

            // highest operator
            ParsedToken operator = TokensTools.getMostOperatorPriority(parsedTokenList);
            int operatorPosition = parsedTokenList.indexOf(operator);

            TreeNode thisNode = new TreeNode(operator);

            // get left operand
            ParsedToken leftOperand = TokensTools.getLeftOperand(parsedTokenList, operatorPosition);
            thisNode.setLeft(new TreeNode(leftOperand));

            // get right operand
            ParsedToken rightOperand = TokensTools.getRightOperand(parsedTokenList, operatorPosition);
            thisNode.setRight(new TreeNode(rightOperand));

            // associate the root child
            if (root == null) {
                root = thisNode;
            } else {

                /**
                 * If different from 0: => negative: the operator to find is on
                 * the left
                 */
                int operatorDirection = 0;

                // find the LEFT parent because it's already processed with an other operator
                if (leftOperand != null && leftOperand.isProcessed()) {
                    TreeNode leftParent = findParent(root, leftOperand);

                    if (leftParent != null) {
                        System.out.println("Oh! LEFT used... " + leftOperand.toString() + "<-" + leftParent.getToken());
                        thisNode.setLeft(leftParent);
                    } else {
                        System.out.println("Oh! LEFT used... " + leftOperand.toString() + "<- NULL");

                        // it's the parent
                    }
                }

                // find the RIGHT parent because it's already processed with an other operator
                if (rightOperand != null && rightOperand.isProcessed()) {
                    TreeNode rightParent = findParent(root, rightOperand);

                    if (rightParent != null) {
                        System.out.println("Oh! RIGHT used... " + rightOperand.toString() + "<-" + rightParent.getToken());
                        thisNode.setRight(rightParent);
                    } else {
                        System.out.println("Oh! RIGHT used... " + rightOperand.toString() + "<- NULL");
                    }
                }

                root = thisNode;
            }
            
            

            operator.setProcessed(true);
            leftOperand.setProcessed(true);
            rightOperand.setProcessed(true);

            // logs
            System.out.println("OPER token: " + operator.toString());
            System.out.println("LEFT child: " + leftOperand.toString());
            System.out.println("RIGT child: " + rightOperand.toString());

            System.out.println("");
        }

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
