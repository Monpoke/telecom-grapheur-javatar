package graphtest.syntaxor;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_PAR_CLOSE;
import graphtest.parsed.TOK_PAR_OPEN;
import graphtest.parsed.TOK_VARIABLE;
import java.util.ArrayList;

/**
 *
 * @author Florent
 */
public class PriorityTools {
    
    public ArrayList<ParsedToken> addPriority(ArrayList<ParsedToken> lexicalArray){
        return null;
    }
    
    public ArrayList<ParsedToken> setPriorityParenthesis(ArrayList<ParsedToken> lexicalArray){
        ArrayList<ParsedToken> parenthesisArray = new ArrayList<>();
        parenthesisArray = removeUselessParenthesis(parenthesisArray);
        
        
        
        return null;
    }
    
    public ArrayList<ParsedToken> removeUselessParenthesis(ArrayList<ParsedToken> lexicalArray){
        ArrayList<ParsedToken> parenthesisArray = new ArrayList<>();
        parenthesisArray = lexicalArray;
        
        if(parenthesisArray.get(0) instanceof TOK_PAR_OPEN 
                && parenthesisArray.get(parenthesisArray.size()-1) instanceof TOK_PAR_CLOSE){
            parenthesisArray.remove(0);
            parenthesisArray.remove(parenthesisArray.size()-1);
        }
        
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
    
}
