/*   
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author A643012
 */
public class FCT_SIN extends Lexem implements Rule {

    public final static String NAME = "FCT_SIN";

    public FCT_SIN() {
    }

    @Override
    public boolean match(String sentence) {
        pattern = Pattern.compile("^(sin)");
        Matcher matcher = pattern.matcher(sentence);

        boolean r = matcher.find();

        if (r) {
            lastMatch = (matcher.group(0));
            movePointerFromX = lastMatch.length();

            return true;
        }

        return false;

    }

    public String getName() {
        return NAME;
    }
}
