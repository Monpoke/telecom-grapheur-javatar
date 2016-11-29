/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.syntaxor;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_FCT_COS;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_OPERATOR_DIVIDE;
import graphtest.parsed.TOK_OPERATOR_MULTIPLY;
import graphtest.parsed.TOK_OPERATOR_PLUS;
import graphtest.parsed.TOK_PAR_CLOSE;
import graphtest.parsed.TOK_PAR_OPEN;
import graphtest.parsed.TOK_VARIABLE;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Florent
 */
public class PriorityToolsTest {
    
    public PriorityToolsTest() {
    }

    /**
     * Test of addPriority method, of class PriorityTools.
     */
    @Test
    public void testAddPriority() {
        // --- Expected good priority set ---
        
        /* Testing : 3+x */
        ArrayList<ParsedToken> priorityArray = new ArrayList<>();
        priorityArray.add(new TOK_NUMBER(3));
        priorityArray.add(new TOK_OPERATOR_PLUS());
        priorityArray.add(new TOK_VARIABLE("x"));
        
        PriorityTools.addPriority(priorityArray);
        
        assertEquals(3,priorityArray.get(0).getPriority());
        assertEquals(4,priorityArray.get(1).getPriority());
        assertEquals(5,priorityArray.get(2).getPriority());
        
        /* Testing : (4*5)+6 */
        ArrayList<ParsedToken> priorityArray1 = new ArrayList<>();
        priorityArray1.add(new TOK_PAR_OPEN());
        priorityArray1.add(new TOK_NUMBER(4));
        priorityArray1.add(new TOK_OPERATOR_MULTIPLY());
        priorityArray1.add(new TOK_NUMBER(5));
        priorityArray1.add(new TOK_PAR_CLOSE());
        priorityArray1.add(new TOK_OPERATOR_PLUS());
        priorityArray1.add(new TOK_NUMBER(6));
        
        PriorityTools.addPriority(priorityArray1);
        
        assertEquals(31,priorityArray1.get(0).getPriority());
        assertEquals(32,priorityArray1.get(1).getPriority());
        assertEquals(33,priorityArray1.get(2).getPriority());
        assertEquals(12,priorityArray1.get(3).getPriority());
        assertEquals(11,priorityArray1.get(4).getPriority());
        
        /* Testing : (2*(6+3))+2 */
        ArrayList<ParsedToken> priorityArray2 = new ArrayList<>();
        priorityArray2.add(new TOK_PAR_OPEN());
        priorityArray2.add(new TOK_NUMBER(2));
        priorityArray2.add(new TOK_OPERATOR_MULTIPLY());
        priorityArray2.add(new TOK_PAR_OPEN());
        priorityArray2.add(new TOK_NUMBER(6));
        priorityArray2.add(new TOK_OPERATOR_PLUS());
        priorityArray2.add(new TOK_NUMBER(3));
        priorityArray2.add(new TOK_PAR_CLOSE());
        priorityArray2.add(new TOK_PAR_CLOSE());
        priorityArray2.add(new TOK_OPERATOR_PLUS());
        priorityArray2.add(new TOK_NUMBER(2));
        priorityArray2.add(new TOK_NUMBER(6));
        
        PriorityTools.addPriority(priorityArray2);
                
        assertEquals(49,priorityArray2.get(0).getPriority());
        assertEquals(146,priorityArray2.get(1).getPriority());
        assertEquals(292,priorityArray2.get(2).getPriority());
        assertEquals(293,priorityArray2.get(3).getPriority());
        assertEquals(294,priorityArray2.get(4).getPriority());
        assertEquals(19,priorityArray2.get(5).getPriority());
        assertEquals(18,priorityArray2.get(6).getPriority());
        
        /* Testing x*(6+(3/4)*(1+y)) */
        ArrayList<ParsedToken> priorityArray3 = new ArrayList<>();
        priorityArray3.add(new TOK_VARIABLE("x"));
        priorityArray3.add(new TOK_OPERATOR_MULTIPLY());
        priorityArray3.add(new TOK_PAR_OPEN());
        priorityArray3.add(new TOK_NUMBER(6));
        priorityArray3.add(new TOK_OPERATOR_PLUS());
        priorityArray3.add(new TOK_PAR_OPEN());
        priorityArray3.add(new TOK_NUMBER(3));
        priorityArray3.add(new TOK_OPERATOR_DIVIDE());
        priorityArray3.add(new TOK_NUMBER(4));
        priorityArray3.add(new TOK_PAR_CLOSE());
        priorityArray3.add(new TOK_OPERATOR_MULTIPLY());
        priorityArray3.add(new TOK_PAR_OPEN());
        priorityArray3.add(new TOK_NUMBER(1));
        priorityArray3.add(new TOK_OPERATOR_PLUS());
        priorityArray3.add(new TOK_VARIABLE("y"));
        priorityArray3.add(new TOK_PAR_CLOSE());
        priorityArray3.add(new TOK_PAR_CLOSE());
                
        PriorityTools.addPriority(priorityArray3);
        
        //System.out.println(priorityArray3);
        
        /*assertEquals(11,priorityArray3.get(0).getPriority());
        assertEquals(34,priorityArray3.get(1).getPriority());
        assertEquals(69,priorityArray3.get(2).getPriority());
        assertEquals(72,priorityArray3.get(3).getPriority());
        assertEquals(404,priorityArray3.get(4).getPriority());
        assertEquals(403,priorityArray3.get(5).getPriority());
        assertEquals(402,priorityArray3.get(6).getPriority());
        assertEquals(208,priorityArray3.get(7).getPriority());
        assertEquals(408,priorityArray3.get(8).getPriority());
        assertEquals(409,priorityArray3.get(9).getPriority());
        assertEquals(410,priorityArray3.get(10).getPriority());*/
        //assertEquals(true,false); // Assert Equals above are wrong (in cause the (3/4)*(1+y) works now but a (3+4)*(1+y) cannot work)
    }

