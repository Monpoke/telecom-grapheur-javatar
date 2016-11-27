/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import static graphtest.lexem.Lexem.pattern;
import graphtest.parsed.ParsedToken;
import java.util.regex.Pattern;

/**
 *
 * @author A643012
 */
public class Matcher_OPERATOR_PLUS extends Lexem implements Rule {

    public final static String NAME = "OPERATOR_PLUS";

    public Matcher_OPERATOR_PLUS() {
    }

    @Override
    public ParsedToken match(String sentence) {
        movePointerFromX = 1;
        lastMatch = "+";
        
        
        if(sentence.charAt(0) == '+'){
            return new graphtest.parsed.TOK_OPERATOR_PLUS();
        }
        
        return null;
    }

    public String getName() {
        return NAME;
    }

}
