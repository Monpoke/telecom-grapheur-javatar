/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.treeverter;

import graphtest.TreeNode;
import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_PAR_CLOSE;
import graphtest.parsed.TOK_PAR_OPEN;
import java.util.ArrayList;

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

        this.createTree();
    }

    private void createTree() {

        processPriorities();

        /**
         * Tokens not processed yet
         */
        while (true != this.tokensAreAllProcessed()) {

            System.out.println("tour");

            // highest operator
            ParsedToken operator = getMostOperatorPriority();
            int operatorPosition = parsedTokenList.indexOf(operator);

            TreeNode thisNode = new TreeNode(operator);

            // get left operand
            ParsedToken leftOperand = getLeftOperand(operatorPosition);
            thisNode.setLeft(new TreeNode(leftOperand));

            // get right operand
            ParsedToken rightOperand = getRightOperand(operatorPosition);
            thisNode.setRight(new TreeNode(rightOperand));

            // associate the root child
            if (root == null) {
                root = thisNode;
            } else {

                // find the LEFT parent because it's already processed with an other operator
                if(leftOperand.isProcessed()){
                    TreeNode leftParent = findParent(root, leftOperand);
                    thisNode.setLeft(leftParent);
                }
                // find the RIGHT parent because it's already processed with an other operator
                if(rightOperand.isProcessed()){
                    TreeNode rightParent = findParent(root, rightOperand);
                    thisNode.setRight(rightParent);
                }
                
                
                root=thisNode;
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

    private TreeNode newNode() {
        return null;
    }

    public ArrayList<ParsedToken> getParsedTokenList() {
        return parsedTokenList;
    }

    public TreeNode getRoot() {
        return root;
    }

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
     * Gets the operator with the highest priority.
     *
     * @return
     */
    private ParsedToken getMostOperatorPriority() {
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
     * Returns true when all operators have been processed.
     *
     * @return
     */
    private boolean tokensAreAllProcessed() {
        for (ParsedToken parsedToken : parsedTokenList) {
            if (!parsedToken.isProcessed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets left operand.
     *
     * @param operatorPosition
     * @return
     */
    private ParsedToken getLeftOperand(int operatorPosition) {
        return parsedTokenList.get(operatorPosition - 1);
    }

    private ParsedToken getRightOperand(int operatorPosition) {
        return parsedTokenList.get(operatorPosition + 1);
    }

    /**
     * Find the parent.
     * @param tree
     * @param tok
     * @return 
     */
    private TreeNode findParent(TreeNode tree, ParsedToken tok) {
        if (!tok.isProcessed()) {
            return null;
        }

        if (tree.getLeft().getToken() == tok || tree.getRight().getToken() == tok) {
            return tree;
        }

        // left, then right
        TreeNode toR = findParent(tree.getLeft(), tok);
        if (toR == null) {
            toR = findParent(tree.getRight(), tok);
        }

        return toR;
    }
}
