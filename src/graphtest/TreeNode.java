/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest;

import graphtest.parsed.ParsedToken;

/**
 *
 * @author A643012
 */
public class TreeNode {

    protected ParsedToken token;

    protected TreeNode left;
    protected TreeNode right;

    public TreeNode(ParsedToken token, ParsedToken left, ParsedToken right) {
        this.token = token;
        this.left = new TreeNode(left);
        this.right = new TreeNode(right);
    }

    public TreeNode(ParsedToken token, TreeNode left, TreeNode right) {
        this.token = token;
        this.left = left;
        this.right = right;
    }
    
    public TreeNode(ParsedToken token, ParsedToken left, TreeNode right) {
        this.token = token;
        this.left = new TreeNode(left);
        this.right = right;
    }
    
    public TreeNode(ParsedToken token, TreeNode left, ParsedToken right) {
        this.token = token;
        this.left = left;
        this.right = new TreeNode(right);
    }
    

    public TreeNode(ParsedToken token) {
        this.token = token;
    }

    public TreeNode(ParsedToken token, TreeNode left) {
        this.token = token;
        this.left = left;
    }

    public ParsedToken getToken() {
        return token;
    }

    public void setToken(ParsedToken token) {
        this.token = token;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

}
