/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest;

import graphtest.evaluator.Evaluator;
import graphtest.evaluator.Variable;
import graphtest.exceptions.ParsingException;
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
public class GraphTest {


    public static Evaluator lancementProjet(String fonction) {
        Scanner s = new Scanner(System.in);

        String math = "";
        try {
            // FIRST STEP
            Parser parser = new Parser(fonction);

            // SECOND STEP
            ArrayList<ParsedToken> parsedTokenList = parser.getParsedTokenList();
            displayList(parsedTokenList);

            // CORRECT SYNTAX
            // TREE
            TreeConverter converter = new TreeConverter(parsedTokenList);
            TreeNode root = converter.getRoot();
            displayList(converter.getParsedTokenList());

            if (root == null) {
                System.exit(1);
            }

            BTreePrinter bTreePrinter = new BTreePrinter();
            bTreePrinter.printNode(root);

            // EVALUATOR
            Evaluator evaluator = new Evaluator(root);
            return evaluator;
        } catch (ParsingException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(GraphTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GraphTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static double evaluateur(Evaluator eval, double x) {
        if(eval==null){
            return 0;
        }
        eval.resetScope();
        eval.addVariable(new Variable("x", x));
        try {
            return eval.evaluate();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Double(0);
    }

    private static void displayList(ArrayList<ParsedToken> parsedTokenList) {
        for (ParsedToken parsedToken : parsedTokenList) {
            System.out.print(parsedToken.toString() + " (" + parsedToken.getPriority() + ") ");
        }

        System.out.println("");
    }
    
    
}
