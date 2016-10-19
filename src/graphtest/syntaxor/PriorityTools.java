package graphtest.syntaxor;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_PAR_CLOSE;
import graphtest.parsed.TOK_PAR_OPEN;
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
        
        ArrayList<ParsedToken> parenthesisPosition = new ArrayList<>();
        
        int i = 0;
        
        while(i < parenthesisArray.size()){
            if(parenthesisArray.get(i) instanceof TOK_PAR_OPEN){
                
            }else if(parenthesisArray.get(i) instanceof TOK_PAR_CLOSE){
                
            }
            
            i++;
        }
        
        
        
        return null;
    }
    
}
