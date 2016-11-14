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

/**
 *
 * @author A643012
 */
public class GraphTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        String math = "";

        do {
            System.out.println("Phrase: ");
            math = s.nextLine();
        } while (math.length() <= 0);

        System.out.println("Lancement du parsage...");

        try {
            // FIRST STEP
            Parser parser = new Parser(math);

            // SECOND STEP
            ArrayList<ParsedToken> parsedTokenList = parser.getParsedTokenList();
            displayList(parsedTokenList);

            // CORRECT SYNTAX
            // TREE
            TreeNode tree = new TreeNode(null);
            TreeConverter converter = new TreeConverter(parsedTokenList);
            
            System.out.println("-----------\nConverter\n-----------\n");
            displayList(converter.getParsedTokenList());
            
            
            
            /*
            // EVALUATOR
            Evaluator evaluator = new Evaluator(tree);
            evaluator.addVariable(new Variable("x", 2));
            System.out.println("Resultat=" + evaluator.evaluate());*/
            
        } catch (ParsingException ex) {
            Logger.getLogger(GraphTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GraphTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void displayList(ArrayList<ParsedToken> parsedTokenList) {
        for (ParsedToken parsedToken : parsedTokenList) {
            System.out.print(parsedToken.toString() + " ("+parsedToken.getPriority()+") ");
        }

        System.out.println("");
    }

}
