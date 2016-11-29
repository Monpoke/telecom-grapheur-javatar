package graphtest.evaluator;

import graphtest.TreeNode;
import graphtest.syntaxor.Stack;

/**
 *
 * @author Florent
 */
public class StackTreeConverter {
    
    private Stack stack;
    private TreeNode root;
    
    public StackTreeConverter(Stack stack){
        this.stack = stack;
        startConvertStackToTree();
    }
    
    private void startConvertStackToTree(){
        while(!stack.empty()){
            if(root == null){
                this.root = new TreeNode(stack.pop());
            }else{
                root.setLeftChild(new TreeNode(stack.pop()));
            }
        }
    }
    
    public TreeNode getRoot(){
        return this.root;
    }
}
