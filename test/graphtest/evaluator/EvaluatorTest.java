/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.evaluator;

import graphtest.TreeNode;
import graphtest.exceptions.VariableException;
import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_FCT_COS;
import graphtest.parsed.TOK_FCT_SIN;
import graphtest.parsed.TOK_FCT_TAN;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_OPERATOR_DIVIDE;
import graphtest.parsed.TOK_OPERATOR_MINUS;
import graphtest.parsed.TOK_OPERATOR_MULTIPLY;
import graphtest.parsed.TOK_OPERATOR_PLUS;
import graphtest.parsed.TOK_VARIABLE;
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
    public void simple_addition() throws Exception {
        System.out.println("[simple_addition]");

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
    public void simple_multiplication() throws Exception {
        System.out.println("[simple_multiplication]");

        ParsedToken number_3 = new TOK_NUMBER(3);
        ParsedToken number_2 = new TOK_NUMBER(2);
        ParsedToken multiply = new TOK_OPERATOR_MULTIPLY();

        // create tree
        TreeNode root = new TreeNode(multiply, number_3, number_2);

        // asserts
        Evaluator e = new Evaluator(root);
        assertEquals((3 * 2), e.evaluate(), 0.001);
    }

    @Test
    public void simple_substraction() throws Exception {
        System.out.println("[simple_substraction]");

        ParsedToken number_3 = new TOK_NUMBER(3);
        ParsedToken number_2 = new TOK_NUMBER(2);
        ParsedToken minus = new TOK_OPERATOR_MINUS();

        // create tree
        TreeNode root = new TreeNode(minus, number_3, number_2);

        // asserts
        Evaluator e = new Evaluator(root);
        assertEquals((3 - 2), e.evaluate(), 0.001);
    }

    @Test
    public void simple_division() throws Exception {
        System.out.println("[simple_division]");

        ParsedToken number_3 = new TOK_NUMBER(3);
        ParsedToken number_2 = new TOK_NUMBER(2);
        ParsedToken divide = new TOK_OPERATOR_DIVIDE();

        // create tree
        TreeNode root = new TreeNode(divide, number_3, number_2);

        // asserts
        Evaluator e = new Evaluator(root);
        assertEquals((3.0 / 2.0), e.evaluate(), 0.001);
    }

    @Test
    public void simple_priorities() throws Exception {
        System.out.println("[simple_priorities]");
        ParsedToken number_3 = new TOK_NUMBER(3);
        ParsedToken number_2 = new TOK_NUMBER(2);
        ParsedToken multiply = new TOK_OPERATOR_MULTIPLY();
        ParsedToken substraction = new TOK_OPERATOR_MINUS();

        // create tree
        TreeNode root = new TreeNode(substraction, new TreeNode(multiply, number_3, number_2), number_3);

        // asserts
        Evaluator e = new Evaluator(root);
        assertEquals((3 * 2) - 3, e.evaluate(), 0.001);
    }

    @Test
    public void function_sin() throws Exception {
        System.out.println("[function_sin]");
        ParsedToken number_3 = new TOK_NUMBER(3);
        ParsedToken number_2 = new TOK_NUMBER(2);
        ParsedToken multiply = new TOK_OPERATOR_MULTIPLY();
        ParsedToken sin = new TOK_FCT_SIN();

        // create tree
        TreeNode root = new TreeNode(sin, new TreeNode(multiply, number_3, number_2));

        // asserts
        Evaluator e = new Evaluator(root);
        assertEquals(Math.sin(3 * 2), e.evaluate(), 0.001);
    }

    @Test
    public void function_cos() throws Exception {
        System.out.println("[function_cos]");
        ParsedToken number_3 = new TOK_NUMBER(3);
        ParsedToken number_2 = new TOK_NUMBER(2);
        ParsedToken multiply = new TOK_OPERATOR_MULTIPLY();
        ParsedToken cos = new TOK_FCT_COS();

        // create tree
        TreeNode root = new TreeNode(cos, new TreeNode(multiply, number_3, number_2));

        // asserts
        Evaluator e = new Evaluator(root);
        assertEquals(Math.cos(3 * 2), e.evaluate(), 0.001);
    }
    
    @Test
    public void function_tan() throws Exception {
        System.out.println("[function_tan]");
        ParsedToken number_3 = new TOK_NUMBER(3);
        ParsedToken number_2 = new TOK_NUMBER(2);
        ParsedToken multiply = new TOK_OPERATOR_MULTIPLY();
        ParsedToken tan = new TOK_FCT_TAN();

        // create tree
        TreeNode root = new TreeNode(tan, new TreeNode(multiply, number_3, number_2));

        // asserts
        Evaluator e = new Evaluator(root);
        assertEquals(Math.tan(3 * 2), e.evaluate(), 0.001);
    }

    /**
     * Variable not set!
     *
     * @throws Exception
     */
    @Test(expected = VariableException.class)
    public void function_test_variable_simple_not_existing() throws Exception {

        double x = 12.0;

        System.out.println("[function_test_variable_simple_not_existing]");
        ParsedToken variable_x = new TOK_VARIABLE("x");
        ParsedToken number_40 = new TOK_NUMBER(40.0);
        ParsedToken number_2 = new TOK_NUMBER(2);
        ParsedToken multiply = new TOK_OPERATOR_MULTIPLY();
        ParsedToken addition = new TOK_OPERATOR_PLUS();

        // create tree
        TreeNode root = new TreeNode(addition, new TreeNode(multiply, number_40, variable_x), number_2);

        // asserts
        Evaluator e = new Evaluator(root);

        // have to add a variable
        assertEquals(40.0 * x + 2.0, e.evaluate(), 0.001);
    }

    /**
     * Variable not set!
     *
     * @throws Exception
     */
    @Test()
    public void function_test_variable_simple() throws Exception {

        double x = 2.0;

        System.out.println("[function_test_variable_simple]");
        ParsedToken variable_x = new TOK_VARIABLE("x");
        ParsedToken number_40 = new TOK_NUMBER(40);
        ParsedToken number_2 = new TOK_NUMBER(2);
        ParsedToken multiply = new TOK_OPERATOR_MULTIPLY();
        ParsedToken addition = new TOK_OPERATOR_PLUS();

        // create tree
        TreeNode root = new TreeNode(addition, new TreeNode(multiply, number_40, variable_x), number_2);

        // asserts
        Evaluator e = new Evaluator(root);

        // have to add a variable
        e.addVariable(new Variable("x", x));

        assertEquals(40.0 * x + 2.0, e.evaluate(), 0.001);
    }
    
    /**
     * Variable not set!
     *
     * @throws Exception
     */
    @Test()
    public void function_constant_pi() throws Exception {

        double x = 2.0;

        System.out.println("[function_constant_pi]");
        ParsedToken variable_pi = new TOK_VARIABLE("pi");
        ParsedToken number_3 = new TOK_NUMBER(3);
        ParsedToken multiply = new TOK_OPERATOR_MULTIPLY();

        // create tree
        TreeNode root = new TreeNode(multiply, number_3, variable_pi);

        // asserts
        Evaluator e = new Evaluator(root);


        assertEquals(3.0 * Math.PI, e.evaluate(), 0.001);
    }

    
}