    /**
     * Test of setPriorityParenthesis method, of class PriorityTools.
     */
    @Test
    public void testSetPriorityParenthesis() {
        
    }

    /**
     * Test of removeUselessParenthesis method, of class PriorityTools.
     */
    @Test
    public void testRemoveUselessParenthesis() {
        // --- Expected good result ---
        
        /* Testing : (3+x) */
        ArrayList<ParsedToken> lexicalArray = new ArrayList<>();
        lexicalArray.add(new TOK_PAR_OPEN());
        lexicalArray.add(new TOK_NUMBER(3));
        lexicalArray.add(new TOK_OPERATOR_PLUS());
        lexicalArray.add(new TOK_VARIABLE("x"));
        lexicalArray.add(new TOK_PAR_CLOSE());
        
        ArrayList<ParsedToken> lexicalArrayCorrected = new ArrayList<>();
        lexicalArrayCorrected.add(new TOK_NUMBER(3));
        lexicalArrayCorrected.add(new TOK_OPERATOR_PLUS());
        lexicalArrayCorrected.add(new TOK_VARIABLE("x"));
        assertEquals(lexicalArrayCorrected.toString(),PriorityTools.removeUselessParenthesis(lexicalArray).toString());
        
        /* Testing : x+4*(5) */
        ArrayList<ParsedToken> lexicalArray1 = new ArrayList<>();
        lexicalArray1.add(new TOK_VARIABLE("x"));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(4));
        lexicalArray1.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray1.add(new TOK_PAR_OPEN());
        lexicalArray1.add(new TOK_NUMBER(5));
        lexicalArray1.add(new TOK_PAR_CLOSE());
        
        ArrayList<ParsedToken> lexicalArray1Corrected = new ArrayList<>();
        lexicalArray1Corrected.add(new TOK_VARIABLE("x"));
        lexicalArray1Corrected.add(new TOK_OPERATOR_PLUS());
        lexicalArray1Corrected.add(new TOK_NUMBER(4));
        lexicalArray1Corrected.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray1Corrected.add(new TOK_NUMBER(5));
        assertEquals(lexicalArray1Corrected.toString(),PriorityTools.removeUselessParenthesis(lexicalArray1).toString());
        
        /* Testing : 5+4*((x)) */
        ArrayList<ParsedToken> lexicalArray2 = new ArrayList<>();
        lexicalArray2.add(new TOK_NUMBER(5));
        lexicalArray2.add(new TOK_OPERATOR_PLUS());
        lexicalArray2.add(new TOK_NUMBER(4));
        lexicalArray2.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray2.add(new TOK_PAR_OPEN());
        lexicalArray2.add(new TOK_PAR_OPEN());
        lexicalArray2.add(new TOK_VARIABLE("x"));
        lexicalArray2.add(new TOK_PAR_CLOSE());
        lexicalArray2.add(new TOK_PAR_CLOSE());
        
