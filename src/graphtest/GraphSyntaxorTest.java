package graphtest;

import graphtest.exceptions.ParsingException;
import graphtest.parsed.ParsedToken;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class allows to test the 2nd part of the the Grapheur : Syntaxical part
 * This class suppose that the lexical part is correct.
 * To see a separated test refer to the Test package
 * @author Florent
 */
public class GraphSyntaxorTest {
    
    public static void main(String[] args) {

        //---Lexical Part---
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
            
            //---Syntax Part---
            Syntaxor syntaxor = new Syntaxor(parsedTokenList);
            
        } catch (ParsingException ex) {
            Logger.getLogger(GraphTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
    private static void displayList(ArrayList<ParsedToken> parsedTokenList) {
        for(ParsedToken parsedToken :parsedTokenList){
            System.out.print(parsedToken.toString() + " ");
        }
        System.out.println("");
    }

}
