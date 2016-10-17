/*   
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author A643012
 */
public class Matcher_NUMBER extends Lexem implements Rule {

    public final static String NAME = "NUMBER";

    public Matcher_NUMBER() {
      
    }

    @Override
    public ParsedToken match(String sentence) {

        /**
         * Pattern for a number:
         * 1,0000 and 1.000 supported
         */
        pattern = Pattern.compile("^([0-9]+((,|\\.)[0-9])?)");
        Matcher matcher = pattern.matcher(sentence);

        boolean r = matcher.find();
        if (r) {
            lastMatch = (matcher.group(0));
            movePointerFromX = lastMatch.length();

            TOK_NUMBER tok_number = new TOK_NUMBER(Double.parseDouble(lastMatch));
            
            return tok_number;
        }

        return null;

    }

    public String getName() {
        return NAME;
    }
}
