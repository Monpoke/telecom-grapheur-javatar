/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest;

import graphtest.lexem.FCT_SIN;
import graphtest.lexem.NUMBER;
import graphtest.lexem.OPERATOR_PLUS;
import graphtest.lexem.Rule;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author A643012
 */
class Parser {

    private List<Rule> lexem = new ArrayList<>();

    /**
     * Contains the original phrase to process
     */
    private final String math;

    public Parser(String math) {
        this.math = math;

        registerLexem();

        startParser();
    }

    private void startParser() {

        int i = 0;
        String remaining;
        // avancement caractère par caractère
        do {
            remaining = math.substring(i);
            boolean found = false;

            for (Rule rule : lexem) {

                // reset rule
                rule.reset();

                

                if (rule.match(remaining)) {
                    System.out.println("Match: " + rule.getName() + " - " + rule.getLastMatch());
                    i += rule.movePointerFrom();

                    found = true;
                    // block next rules
                    break;
                }
            }

            if (!found) {
                i++;
            }
        } while (remaining.length() > 1);

    }

    private void registerLexem() {

        lexem.add(new OPERATOR_PLUS());
        lexem.add(new NUMBER());
        
        // FCTS
        lexem.add(new FCT_SIN());

    }

}
