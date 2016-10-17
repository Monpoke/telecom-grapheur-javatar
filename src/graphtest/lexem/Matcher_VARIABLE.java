/*   
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_VARIABLE;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author A643012
 */
public class Matcher_VARIABLE extends Lexem implements Rule {

    public final static String NAME = "VARIABLE";

    public Matcher_VARIABLE() {
      
    }

    @Override
    public ParsedToken match(String sentence) {

        /**
         * Pattern for a number:
         * 1,0000 and 1.000 supported
         */
        pattern = Pattern.compile("^[a-zA-Z]");
        Matcher matcher = pattern.matcher(sentence);

        boolean r = matcher.find();
        if (r) {
            lastMatch = (matcher.group(0));
            movePointerFromX = lastMatch.length();

            TOK_VARIABLE tok_variable = new TOK_VARIABLE(lastMatch);
            return tok_variable;
        }

        return null;

    }

    public String getName() {
        return NAME;
    }
}
