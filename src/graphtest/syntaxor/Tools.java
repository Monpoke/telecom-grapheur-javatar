package graphtest.syntaxor;

import graphtest.lexem.PAR_OPEN;
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
    public Stack lexicalIntoSyntax(ArrayList lexicalArray){
        Stack orderedStack = new Stack(lexicalArray.size());
        
        
        
        return orderedStack;
    }
    
    public boolean verifySyntax(ArrayList lexicalArray) throws MultipleTokenException{
        if(verifyTokenInRow(lexicalArray) == false || verifyTokenStartAndEnd(lexicalArray) == false){
            return false;
        }else{
            return true;
        }
    }
    
    public static boolean verifyTokenInRow(ArrayList lexicalArray){
        int i =0;
        boolean syntaxTokenVerify = true;
        
        while(i < lexicalArray.size()){
            if(lexicalArray.get(i) instanceof OPERATOR && lexicalArray.get(i+1) instanceof OPERATOR){
                syntaxTokenVerify = false;
            }
            i++;
        }
        
        return syntaxTokenVerify;
    }
    
    public static boolean verifyTokenStartAndEnd(ArrayList lexicalArray){
        int lastArrayIndex = lexicalArray.size()-1;
        boolean syntaxTokenVerify = true;

        // Start check. The start carac must be a number, an open parenthesis or a function
        if(! (lexicalArray.get(0) instanceof NUMBER || lexicalArray.get(0) instanceof FCT_SIN || lexicalArray.get(0) instanceof PAR_OPEN)){
            syntaxTokenVerify = false;
        }
        
        // End check. The end carac must be a number or a closing parenthesis
        if(lexicalArray.get(lastArrayIndex) instanceof FCT_SIN || lexicalArray.get(lastArrayIndex) instanceof PAR_OPEN || lexicalArray.get(lastArrayIndex) instanceof OPERATOR){
            syntaxTokenVerify = false;
        }
        
        return syntaxTokenVerify;
    }
}
