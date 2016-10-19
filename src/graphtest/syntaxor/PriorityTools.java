package graphtest.syntaxor;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_OPERATOR_DIVIDE;
import graphtest.parsed.TOK_OPERATOR_MINUS;
import graphtest.parsed.TOK_OPERATOR_MULTIPLY;
import graphtest.parsed.TOK_OPERATOR_PLUS;
import graphtest.parsed.TOK_PAR_CLOSE;
import graphtest.parsed.TOK_PAR_OPEN;
import graphtest.parsed.TOK_VARIABLE;
import java.util.ArrayList;

/**
 *
 * @author Florent
 */
public class PriorityTools {
    
    /**
     * Attribute priority to the token in the expression
     * @param lexicalArray
     * @return ArrayList<ParsedToken> 
     */
    public ArrayList<ParsedToken> addPriority(ArrayList<ParsedToken> lexicalArray){
        ArrayList<ParsedToken> priorityArray = new ArrayList<>();
        priorityArray = setPriorityParenthesis(lexicalArray);
        
        int coef = coefficientCalculator(priorityArray);
        
        return priorityArray;
    }
    
    /**
     * Add some parenthesis in the expression in order to respect the priority setter rules
     * See @Florent for more details about the priority setter rules
     * @param lexicalArray
     * @return ArrayList<ParsedToken>
     */
    public ArrayList<ParsedToken> setPriorityParenthesis(ArrayList<ParsedToken> lexicalArray){
        ArrayList<ParsedToken> parenthesisArray = new ArrayList<>();
        parenthesisArray = removeUselessParenthesis(parenthesisArray);
        
        for(int i=0;i<parenthesisArray.size();i++){
            if(parenthesisArray.get(i) instanceof TOK_NUMBER || parenthesisArray.get(i) instanceof TOK_VARIABLE){
                //TODO
            }
        }
        
        return parenthesisArray;
    }
    
    /**
     * Remove the useless parenthesis in the expression
     * @param lexicalArray
     * @return ArrayList<ParsedToken>
     */
    public ArrayList<ParsedToken> removeUselessParenthesis(ArrayList<ParsedToken> lexicalArray){
        ArrayList<ParsedToken> parenthesisArray = new ArrayList<>();
        parenthesisArray = lexicalArray;
        
        // Remove useless parenthesis at start and end : for exemple '(3+1)' -> '3+1'
        if(parenthesisArray.get(0) instanceof TOK_PAR_OPEN 
                && parenthesisArray.get(parenthesisArray.size()-1) instanceof TOK_PAR_CLOSE){
            parenthesisArray.remove(0);
            parenthesisArray.remove(parenthesisArray.size()-1);
        }
        
        // Remove useless parenthesis surrounding number/variables : for exemple '3+(1)' -> '3+1'
        for(int i=0;i+2<parenthesisArray.size();i++){
            if(parenthesisArray.get(i) instanceof TOK_PAR_OPEN 
                && parenthesisArray.get(i+2) instanceof TOK_PAR_CLOSE 
                    && (parenthesisArray.get(i+1) instanceof TOK_VARIABLE || parenthesisArray.get(i+1) instanceof TOK_NUMBER)){
                parenthesisArray.remove(i);
                parenthesisArray.remove(i+2);
            }
        }
        
        return parenthesisArray;
    }
    
    public static int coefficientCalculator(ArrayList<ParsedToken> parenthesisArray){
        int nbOperandNumberVar = 0;
        
        for(int i=0;i<parenthesisArray.size();i++){
            if(parenthesisArray.get(i) instanceof TOK_NUMBER 
                || parenthesisArray.get(i) instanceof TOK_VARIABLE 
                    || parenthesisArray.get(i) instanceof TOK_OPERATOR_MINUS 
                        || parenthesisArray.get(i) instanceof TOK_OPERATOR_PLUS 
                            || parenthesisArray.get(i) instanceof TOK_OPERATOR_DIVIDE 
                                || parenthesisArray.get(i) instanceof TOK_OPERATOR_MULTIPLY){
                nbOperandNumberVar++;
            }
        }
        
        return nbOperandNumberVar;
    }
    
}
