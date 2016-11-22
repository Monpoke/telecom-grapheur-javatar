/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import static graphtest.lexem.Lexem.pattern;
import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_PAR_OPEN;
import java.util.regex.Pattern;

/**
 *
 * @author A643012
 */
public class Matcher_PAR_OPEN extends Lexem implements Rule {

    public final static String NAME = "PAR_OPEN";

    public Matcher_PAR_OPEN() {
    }

    @Override
    public ParsedToken match(String sentence) {
        movePointerFromX = 1;
        lastMatch = "(";
        
        
        if(sentence.charAt(0) == '('){
            return new TOK_PAR_OPEN();
        }
        
        return null;
    }

    public String getName() {
        return NAME;
    }

}
