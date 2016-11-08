/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.syntaxor;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_FCT_COS;
import graphtest.parsed.TOK_NUMBER;
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
 * @author A642995
 */
public class PriorityToolsTest {
    
    public PriorityToolsTest() {
    }

    /**
     * Test of addPriority method, of class PriorityTools.
     */
    @Test
    public void testAddPriority() {
        //TODO
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
        assertNotEquals(7,PriorityTools.coefficientCalculator(parenthesisArray2));
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
        //To loof forward
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
