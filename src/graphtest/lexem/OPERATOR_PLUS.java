/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import static graphtest.lexem.Lexem.pattern;
import java.util.regex.Pattern;

/**
 *
 * @author A643012
 */
public class OPERATOR_PLUS extends Lexem implements Rule {

    public final static String NAME = "OPERATOR_PLUS";

    public OPERATOR_PLUS() {
        /*if(isInit()) return;
        //pattern = Pattern.compile("^\\+");
         */

        // NO MATCH, SINGLE CHAR
    }

    @Override
    public boolean match(String sentence) {
        movePointerFromX = 1;
        lastMatch = "+";
        return sentence.charAt(0) == '+';
    }

    public String getName() {
        return NAME;
    }

}
