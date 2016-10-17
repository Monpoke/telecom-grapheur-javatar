package graphtest.syntaxor;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_PAR_OPEN;
import java.util.ArrayList;
import graphtest.syntaxor.exception.MultipleTokenException;

/**
 *
 * @author Florent
 */
public class Tools {
    
    /**
     * Get the unordered ArrayList of Lexem and return the ordered syntax
     * @param lexicalArray
     * @return Stack
     */
    public Stack lexicalIntoSyntax(ArrayList<ParsedToken> lexicalArray){
        Stack orderedStack = new Stack(lexicalArray.size());
        
        if(verifySyntax(lexicalArray)){
            return orderedStack;
        }else{
            return null;
        }
    }
    
    public boolean verifySyntax(ArrayList<ParsedToken> lexicalArray) /*throws MultipleTokenException*/{
        if(verifyTokenInRow(lexicalArray) == false || verifyTokenStartAndEnd(lexicalArray) == false){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Verify that there is no ++ or +- or any double token in a row
     * @param lexicalArray
     * @return boolean
     */
    public static boolean verifyTokenInRow(ArrayList<ParsedToken> lexicalArray){
        int i =0;
        boolean syntaxTokenVerify = true;
        
        while(i < lexicalArray.size()){
            if(lexicalArray.get(i).isOperator() && lexicalArray.get(i+1).isOperator()){
                syntaxTokenVerify = false;
            }
            i++;
        }
        
        return syntaxTokenVerify;
    }
    
    /**
     * Verify the begin of the sentence and the end
     * @param lexicalArray
     * @return boolean
     */
    public static boolean verifyTokenStartAndEnd(ArrayList<ParsedToken> lexicalArray){
        int lastArrayIndex = lexicalArray.size()-1;
        boolean syntaxTokenVerify = true;

        // Start check. The start carac must be a number, an open parenthesis or a function
        if(! (lexicalArray.get(0) instanceof TOK_NUMBER || lexicalArray.get(0).isFunction() || lexicalArray.get(0) instanceof TOK_PAR_OPEN)){
            syntaxTokenVerify = false;
        }
        
        // End check. The end carac must be a number or a closing parenthesis
        if(lexicalArray.get(lastArrayIndex).isFunction() || lexicalArray.get(lastArrayIndex) instanceof TOK_PAR_OPEN || lexicalArray.get(lastArrayIndex).isOperator()){
            syntaxTokenVerify = false;
        }
        
        return syntaxTokenVerify;
    }
}
