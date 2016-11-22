/*   
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_FCT_COS;
import graphtest.parsed.TOK_FCT_SIN;
import graphtest.parsed.TOK_VARIABLE;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author A643012
 */
public class Matcher_CONSTANT extends Lexem implements Rule {

    public final static String NAME = "CONSTANT";
    
    public Matcher_CONSTANT() {
    }

    @Override
    public ParsedToken match(String sentence) {
        pattern = Pattern.compile("^(pi)");
        Matcher matcher = pattern.matcher(sentence);

        boolean r = matcher.find();

        if (r) {
            lastMatch = (matcher.group(0));
            movePointerFromX = lastMatch.length();

            return new TOK_VARIABLE(lastMatch);
        }

        return null;

    }

    public String getName() {
        return NAME;
    }
}
