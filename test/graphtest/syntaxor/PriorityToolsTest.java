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
        
        assertEquals(182,priorityArray1.get(0).getPriority());
        assertEquals(183,priorityArray1.get(1).getPriority());
        assertEquals(184,priorityArray1.get(2).getPriority());
        assertEquals(14,priorityArray1.get(3).getPriority());
        assertEquals(13,priorityArray1.get(4).getPriority());
        
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
                
        assertEquals(290,priorityArray2.get(0).getPriority());
        assertEquals(1731,priorityArray2.get(1).getPriority());
        assertEquals(1733,priorityArray2.get(2).getPriority());
        assertEquals(1734,priorityArray2.get(3).getPriority());
        assertEquals(1735,priorityArray2.get(4).getPriority());
        assertEquals(21,priorityArray2.get(5).getPriority());
        assertEquals(20,priorityArray2.get(6).getPriority());
        
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
        
        assertEquals(67,priorityArray3.get(0).getPriority());
        assertEquals(398,priorityArray3.get(1).getPriority());
        assertEquals(2381,priorityArray3.get(2).getPriority());
        assertEquals(14262,priorityArray3.get(3).getPriority());
        assertEquals(85547,priorityArray3.get(4).getPriority());
        assertEquals(85546,priorityArray3.get(5).getPriority());
        assertEquals(85545,priorityArray3.get(6).getPriority());
        assertEquals(14272,priorityArray3.get(7).getPriority());
        assertEquals(14274,priorityArray3.get(8).getPriority());
        assertEquals(14275,priorityArray3.get(9).getPriority());
        assertEquals(14276,priorityArray3.get(10).getPriority());
        assertEquals(true,false); // Assert Equals above are wrong (in cause the (3/4)*(1+y) works now but a (3+4)*(1+y) cannot work)
        
        /* Testing cos(2*x)+2 */
        ArrayList<ParsedToken> priorityArray4 = new ArrayList<>();
        priorityArray4.add(new TOK_FCT_COS());
        priorityArray4.add(new TOK_PAR_OPEN());
        priorityArray4.add(new TOK_NUMBER(2));
        priorityArray4.add(new TOK_OPERATOR_MULTIPLY());
        priorityArray4.add(new TOK_VARIABLE("x"));
        priorityArray4.add(new TOK_PAR_CLOSE());
        priorityArray4.add(new TOK_OPERATOR_PLUS());
        priorityArray4.add(new TOK_NUMBER(2));
        
        PriorityTools.addPriority(priorityArray4);
        
        assertEquals(30, priorityArray4.get(0).getPriority());
        assertEquals(219, priorityArray4.get(1).getPriority());
        assertEquals(220, priorityArray4.get(2).getPriority());
        assertEquals(221, priorityArray4.get(3).getPriority());
        assertEquals(16, priorityArray4.get(4).getPriority());
        assertEquals(15, priorityArray4.get(5).getPriority());
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
        
        assertEquals(184,priorityArray.get(0).getPriority());
        assertEquals(183,priorityArray.get(1).getPriority());
        assertEquals(182,priorityArray.get(2).getPriority());
        assertEquals(14,priorityArray.get(3).getPriority());
        assertEquals(13,priorityArray.get(4).getPriority());
    
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
        assertEquals(55,priorityArray1.get(1).getPriority());
        assertEquals(57,priorityArray1.get(2).getPriority());
        assertEquals(328,priorityArray1.get(3).getPriority());
        assertEquals(1951,priorityArray1.get(4).getPriority());
        assertEquals(1952,priorityArray1.get(5).getPriority());
        assertEquals(1953,priorityArray1.get(6).getPriority());
        assertEquals(338,priorityArray1.get(7).getPriority());
        assertEquals(336,priorityArray1.get(8).getPriority());
    
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
        assertEquals(55,priorityArray2.get(1).getPriority());
        assertEquals(57,priorityArray2.get(2).getPriority());
        assertEquals(328,priorityArray2.get(3).getPriority());
        assertEquals(11674,priorityArray2.get(4).getPriority());
        assertEquals(11672,priorityArray2.get(5).getPriority());
        assertEquals(1951,priorityArray2.get(6).getPriority());
        assertEquals(1952,priorityArray2.get(7).getPriority());
        assertEquals(1953,priorityArray2.get(8).getPriority());
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
