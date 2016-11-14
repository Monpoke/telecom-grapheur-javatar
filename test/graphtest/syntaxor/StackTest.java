/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.syntaxor;

import graphtest.parsed.TOK_FCT_COS;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_PAR_CLOSE;
import graphtest.parsed.TOK_PAR_OPEN;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author A642995
 */
public class StackTest {
    
    public StackTest() {
    }

    /**
     * Test of empty method, of class Stack.
     */
    @Test
    public void testEmpty() {
        Stack st = new Stack(2);
        assertEquals(true, st.empty());

        st.push(new TOK_NUMBER(1));
        assertEquals(false,st.empty());

        st.push(new TOK_FCT_COS());
        assertEquals(false, st.empty());
        
        st.pop();
        assertEquals(false, st.empty());
        
        st.pop();
        assertEquals(true, st.empty());
    }

    /**
     * Test of pop method, of class Stack.
     */
    @Test
    public void testPop() {
        Stack st = new Stack(2);
        assertNull(st.pop());
        
        TOK_PAR_CLOSE tpc = new TOK_PAR_CLOSE();
        st.push(tpc);
        assertEquals(tpc, st.pop());
        assertEquals(true,st.empty());
        
        TOK_PAR_OPEN tpo = new TOK_PAR_OPEN();
        st.push(tpc);
        st.push(tpo);
        assertEquals(tpo,st.pop());
        assertEquals(false,st.empty());
        
    }

    /**
     * Test of push method, of class Stack.
     */
    @Test
    public void testPush() {
        Stack st = new Stack(2);
        assertNull(st.peek());
        
        TOK_PAR_CLOSE tpc = new TOK_PAR_CLOSE();
        st.push(tpc);
        assertEquals(tpc, st.peek());
        assertEquals(false,st.empty());
        st.pop();
        
        TOK_PAR_OPEN tpo = new TOK_PAR_OPEN();
        st.push(tpc);
        st.push(tpo);
        assertEquals(tpo,st.peek());
        assertEquals(false,st.empty());
        assertEquals(true,st.isFull());
    }

    /**
     * Test of peek method, of class Stack.
     */
    @Test
    public void testPeek() {
        Stack st = new Stack(2);
        assertNull(st.peek());
        
        TOK_PAR_CLOSE tpc = new TOK_PAR_CLOSE();
        st.push(tpc);
        assertEquals(tpc, st.peek());
        assertEquals(false,st.empty());
        st.pop();
        
        TOK_PAR_OPEN tpo = new TOK_PAR_OPEN();
        st.push(tpc);
        st.push(tpo);
        assertEquals(tpo,st.peek());
        assertEquals(false,st.empty());
        st.pop();
        assertEquals(tpc,st.peek());
    }

    /**
     * Test of isFull method, of class Stack.
     */
    @Test
    public void testIsFull() {
        Stack st = new Stack(2);
        assertEquals(false,st.isFull());
        
        st.push(new TOK_PAR_CLOSE());
        assertEquals(false,st.isFull());
        
        st.push(new TOK_PAR_OPEN());
        assertEquals(true,st.isFull());
        
        st.pop();
        assertEquals(false,st.isFull());
        
        st.push(new TOK_FCT_COS());
        assertEquals(true,st.isFull());
        
    }
}
