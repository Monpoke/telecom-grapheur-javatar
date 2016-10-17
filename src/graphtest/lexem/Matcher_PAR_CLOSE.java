/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_PAR_CLOSE;
import graphtest.parsed.TOK_PAR_OPEN;

/**
 *
 * @author A643012
 */
public class Matcher_PAR_CLOSE extends Lexem implements Rule {

    public final static String NAME = "PAR_OPEN";

    public Matcher_PAR_CLOSE() {
    }

    @Override
    public ParsedToken match(String sentence) {
        movePointerFromX = 1;
        lastMatch = "(";
        
        
        if(sentence.charAt(0) == ')'){
            return new TOK_PAR_CLOSE();
        }
        
        return null;
    }

    public String getName() {
        return NAME;
    }

}
