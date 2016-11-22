/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_OPERATOR_MULTIPLY;

/**
 *
 * @author A643012
 */
public class Matcher_OPERATOR_MULTIPLY extends Lexem implements Rule {

    public final static String NAME = "OPERATOR_MULTIPLY";

    public Matcher_OPERATOR_MULTIPLY() {
    }

    @Override
    public ParsedToken match(String sentence) {
        movePointerFromX = 1;
        lastMatch = "*";
        if(sentence.charAt(0) == '*'){
            return new TOK_OPERATOR_MULTIPLY();
        }
        return null;
    }

    public String getName() {
        return NAME;
    }

}
