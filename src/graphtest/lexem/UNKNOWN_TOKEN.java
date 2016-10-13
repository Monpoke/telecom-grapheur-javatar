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
public class UNKNOWN_TOKEN extends Lexem implements Rule {

    public final static String NAME = "UNKNOWN_TOKEN";

    public UNKNOWN_TOKEN() {
    }

    @Override
    public boolean match(String sentence) {
        movePointerFromX = 1;
        lastMatch = "";
        return false;
    }

    public String getName() {
        return NAME;
    }

}
