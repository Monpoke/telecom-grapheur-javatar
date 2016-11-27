/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest;

import graphtest.evaluator.Evaluator;
import graphtest.evaluator.Variable;
import graphtest.exceptions.ParsingException;
import graphtest.exceptions.VariableException;
import graphtest.parsed.ParsedToken;
import graphtest.treeverter.TreeConverter;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author A643012
 */
public class MathParser {


    /**
     * Gets an evaluator from math string.
     * @param fonction
     * @return 
     */
    public static Evaluator getEvaluatorFromMathString(String fonction) {
        Scanner s = new Scanner(System.in);

        String math = "";
        try {
            // FIRST STEP
            Parser parser = new Parser(fonction);

            // SECOND STEP
            ArrayList<ParsedToken> parsedTokenList = parser.getParsedTokenList();

            // CORRECT SYNTAX
            // TREE
            TreeConverter converter = new TreeConverter(parsedTokenList);
            TreeNode root = converter.getRoot();

            // it should be impossible to reach this stade.
            if (root == null) {
                System.exit(1);
            }

            // EVALUATOR
            Evaluator evaluator = new Evaluator(root);
            
            // check context
            registerVariables(evaluator, 1);
            evaluator.evaluate();
            
            return evaluator;
        } catch (ParsingException ex) {
            //JOptionPane.showMessageDialog(null, "Erreur dans l'expression mathématique :\n" + ex.getMessage());
            Logger.getLogger(MathParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (VariableException ex) {
            //JOptionPane.showMessageDialog(null, "Une variable/constante n'est pas définie :\n" + ex.getMessage());
            Logger.getLogger(MathParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Une erreur est survenue !\n"+ex.getMessage());
            Logger.getLogger(MathParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Evaluate with a "x" as parameter.
     * @param eval
     * @param x
     * @return 
     */
    public static double evaluate(Evaluator eval, double x) {
        if(eval==null){
            return 0;
        }
        // global variables
        registerVariables(eval, x);
        
        try {
            return eval.evaluate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return new Double(0);
    }

    private static void registerVariables(Evaluator eval, double x){
        
        eval.resetScope();
        
        eval.addVariable(new Variable("x", x));
        eval.addVariable(new Variable("e", Math.E));
    }
    
}
