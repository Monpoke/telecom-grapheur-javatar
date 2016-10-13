/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

/**
 *
 * @author A643012
 */
public class OPERATOR_DIVIDE extends Lexem implements Rule {

    public final static String NAME = "OPERATOR_DIVIDE";

    public OPERATOR_DIVIDE() {
    }

    @Override
    public boolean match(String sentence) {
        movePointerFromX = 1;
        lastMatch = "/";
        return sentence.charAt(0) == '/';
    }

    public String getName() {
        return NAME;
    }

}
