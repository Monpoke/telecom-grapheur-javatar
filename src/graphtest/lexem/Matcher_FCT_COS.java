/*   
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_FCT_COS;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author A643012
 */
public class Matcher_FCT_COS extends Lexem implements Rule {

    public final static String NAME = "FCT_COS";

    public Matcher_FCT_COS() {
    }

    @Override
    public ParsedToken match(String sentence) {
        pattern = Pattern.compile("^(cos)");
        Matcher matcher = pattern.matcher(sentence);

        boolean r = matcher.find();

        if (r) {
            lastMatch = (matcher.group(0));
            movePointerFromX = lastMatch.length();

            return new TOK_FCT_COS();
        }

        return null;

    }

    public String getName() {
        return NAME;
    }
}
