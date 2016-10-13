/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest;

import graphtest.lexem.FCT_SIN;
import graphtest.lexem.NUMBER;
import graphtest.lexem.OPERATOR_DIVIDE;
import graphtest.lexem.OPERATOR_MINUS;
import graphtest.lexem.OPERATOR_MULTIPLY;
import graphtest.lexem.OPERATOR_PLUS;
import graphtest.lexem.Rule;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String remaining = math.substring(i);
        // avancement caractère par caractère
        do {
            boolean found = false;

            if (remaining.isEmpty()) {
                break;
            }

            for (Rule rule : lexem) {

                // reset rule
                rule.reset();

                try {

                    if (rule.match(remaining)) {
                        System.out.println("Match: " + rule.getName() + " - " + rule.getLastMatch());
                        i += rule.movePointerFrom();

                        found = true;
                        // block next rules
                        break;
                    }

                } catch (Exception parserException) {
                    System.out.println("ERROR..." + parserException.getMessage());
                    parserException.printStackTrace();
                }
            }

            if (!found) {
                i++;
            } else {
                try {
                    throw new Exception("Unknown token");
                } catch (Exception ex) {
                    System.out.println("Unknown token");
                }
            }

            remaining = math.substring(i);
        } while (!remaining.isEmpty());

    }

    private void registerLexem() {

        // OPERATORS
        lexem.add(new OPERATOR_PLUS());
        lexem.add(new OPERATOR_MINUS());
        lexem.add(new OPERATOR_DIVIDE());
        lexem.add(new OPERATOR_MULTIPLY());

        // CHECK NUMBER
        lexem.add(new NUMBER());

        // FCTS
        lexem.add(new FCT_SIN());

    }

}
