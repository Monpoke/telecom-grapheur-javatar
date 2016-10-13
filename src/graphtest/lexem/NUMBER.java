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
public class NUMBER extends Lexem implements Rule {

    public final static String NAME = "NUMBER";

    public NUMBER() {
      
    }

    @Override
    public boolean match(String sentence) {

        pattern = Pattern.compile("^([0-9]+((,|\\.)[0-9])?)");
        Matcher matcher = pattern.matcher(sentence);

        boolean r = matcher.find();
        if (r) {
            lastMatch = (matcher.group(0));
            System.out.println(lastMatch);
            movePointerFromX = lastMatch.length();

            return true;
        }

        return false;

    }

    public String getName() {
        return NAME;
    }
}
