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
import graphtest.parsed.TOK_OPERATOR_MINUS;
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
public class SyntaxToolsTest {
    
    public SyntaxToolsTest() {
    }

    /**
     * Test of lexicalIntoSyntax method, of class SyntaxTools.
     */
    @Test
    public void testLexicalIntoSyntax() {
        System.out.println("lexicalIntoSyntax");
        ArrayList<ParsedToken> lexicalArray = null;
        SyntaxTools instance = new SyntaxTools();
        Stack expResult = null;
        Stack result = instance.lexicalIntoSyntax(lexicalArray);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of verifySyntax method, of class SyntaxTools.
     */
    @Test
    public void testVerifySyntax() {
        System.out.println("verifySyntax");
        ArrayList<ParsedToken> lexicalArray = null;
        SyntaxTools instance = new SyntaxTools();
        boolean expResult = false;
        boolean result = instance.verifySyntax(lexicalArray);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addHideMultiplyToken method, of class SyntaxTools.
     */
    @Test
    public void testAddHideMultiplyToken() {
        System.out.println("addHideMultiplyToken");
        
        // --- Expected already correct lexicalArray ---
        
        /* Testing : cos(3+x) */
        ArrayList<ParsedToken> lexicalArray = new ArrayList<>();
        lexicalArray.add(new TOK_FCT_COS());
        lexicalArray.add(new TOK_PAR_OPEN());
        lexicalArray.add(new TOK_NUMBER(3));
        lexicalArray.add(new TOK_OPERATOR_PLUS());
        lexicalArray.add(new TOK_VARIABLE("x"));
        lexicalArray.add(new TOK_PAR_CLOSE());
        assertEquals(lexicalArray,SyntaxTools.addHideMultiplyToken(lexicalArray));
        
        /* Testing : x+3*(2+5) */
        ArrayList<ParsedToken> lexicalArray1 = new ArrayList<>();
        lexicalArray1.add(new TOK_VARIABLE("x"));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(3));
        lexicalArray1.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray1.add(new TOK_PAR_OPEN());
        lexicalArray1.add(new TOK_NUMBER(2));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(5));
        lexicalArray1.add(new TOK_PAR_CLOSE());
        assertEquals(lexicalArray, SyntaxTools.addHideMultiplyToken(lexicalArray1));
        
        // --- Expected changes ---
        
        /* Testing : 4(5+6) */
        ArrayList<ParsedToken> lexicalArray2 = new ArrayList<>();
        lexicalArray2.add(new TOK_NUMBER(4));
        lexicalArray2.add(new TOK_PAR_OPEN());
        lexicalArray2.add(new TOK_NUMBER(5));
        lexicalArray2.add(new TOK_OPERATOR_PLUS());
        lexicalArray2.add(new TOK_NUMBER(6));
        lexicalArray2.add(new TOK_PAR_CLOSE());
        
        ArrayList<ParsedToken> lexicalArray2Corrected = new ArrayList<>();
        lexicalArray2Corrected.add(new TOK_NUMBER(4));
        lexicalArray2Corrected.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray2Corrected.add(new TOK_PAR_OPEN());
        lexicalArray2Corrected.add(new TOK_NUMBER(5));
        lexicalArray2Corrected.add(new TOK_OPERATOR_PLUS());
        lexicalArray2Corrected.add(new TOK_NUMBER(6));
        lexicalArray2Corrected.add(new TOK_PAR_CLOSE());
        assertEquals(lexicalArray2Corrected,SyntaxTools.addHideMultiplyToken(lexicalArray2));
        
        /* Testing : 7+8(4+2x) */
        ArrayList<ParsedToken> lexicalArray3 = new ArrayList<>();
        lexicalArray3.add(new TOK_NUMBER(7));
        lexicalArray3.add(new TOK_OPERATOR_PLUS());
        lexicalArray3.add(new TOK_NUMBER(8));
        lexicalArray3.add(new TOK_PAR_OPEN());
        lexicalArray3.add(new TOK_NUMBER(4));
        lexicalArray3.add(new TOK_OPERATOR_PLUS());
        lexicalArray3.add(new TOK_NUMBER(2));
        lexicalArray3.add(new TOK_VARIABLE("x"));
        lexicalArray3.add(new TOK_PAR_CLOSE());
        
        ArrayList<ParsedToken> lexicalArray3Corrected = new ArrayList<>();
        lexicalArray3Corrected.add(new TOK_NUMBER(7));
        lexicalArray3Corrected.add(new TOK_OPERATOR_PLUS());
        lexicalArray3Corrected.add(new TOK_NUMBER(8));
        lexicalArray3Corrected.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray3Corrected.add(new TOK_PAR_OPEN());
        lexicalArray3Corrected.add(new TOK_NUMBER(4));
        lexicalArray3Corrected.add(new TOK_OPERATOR_PLUS());
        lexicalArray3Corrected.add(new TOK_NUMBER(2));
        lexicalArray3Corrected.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray3Corrected.add(new TOK_VARIABLE("x"));
        lexicalArray3Corrected.add(new TOK_PAR_CLOSE());
        assertEquals(lexicalArray3Corrected,SyntaxTools.addHideMultiplyToken(lexicalArray3));
        
    }

