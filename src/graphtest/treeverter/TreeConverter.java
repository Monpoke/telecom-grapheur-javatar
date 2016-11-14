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
    private TreeNode root;

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

        // create root
        root = new TreeNode(null);

        for (int i = 0, t = parsedTokenList.size(); i < t - 1; i++) {
            ParsedToken current = parsedTokenList.get(i);
            System.out.println(current.toString());

            // get next, probably an operator
            ParsedToken next = parsedTokenList.get(i + 1);

        }

    }

    private TreeNode newNode() {
        return null;
    }

    public ArrayList<ParsedToken> getParsedTokenList() {
        return parsedTokenList;
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

    

}
