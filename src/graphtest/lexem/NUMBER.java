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
        pattern = Pattern.compile("^([0-9]+)");
        System.out.println("+++> " + pattern.pattern());
        System.out.println("===> " + pattern.matcher("sin(8)").find());

    }

    @Override
    public boolean match(String sentence) {

        Matcher matcher = pattern.matcher(sentence);

        boolean r = matcher.find();

        System.out.println("+++> " + pattern.pattern());
        System.out.println("Test de >> " + pattern.matcher("sin(8)").find());

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