    /**
     * Test of existedHideMultiply method, of class SyntaxTools.
     */
    @Test
    public void testExistedHideMultiply() {
        System.out.println("existedHideMultiply");
        
        // --- Expected = 0 tests ---
        
        /* Testing : cos(3+x) */
        ArrayList<ParsedToken> lexicalArray = new ArrayList<>();
        lexicalArray.add(new TOK_FCT_COS());
        lexicalArray.add(new TOK_PAR_OPEN());
        lexicalArray.add(new TOK_NUMBER(3));
        lexicalArray.add(new TOK_OPERATOR_PLUS());
        lexicalArray.add(new TOK_VARIABLE("x"));
        lexicalArray.add(new TOK_PAR_CLOSE());
        assertEquals(0,SyntaxTools.existedHideMultiply(lexicalArray));
        
        /* Testing : x+3*(2+5) */
        ArrayList<ParsedToken> lexicalArray1 = new ArrayList<>();
        lexicalArray1.add(new TOK_VARIABLE("x"));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(3));
        lexicalArray1.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray1.add(new TOK_PAR_OPEN());
        lexicalArray1.add(new TOK_NUMBER(2));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(5));
        lexicalArray1.add(new TOK_PAR_CLOSE());
        assertEquals(0, SyntaxTools.existedHideMultiply(lexicalArray1));
        
        /* --- Expected existing at least 1 hide Multiply ---
        
        /* Testing : 4(5+6) */
        ArrayList<ParsedToken> lexicalArray2 = new ArrayList<>();
        lexicalArray2.add(new TOK_NUMBER(4));
        lexicalArray2.add(new TOK_PAR_OPEN());
        lexicalArray2.add(new TOK_NUMBER(5));
        lexicalArray2.add(new TOK_OPERATOR_PLUS());
        lexicalArray2.add(new TOK_NUMBER(6));
        lexicalArray2.add(new TOK_PAR_CLOSE());
        assertEquals(1,SyntaxTools.existedHideMultiply(lexicalArray2));
        
        /* Testing : 7+8(4+2x) */
        ArrayList<ParsedToken> lexicalArray3 = new ArrayList<>();
        lexicalArray3.add(new TOK_NUMBER(7));
        lexicalArray3.add(new TOK_OPERATOR_PLUS());
        lexicalArray3.add(new TOK_NUMBER(8));
        lexicalArray3.add(new TOK_PAR_OPEN());
        lexicalArray3.add(new TOK_NUMBER(4));
        lexicalArray3.add(new TOK_OPERATOR_PLUS());
        lexicalArray3.add(new TOK_NUMBER(2));
        lexicalArray3.add(new TOK_VARIABLE("x"));
        lexicalArray3.add(new TOK_PAR_CLOSE());
        assertEquals(2,SyntaxTools.existedHideMultiply(lexicalArray3));
    }

    /**
     * Test of verifyNumberInRow method, of class SyntaxTools.
     */
    @Test
    public void testVerifyNumberInRow() {
        System.out.println("verifyNumberInRow");
        
        // --- Expected true tests ---
        
        /* Testing : cos(3+x) */
        ArrayList<ParsedToken> lexicalArray = new ArrayList<>();
        lexicalArray.add(new TOK_FCT_COS());
        lexicalArray.add(new TOK_PAR_OPEN());
        lexicalArray.add(new TOK_NUMBER(3));
        lexicalArray.add(new TOK_OPERATOR_PLUS());
        lexicalArray.add(new TOK_VARIABLE("x"));
        lexicalArray.add(new TOK_PAR_CLOSE());
        assertEquals(true,SyntaxTools.verifyNumberInRow(lexicalArray));
        
        /* Testing : x+3*(2+5) */
        ArrayList<ParsedToken> lexicalArray1 = new ArrayList<>();
        lexicalArray1.add(new TOK_VARIABLE("x"));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(3));
        lexicalArray1.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray1.add(new TOK_PAR_OPEN());
        lexicalArray1.add(new TOK_NUMBER(2));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(5));
        lexicalArray1.add(new TOK_PAR_CLOSE());
        assertEquals(true, SyntaxTools.verifyNumberInRow(lexicalArray1));
        
        // --- Testing Token in row, expect false
        
        /* Testing : 3+xy */
        ArrayList<ParsedToken> lexicalArray2 = new ArrayList<>();
        lexicalArray2.add(new TOK_NUMBER(3));
        lexicalArray2.add(new TOK_OPERATOR_PLUS());
        lexicalArray2.add(new TOK_VARIABLE("x"));
        lexicalArray2.add(new TOK_VARIABLE("y"));
        assertEquals(false,SyntaxTools.verifyNumberInRow(lexicalArray2));
        
        /* Testing : 4,4 + 5 6*/
        ArrayList<ParsedToken> lexicalArray3 = new ArrayList<>();
        lexicalArray3.add(new TOK_NUMBER(4.4));
        lexicalArray3.add(new TOK_OPERATOR_PLUS());
        lexicalArray3.add(new TOK_NUMBER(5));
        lexicalArray3.add(new TOK_NUMBER(6));
        assertEquals(false,SyntaxTools.verifyNumberInRow(lexicalArray3));
    }   

    /**
     * Test of verifyFacingParenthesis method, of class SyntaxTools.
     */
    @Test
    public void testVerifyFacingParenthesis() {
        System.out.println("verifyFacingParenthesis");
        
        // --- Expected true tests ---
        
        /* Testing : cos(3+x) */
        ArrayList<ParsedToken> lexicalArray = new ArrayList<>();
        lexicalArray.add(new TOK_FCT_COS());
        lexicalArray.add(new TOK_PAR_OPEN());
        lexicalArray.add(new TOK_NUMBER(3));
        lexicalArray.add(new TOK_OPERATOR_PLUS());
        lexicalArray.add(new TOK_VARIABLE("x"));
        lexicalArray.add(new TOK_PAR_CLOSE());
        assertEquals(true,SyntaxTools.verifyFacingParenthesis(lexicalArray));
        
        /* Testing : x+3*(2+5) */
        ArrayList<ParsedToken> lexicalArray1 = new ArrayList<>();
        lexicalArray1.add(new TOK_VARIABLE("x"));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(3));
        lexicalArray1.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray1.add(new TOK_PAR_OPEN());
        lexicalArray1.add(new TOK_NUMBER(2));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(5));
        lexicalArray1.add(new TOK_PAR_CLOSE());
        assertEquals(true, SyntaxTools.verifyFacingParenthesis(lexicalArray1));
        
        // --- Testing facing parenthesis, exepect false ---
        
        /* Testing : x+4*()5 */
        ArrayList<ParsedToken> lexicalArray2 = new ArrayList<>();
        lexicalArray2.add(new TOK_VARIABLE("x"));
        lexicalArray2.add(new TOK_OPERATOR_PLUS());
        lexicalArray2.add(new TOK_NUMBER(4));
        lexicalArray2.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray2.add(new TOK_PAR_OPEN());
        lexicalArray2.add(new TOK_PAR_CLOSE());
        lexicalArray2.add(new TOK_NUMBER(5));
        assertEquals(false,SyntaxTools.verifyFacingParenthesis(lexicalArray2));
        
        /* Testing : x+(6*(7+8())) */
        ArrayList<ParsedToken> lexicalArray3 = new ArrayList<>();
        lexicalArray3.add(new TOK_VARIABLE("x"));
        lexicalArray3.add(new TOK_OPERATOR_PLUS());
        lexicalArray3.add(new TOK_PAR_OPEN());
        lexicalArray3.add(new TOK_NUMBER(6));
        lexicalArray3.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray3.add(new TOK_PAR_OPEN());
        lexicalArray3.add(new TOK_NUMBER(7));
        lexicalArray3.add(new TOK_OPERATOR_PLUS());
        lexicalArray3.add(new TOK_NUMBER(8));
        lexicalArray3.add(new TOK_PAR_OPEN());
        lexicalArray3.add(new TOK_PAR_CLOSE());
        lexicalArray3.add(new TOK_PAR_CLOSE());
        lexicalArray3.add(new TOK_PAR_CLOSE());
        assertEquals(false,SyntaxTools.verifyFacingParenthesis(lexicalArray3));
    }

    /**
     * Test of verifyOperatorInRow method, of class SyntaxTools.
     */
    @Test
    public void testVerifyOperatorInRow() {
        System.out.println("verifyOperatorInRow");
        // --- Expected true tests ---
        
        /* Testing : cos(3+x) */
        ArrayList<ParsedToken> lexicalArray = new ArrayList<>();
        lexicalArray.add(new TOK_FCT_COS());
        lexicalArray.add(new TOK_PAR_OPEN());
        lexicalArray.add(new TOK_NUMBER(3));
        lexicalArray.add(new TOK_OPERATOR_PLUS());
        lexicalArray.add(new TOK_VARIABLE("x"));
        lexicalArray.add(new TOK_PAR_CLOSE());
        assertEquals(true,SyntaxTools.verifyOperatorInRow(lexicalArray));
        
        /* Testing : x+3*(2+5) */
        ArrayList<ParsedToken> lexicalArray1 = new ArrayList<>();
        lexicalArray1.add(new TOK_VARIABLE("x"));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(3));
        lexicalArray1.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray1.add(new TOK_PAR_OPEN());
        lexicalArray1.add(new TOK_NUMBER(2));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(5));
        lexicalArray1.add(new TOK_PAR_CLOSE());
        assertEquals(true, SyntaxTools.verifyOperatorInRow(lexicalArray1));
        
        // --- Testing double same operator in row ---
        
        /* Testing : 3++4 */
        ArrayList<ParsedToken> lexicalArray2 = new ArrayList<>();
        lexicalArray2.add(new TOK_NUMBER(3));
        lexicalArray2.add(new TOK_OPERATOR_PLUS());
        lexicalArray2.add(new TOK_OPERATOR_PLUS());
        lexicalArray2.add(new TOK_NUMBER(4));
        assertEquals(false, SyntaxTools.verifyOperatorInRow(lexicalArray2));
        
        /* Testing : 5//6 */
        ArrayList<ParsedToken> lexicalArray3 = new ArrayList<>();
        lexicalArray3.add(new TOK_NUMBER(5));
        lexicalArray3.add(new TOK_OPERATOR_DIVIDE());
        lexicalArray3.add(new TOK_OPERATOR_DIVIDE());
        lexicalArray3.add(new TOK_NUMBER(6));
        assertEquals(false, SyntaxTools.verifyOperatorInRow(lexicalArray3));
        
        // ---Testing double different operator in row---
        
        /* Testing : 7*-x */
        ArrayList<ParsedToken> lexicalArray4 = new ArrayList<>();
        lexicalArray2.add(new TOK_NUMBER(7));
        lexicalArray2.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray2.add(new TOK_OPERATOR_MINUS());
        lexicalArray2.add(new TOK_VARIABLE("x"));
        assertEquals(false, SyntaxTools.verifyOperatorInRow(lexicalArray4));
        
        /* Testing : 8*x+/9 */
        ArrayList<ParsedToken> lexicalArray5 = new ArrayList<>();
        lexicalArray5.add(new TOK_NUMBER(8));
        lexicalArray5.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray5.add(new TOK_VARIABLE("x"));
        lexicalArray5.add(new TOK_OPERATOR_PLUS());
        lexicalArray5.add(new TOK_OPERATOR_DIVIDE());
        lexicalArray5.add(new TOK_NUMBER(9));
        assertEquals(false, SyntaxTools.verifyOperatorInRow(lexicalArray5));
    }

    /**
     * Test of verifyTokenStartAndEnd method, of class SyntaxTools.
     */
    @Test
    public void testVerifyTokenStartAndEnd() {
        System.out.println("verifyTokenStartAndEnd");
        // --- Expected true tests ---
        
        /* Testing : cos(3+x) */
        ArrayList<ParsedToken> lexicalArray = new ArrayList<>();
        lexicalArray.add(new TOK_FCT_COS());
        lexicalArray.add(new TOK_PAR_OPEN());
        lexicalArray.add(new TOK_NUMBER(3));
        lexicalArray.add(new TOK_OPERATOR_PLUS());
        lexicalArray.add(new TOK_VARIABLE("x"));
        lexicalArray.add(new TOK_PAR_CLOSE());
        assertEquals(true,SyntaxTools.verifyTokenStartAndEnd(lexicalArray));
        
        /* Testing : x+3*(2+5) */
        ArrayList<ParsedToken> lexicalArray1 = new ArrayList<>();
        lexicalArray1.add(new TOK_VARIABLE("x"));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(3));
        lexicalArray1.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray1.add(new TOK_PAR_OPEN());
        lexicalArray1.add(new TOK_NUMBER(2));
        lexicalArray1.add(new TOK_OPERATOR_PLUS());
        lexicalArray1.add(new TOK_NUMBER(5));
        lexicalArray1.add(new TOK_PAR_CLOSE());
        assertEquals(true, SyntaxTools.verifyTokenStartAndEnd(lexicalArray1));
        // --- Testing begining --
        
        /* Teststing : )4+x( */
        ArrayList<ParsedToken> lexicalArray2 = new ArrayList<>();
        lexicalArray2.add(new TOK_PAR_CLOSE());
        lexicalArray2.add(new TOK_NUMBER(4));
        lexicalArray2.add(new TOK_OPERATOR_PLUS());
        lexicalArray2.add(new TOK_VARIABLE("x"));
        lexicalArray2.add(new TOK_PAR_OPEN());
        assertEquals(false,SyntaxTools.verifyTokenStartAndEnd(lexicalArray2));
        
        /* Testing : *(5-x) */
        ArrayList<ParsedToken> lexicalArray3 = new ArrayList<>();
        lexicalArray3.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray3.add(new TOK_PAR_OPEN());
        lexicalArray3.add(new TOK_NUMBER(5));
        lexicalArray3.add(new TOK_OPERATOR_MINUS());
        lexicalArray3.add(new TOK_VARIABLE("x"));
        lexicalArray3.add(new TOK_PAR_CLOSE());
        assertEquals(false, SyntaxTools.verifyTokenStartAndEnd(lexicalArray3));
        
        // --- Testing end ---
        
        /* Testing : (6+x)- */
        ArrayList<ParsedToken> lexicalArray4 = new ArrayList<>();
        lexicalArray4.add(new TOK_PAR_OPEN());
        lexicalArray4.add(new TOK_NUMBER(6));
        lexicalArray4.add(new TOK_OPERATOR_PLUS());
        lexicalArray4.add(new TOK_VARIABLE("x"));
        lexicalArray4.add(new TOK_PAR_CLOSE());
        lexicalArray4.add(new TOK_OPERATOR_MINUS());
        assertEquals(false, SyntaxTools.verifyTokenStartAndEnd(lexicalArray4));
        
        /* Testing : 7xcos */
        ArrayList<ParsedToken> lexicalArray5 = new ArrayList<>();
        lexicalArray5.add(new TOK_NUMBER(7));
        lexicalArray5.add(new TOK_OPERATOR_MULTIPLY());
        lexicalArray5.add(new TOK_FCT_COS());
        assertEquals(false, SyntaxTools.verifyTokenStartAndEnd(lexicalArray5));
        
        /* Testing : 8+9/( */
        ArrayList<ParsedToken> lexicalArray6 = new ArrayList<>();
        lexicalArray6.add(new TOK_NUMBER(8));
        lexicalArray6.add(new TOK_OPERATOR_PLUS());
        lexicalArray6.add(new TOK_NUMBER(9));
        lexicalArray6.add(new TOK_OPERATOR_DIVIDE());
        lexicalArray6.add(new TOK_PAR_CLOSE());
        assertEquals(false, SyntaxTools.verifyTokenStartAndEnd(lexicalArray6));
        
    }
    
}
