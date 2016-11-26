/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest;

import graphtest.exceptions.ParsingException;
import graphtest.lexem.Matcher_CONSTANT;
import graphtest.lexem.Matcher_FCT_COS;
import graphtest.lexem.Matcher_FCT_SIN;
import graphtest.lexem.Matcher_FCT_TAN;
import graphtest.lexem.Matcher_NUMBER;
import graphtest.lexem.Matcher_OPERATOR_DIVIDE;
import graphtest.lexem.Matcher_OPERATOR_MINUS;
import graphtest.lexem.Matcher_OPERATOR_MODULO;
import graphtest.lexem.Matcher_OPERATOR_MULTIPLY;
import graphtest.lexem.Matcher_PAR_CLOSE;
import graphtest.lexem.Matcher_PAR_OPEN;
import graphtest.lexem.Matcher_VARIABLE;
import graphtest.lexem.Matcher_OPERATOR_PLUS;
import graphtest.lexem.Matcher_OPERATOR_POWER;
import graphtest.lexem.Rule;
import graphtest.parsed.ParsedToken;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author A643012
 */
class Parser {

    private List<Rule> registeredMatchers = new ArrayList<>();
    private ArrayList<ParsedToken> parsedTokenList = new ArrayList<>();

    /**
     * Contains the original phrase to process
     */
    private final String math;

    public Parser(String math) throws ParsingException {
        this.math = cleanMath(math);

        registerLexem();

        startParser();
    }
    
    private String cleanMath(String input){
        input = input.trim().replace(" ", "");
        return input;
    }

    private void startParser() throws ParsingException {

        int i = 0;
        String remaining = math.substring(i);
        // avancement caractère par caractère
        do {
            boolean found = false;

            if (remaining.isEmpty()) {
                break;
            }

            for (Rule rule : registeredMatchers) {

                // reset rule
                rule.reset();

                try {
                    
                    /**
                     * IF THE MATCHER HAS FOUND SMTH
                     */
                    ParsedToken match = rule.match(remaining);
                    if (match != null) {
                        
                        // Move the pointer from the length of matched content
                        i += rule.movePointerFrom();

                        parsedTokenList.add(match);
                        found = true;
                        // block next rules
                        break;
                    }

                } catch (Exception parserException) {
                    System.out.println("ERROR..." + parserException.getMessage());
                    parserException.printStackTrace();
                    
                    throw new ParsingException(parserException);
                }
            }

            if (!found) {
                i++;
                
                throw new ParsingException("Unknown token -> " + remaining);
            }

            remaining = math.substring(i);
        } while (!remaining.isEmpty());

        
        displayTokensList(parsedTokenList);
    }

    /**
     * This function registers the differents lexems.
     */
    private void registerLexem() {

        // CHECK NUMBER
        registeredMatchers.add(new Matcher_NUMBER());
        
        // OPERATORS
        registeredMatchers.add(new Matcher_OPERATOR_PLUS());
        registeredMatchers.add(new Matcher_OPERATOR_MINUS());
        registeredMatchers.add(new Matcher_OPERATOR_POWER());
        registeredMatchers.add(new Matcher_OPERATOR_DIVIDE());
        registeredMatchers.add(new Matcher_OPERATOR_MULTIPLY());
        registeredMatchers.add(new Matcher_OPERATOR_MODULO());


        // FCTS
        registeredMatchers.add(new Matcher_FCT_SIN());
        registeredMatchers.add(new Matcher_FCT_COS());
        registeredMatchers.add(new Matcher_FCT_TAN());

        // PARENTHESIS
        registeredMatchers.add(new Matcher_PAR_OPEN());
        registeredMatchers.add(new Matcher_PAR_CLOSE());
        
        // detect constant
        registeredMatchers.add(new Matcher_CONSTANT());
        
        // last rule, variable. If registered before, functions won't be processed
        registeredMatchers.add(new Matcher_VARIABLE());
    }

    public ArrayList<ParsedToken> getParsedTokenList() {
        return parsedTokenList;
    }

    
    
    /**
     * Displays a list of parsed tokens.
     * @param parsedTokenList 
     */
    private void displayTokensList(List<ParsedToken> parsedTokenList) {
        System.out.println("FIRST STEP: ANALYSE SYNTAXIQUE");
        
        for(ParsedToken parsed : parsedTokenList){
            System.out.println(parsed.getClass().getName() +"\t" + parsed.getValue() + "\t" + parsed.getVariableName());
        }
    }

}
