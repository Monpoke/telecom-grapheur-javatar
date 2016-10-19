/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.evaluator;

import graphtest.TreeNode;
import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_OPERATOR_PLUS;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author A643012
 */
public class EvaluatorTest {
    
    public EvaluatorTest() {
    }

    @Test
    public void simple_addition() {
        
        ParsedToken number_1 = new TOK_NUMBER(10);
        ParsedToken number_2 = new TOK_NUMBER(20);
        ParsedToken addition = new TOK_OPERATOR_PLUS();
        
        // create tree
        TreeNode root = new TreeNode(addition, number_1, number_2);
        
        // asserts
        Evaluator e = new Evaluator(root);
        assertEquals(10+20, e.evaluate(), 1);
    }
    
}