        ArrayList<ParsedToken> lexicalArray2Corrected = new ArrayList<>();
        lexicalArray2Corrected.add(new TOK_NUMBER(5));
        lexicalArray2Corrected.add(new TOK_OPERATOR_PLUS());
        lexicalArray2Corrected.add(new TOK_NUMBER(4));
        lexicalArray2Corrected.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray2Corrected.add(new TOK_VARIABLE("x"));
        assertEquals(lexicalArray2Corrected.toString(),PriorityTools.removeUselessParenthesis(lexicalArray2).toString());
    }

    /**
     * Test of coefficientCalculator method, of class PriorityTools.
     */
    @Test
    public void testCoefficientCalculator() {
        // --- Expected good result ---
        
        /* Testing : cos(3+x) */
        ArrayList<ParsedToken> parenthesisArray = new ArrayList<>();
        parenthesisArray.add(new TOK_FCT_COS());
        parenthesisArray.add(new TOK_PAR_OPEN());
        parenthesisArray.add(new TOK_NUMBER(3));
        parenthesisArray.add(new TOK_OPERATOR_PLUS());
        parenthesisArray.add(new TOK_VARIABLE("x"));
        parenthesisArray.add(new TOK_PAR_CLOSE());
        assertEquals(4,PriorityTools.coefficientCalculator(parenthesisArray));
        
        /* Testing : x+3*(2+5) */
        ArrayList<ParsedToken> parenthesisArray1 = new ArrayList<>();
        parenthesisArray1.add(new TOK_VARIABLE("x"));
        parenthesisArray1.add(new TOK_OPERATOR_PLUS());
        parenthesisArray1.add(new TOK_NUMBER(3));
        parenthesisArray1.add(new TOK_OPERATOR_MULTIPLY());
        parenthesisArray1.add(new TOK_PAR_OPEN());
        parenthesisArray1.add(new TOK_NUMBER(2));
        parenthesisArray1.add(new TOK_OPERATOR_PLUS());
        parenthesisArray1.add(new TOK_NUMBER(5));
        parenthesisArray1.add(new TOK_PAR_CLOSE());
        assertEquals(7,PriorityTools.coefficientCalculator(parenthesisArray1));
        
        // --- Expected false result ---
        
        /* Testing : x+4*()5 */
        ArrayList<ParsedToken> parenthesisArray2 = new ArrayList<>();
        parenthesisArray2.add(new TOK_VARIABLE("x"));
        parenthesisArray2.add(new TOK_OPERATOR_PLUS());
        parenthesisArray2.add(new TOK_NUMBER(4));
        parenthesisArray2.add(new TOK_OPERATOR_MULTIPLY());
        parenthesisArray2.add(new TOK_PAR_OPEN());
        parenthesisArray2.add(new TOK_PAR_CLOSE());
        parenthesisArray2.add(new TOK_NUMBER(5));
        assertFalse(7==PriorityTools.coefficientCalculator(parenthesisArray2));
    }

    /**
     * Test of indexCloseParenthesis method, of class PriorityTools.
     */
    @Test
    public void testIndexCloseParenthesis() {
        //To look forward
    }

    /**
     * Test of indexOpenParenthesis method, of class PriorityTools.
     */
    @Test
    public void testIndexOpenParenthesis() {
        //To look forward
    }

    /**
     * 
     */
    @Test
    public void testReverseDividePriority(){
        /* Testing : (4/5)+6 */
        ArrayList<ParsedToken> priorityArray = new ArrayList<>();
        priorityArray.add(new TOK_PAR_OPEN());
        priorityArray.add(new TOK_NUMBER(4));
        priorityArray.add(new TOK_OPERATOR_DIVIDE());
        priorityArray.add(new TOK_NUMBER(5));
        priorityArray.add(new TOK_PAR_CLOSE());
        priorityArray.add(new TOK_OPERATOR_PLUS());
        priorityArray.add(new TOK_NUMBER(6));
        
        PriorityTools.addPriority(priorityArray);
        
        assertEquals(33,priorityArray.get(0).getPriority());
        assertEquals(32,priorityArray.get(1).getPriority());
        assertEquals(31,priorityArray.get(2).getPriority());
        assertEquals(12,priorityArray.get(3).getPriority());
        assertEquals(11,priorityArray.get(4).getPriority());
    
        /* Testing 2+x*((3+2)/4) */
        ArrayList<ParsedToken> priorityArray1 = new ArrayList<>();
        priorityArray1.add(new TOK_NUMBER(2));
        priorityArray1.add(new TOK_OPERATOR_PLUS());
        priorityArray1.add(new TOK_VARIABLE("x"));
        priorityArray1.add(new TOK_OPERATOR_MULTIPLY());
        priorityArray1.add(new TOK_PAR_OPEN());
        priorityArray1.add(new TOK_PAR_OPEN());
        priorityArray1.add(new TOK_NUMBER(3));
        priorityArray1.add(new TOK_OPERATOR_PLUS());
        priorityArray1.add(new TOK_NUMBER(2));
        priorityArray1.add(new TOK_PAR_CLOSE());
        priorityArray1.add(new TOK_OPERATOR_DIVIDE());
        priorityArray1.add(new TOK_NUMBER(4));
        priorityArray1.add(new TOK_PAR_CLOSE());
        
        PriorityTools.addPriority(priorityArray1);
        
        assertEquals(9,priorityArray1.get(0).getPriority());
        assertEquals(10,priorityArray1.get(1).getPriority());
        assertEquals(11,priorityArray1.get(2).getPriority());
        assertEquals(30,priorityArray1.get(3).getPriority());
        assertEquals(65,priorityArray1.get(4).getPriority());
        assertEquals(66,priorityArray1.get(5).getPriority());
        assertEquals(67,priorityArray1.get(6).getPriority());
        assertEquals(67,priorityArray1.get(7).getPriority());
        assertEquals(330,priorityArray1.get(8).getPriority());
    
        /* Testing 2+x*(3/(4+2)) */
        ArrayList<ParsedToken> priorityArray2 = new ArrayList<>();
        priorityArray2.add(new TOK_NUMBER(2));
        priorityArray2.add(new TOK_OPERATOR_PLUS());
        priorityArray2.add(new TOK_VARIABLE("x"));
        priorityArray2.add(new TOK_OPERATOR_MULTIPLY());
        priorityArray2.add(new TOK_PAR_OPEN());
        priorityArray2.add(new TOK_NUMBER(3));
        priorityArray2.add(new TOK_OPERATOR_DIVIDE());
        priorityArray2.add(new TOK_PAR_OPEN());
        priorityArray2.add(new TOK_NUMBER(4));
        priorityArray2.add(new TOK_OPERATOR_PLUS());
        priorityArray2.add(new TOK_NUMBER(2));
        priorityArray2.add(new TOK_PAR_CLOSE());
        priorityArray2.add(new TOK_PAR_CLOSE());
        
        PriorityTools.addPriority(priorityArray2);
        
        assertEquals(9,priorityArray2.get(0).getPriority());
        assertEquals(10,priorityArray2.get(1).getPriority());
        assertEquals(11,priorityArray2.get(2).getPriority());
        assertEquals(30,priorityArray2.get(3).getPriority());
        assertEquals(332,priorityArray2.get(4).getPriority());
        assertEquals(168,priorityArray2.get(5).getPriority());
        assertEquals(59,priorityArray2.get(6).getPriority());
        assertEquals(60,priorityArray2.get(7).getPriority());
        assertEquals(61,priorityArray2.get(8).getPriority());
    }
    
    /**
     * Test of schemaXOperatorY method, of class PriorityTools.
     */
    @Test
    public void testSchemaXOperatorY() {
        // --- Expected already correct priorityArray ---
        
        /* Testing : cos(3+x) */
        ArrayList<ParsedToken> priorityArray = new ArrayList<>();
        priorityArray.add(new TOK_FCT_COS());
        priorityArray.add(new TOK_PAR_OPEN());
        priorityArray.add(new TOK_NUMBER(3));
        priorityArray.add(new TOK_OPERATOR_PLUS());
        priorityArray.add(new TOK_VARIABLE("x"));
        priorityArray.add(new TOK_PAR_CLOSE());
        assertEquals(true,PriorityTools.schemaXOperatorY(priorityArray, 3));
        
        /* Testing : x+3*(2+5) */
        ArrayList<ParsedToken> priorityArray1 = new ArrayList<>();
        priorityArray1.add(new TOK_VARIABLE("x"));
        priorityArray1.add(new TOK_OPERATOR_PLUS());
        priorityArray1.add(new TOK_NUMBER(3));
        priorityArray1.add(new TOK_OPERATOR_MULTIPLY());
        priorityArray1.add(new TOK_PAR_OPEN());
        priorityArray1.add(new TOK_NUMBER(2));
        priorityArray1.add(new TOK_OPERATOR_PLUS());
        priorityArray1.add(new TOK_NUMBER(5));
        priorityArray1.add(new TOK_PAR_CLOSE());
        assertEquals(true,PriorityTools.schemaXOperatorY(priorityArray1, 1));
        assertEquals(true,PriorityTools.schemaXOperatorY(priorityArray1, 6));
        
        // --- Expected false -> x*( ---
        
        assertEquals(false,PriorityTools.schemaXOperatorY(priorityArray1, 3));
    }
    
}
