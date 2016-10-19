/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.evaluator;

import graphtest.TreeNode;
import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_OPERATOR_MINUS;
import graphtest.parsed.TOK_OPERATOR_MULTIPLY;
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
        System.out.println("[addition]");

        ParsedToken number_1 = new TOK_NUMBER(10);
        ParsedToken number_2 = new TOK_NUMBER(20);
        ParsedToken addition = new TOK_OPERATOR_PLUS();

        // create tree
        TreeNode root = new TreeNode(addition, number_1, number_2);

        // asserts
        Evaluator e = new Evaluator(root);
        assertEquals(10 + 20, e.evaluate(), 1);
    }

    @Test
    public void simple_multiplication() {
        System.out.println("[multiplication]");

        ParsedToken number_3 = new TOK_NUMBER(3);
        ParsedToken number_2 = new TOK_NUMBER(2);
        ParsedToken multiply = new TOK_OPERATOR_MULTIPLY();

        // create tree
        TreeNode root = new TreeNode(multiply, number_3, number_2);

        // asserts
        Evaluator e = new Evaluator(root);
        assertEquals((3 * 2), e.evaluate(), 1);
    }

    @Test
    public void simple_priorities() {
        System.out.println("[simple_priorities]");
        ParsedToken number_3 = new TOK_NUMBER(3);
        ParsedToken number_2 = new TOK_NUMBER(2);
        ParsedToken multiply = new TOK_OPERATOR_MULTIPLY();
        ParsedToken substraction = new TOK_OPERATOR_MINUS();

        // create tree
        TreeNode root = new TreeNode(substraction, new TreeNode(multiply, number_3, number_2), number_3);

        // asserts
        Evaluator e = new Evaluator(root);
        assertEquals((3 * 2) - 3, e.evaluate(), 1);
    }

}
