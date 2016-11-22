/*   
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_FCT_COS;
import graphtest.parsed.TOK_FCT_SIN;
import graphtest.parsed.TOK_FCT_TAN;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author A643012
 */
public class Matcher_FCT_TAN extends Lexem implements Rule {

    public final static String NAME = "FCT_TAN";

    public Matcher_FCT_TAN() {
    }

    @Override
    public ParsedToken match(String sentence) {
        pattern = Pattern.compile("^(tan)");
        Matcher matcher = pattern.matcher(sentence);

        boolean r = matcher.find();

        if (r) {
            lastMatch = (matcher.group(0));
            movePointerFromX = lastMatch.length();

            return new TOK_FCT_TAN();
        }

        return null;

    }

    public String getName() {
        return NAME;
    }
}
