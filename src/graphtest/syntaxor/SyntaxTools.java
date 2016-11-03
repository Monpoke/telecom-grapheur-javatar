package graphtest.syntaxor;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_OPERATOR_MULTIPLY;
import graphtest.parsed.TOK_PAR_CLOSE;
import graphtest.parsed.TOK_PAR_OPEN;
import graphtest.parsed.TOK_VARIABLE;
import java.util.ArrayList;

/**
 *
 * @author Florent
 */
public class SyntaxTools {
    
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
    
    public boolean verifySyntax(ArrayList<ParsedToken> lexicalArray){
        if(verifyOperatorInRow(lexicalArray) == false || verifyTokenStartAndEnd(lexicalArray) == false){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Add the unplaced MultiplyToken like 3a => 3*a
     * @param lexicalArray
     * @return ArrayList<ParsedToken>
     */
    public static ArrayList<ParsedToken> addHideMultiplyToken(ArrayList<ParsedToken> lexicalArray){
        int additionalSpace = existedHideMultiply(lexicalArray);
        
        if(additionalSpace == 0){
            return lexicalArray;
        }else{
            ArrayList<ParsedToken> multiplyArray = new ArrayList<>(lexicalArray.size()+additionalSpace);
            int multiplyIndex =0;
            
            for(int i=0;i<lexicalArray.size();i++){
                multiplyIndex += i;
                multiplyArray.add(multiplyIndex, lexicalArray.get(i));
                
                if((lexicalArray.get(i) instanceof TOK_NUMBER || lexicalArray.get(i) instanceof TOK_VARIABLE) 
                    && (lexicalArray.get(i+1) instanceof TOK_PAR_OPEN || lexicalArray.get(i+1).isFunction())){
                    multiplyArray.add(++multiplyIndex, new TOK_OPERATOR_MULTIPLY());
                }else if(lexicalArray.get(i) instanceof TOK_NUMBER && lexicalArray.get(i+1) instanceof TOK_VARIABLE){
                    multiplyArray.add(++multiplyIndex, new TOK_OPERATOR_MULTIPLY());
                }
            }
            
            return multiplyArray;
        }
    }
    
    /**
     * Return the number of hiding mulitply in the expression
     * @param lexicalArray
     * @return int
     */
    public static int existedHideMultiply(ArrayList<ParsedToken> lexicalArray){
        int nbHideMultiply = 0;
        
        for(int i=0;i<lexicalArray.size();i++){
            if((lexicalArray.get(i) instanceof TOK_NUMBER || lexicalArray.get(i) instanceof TOK_VARIABLE) 
                    && (lexicalArray.get(i+1) instanceof TOK_PAR_OPEN || lexicalArray.get(i+1).isFunction())){
                nbHideMultiply++;
            }else if(lexicalArray.get(i) instanceof TOK_NUMBER && lexicalArray.get(i+1) instanceof TOK_VARIABLE){
                nbHideMultiply++;
            }
        }
        return nbHideMultiply;
    }
    
    /**
     * Verify that there is any problem related to operator such as a + EOF or ... + b
     * @param lexicalArray
     * @return boolean
     */
    /*public static boolean verifyNumberOrParenthesisBeforeAfterOperator(ArrayList<ParsedToken> lexicalArray){
        if(verifyTokenStartAndEnd(lexicalArray)){
            for(int i=1;i<lexicalArray.size()-1;i++){
                if(lexicalArray.get(i).isOperator()){
                    
                }
            }
        }
        return false;
        
    
        //NOT RELEVANT, CAN BE REPLACE BY verifyOperatorInRow & verifyTokenStartAndEnd
    }*/
    
    /**
     * Verify that there isn't 2 variables / number in row like 2,2 4 + 3
     * Test shall be done after the multiply re-affectation
     * @param lexicalArray
     * @return boolean
     */
    public static boolean verifyNumberInRow(ArrayList<ParsedToken> lexicalArray){
        for(int i=0;i<lexicalArray.size();i++){
            if((lexicalArray.get(i) instanceof TOK_NUMBER || lexicalArray.get(i) instanceof TOK_VARIABLE) 
                    && (lexicalArray.get(i+1) instanceof TOK_NUMBER || lexicalArray.get(i+1) instanceof TOK_VARIABLE)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Verify that there is no () : of course with nothing inside parenthesis
     * @param lexicalArray
     * @return boolean 
     */
    public static boolean verifyFacingParenthesis(ArrayList<ParsedToken> lexicalArray){
        for(int i=0;i<lexicalArray.size();i++){
            if(lexicalArray.get(i) instanceof TOK_PAR_OPEN && lexicalArray.get(i+1) instanceof TOK_PAR_CLOSE){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Verify that there is no ++ or +- or any double token in a row
     * @param lexicalArray
     * @return boolean
     */
    public static boolean verifyOperatorInRow(ArrayList<ParsedToken> lexicalArray){
        int i =0;
        
        while(i < lexicalArray.size()){
            if(lexicalArray.get(i).isOperator() && lexicalArray.get(i+1).isOperator()){
                return false;
            }
            i++;
        }
        return true;
    }
    
    /**
     * Verify the begin of the sentence and the end
     * @param lexicalArray
     * @return boolean
     */
    public static boolean verifyTokenStartAndEnd(ArrayList<ParsedToken> lexicalArray){
        int lastArrayIndex = lexicalArray.size()-1;

        // Start check. The start carac must be a number, an open parenthesis or a function
        if(! (lexicalArray.get(0) instanceof TOK_NUMBER || lexicalArray.get(0).isFunction() || lexicalArray.get(0) instanceof TOK_PAR_OPEN)){
            return false;
        }
        
        // End check. The end carac must be a number or a closing parenthesis
        if(lexicalArray.get(lastArrayIndex).isFunction() || lexicalArray.get(lastArrayIndex) instanceof TOK_PAR_OPEN || lexicalArray.get(lastArrayIndex).isOperator()){
            return false;
        }
        
        return true;
    }
}
