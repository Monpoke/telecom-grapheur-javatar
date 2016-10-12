/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import java.util.regex.Pattern;

/**
 *
 * @author A643012
 */
abstract class Lexem {

    public static String NAME = null;

    protected static Pattern pattern = null;

    protected int movePointerFromX = 0;

    protected String lastMatch = "";

    protected boolean isInit() {
        return pattern != null;
    }

    public void init() {
    }

    public void reset(){
        lastMatch="";
        movePointerFromX=1;
    }
    

    public int movePointerFrom() {
        return movePointerFromX;
    }
    
    public String getLastMatch(){
        return lastMatch;
    } 
}
