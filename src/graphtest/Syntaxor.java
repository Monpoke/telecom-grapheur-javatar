package graphtest;

import graphtest.exceptions.LexicalException;
import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_OPERATOR_PLUS;
import graphtest.syntaxor.PriorityTools;
import graphtest.syntaxor.Stack;
import graphtest.syntaxor.SyntaxTools;
import java.util.ArrayList;

/**
 * Main class of the 2nd Grapheur part.
 * Designed to use all the syntaxTools and PriorityTools methods.
 * This class allows to go from the lexical part into the the syntaxical part.
 * Transform the lexicalArray into a priorityArray and then into a Stack (fill with the priorityArray to be in the good order)
 * 
 * @author Florent
 */
public class Syntaxor {
    
    private final ArrayList<ParsedToken> lexicalArray;
    private Stack orderedStack;
    
    public Syntaxor(ArrayList<ParsedToken> lexicalArray) throws LexicalException{
        this.lexicalArray = lexicalArray;
        
        addOptionalOperators();
        startSyntaxor();
    }
    
    private void startSyntaxor() throws LexicalException{
        ArrayList<ParsedToken> syntaxArray = new ArrayList<>();
        
        if(!this.lexicalArray.isEmpty()){
            syntaxArray = SyntaxTools.lexicalIntoSyntax(this.lexicalArray);
            PriorityTools.addPriority(syntaxArray);
            
            this.orderedStack = new Stack(PriorityTools.coefficientCalculator(syntaxArray));

            long minPrio = syntaxArray.get(0).getPriority();
            ParsedToken indexMinPrio = syntaxArray.get(0);
            long maxPrio = 0;
            
            for(ParsedToken element : syntaxArray){
                if(element.getPriority() > maxPrio){
                    maxPrio = element.getPriority();
                }
            }
            
            while(!this.orderedStack.isFull()){
                
                for(ParsedToken element : syntaxArray){
                    if(element.getPriority() <= minPrio){
                        minPrio = element.getPriority();
                        indexMinPrio = element;
                    }
                }
                
                this.orderedStack.push(indexMinPrio);
                syntaxArray.remove(indexMinPrio);
                minPrio = maxPrio;
            }
        }
    }
    
    /**
     * Return the orderedStack
     * @return Stack
     */
    public Stack getOrderedStack(){
        return this.orderedStack;
    }
    
    /**
     * Display the stack at the given state
     */
    public void displayOrderedStack(){
        this.orderedStack.displayStack();
    }
    
    /**
     * Display the stack at the given state with the priority
     */
    public void displayPriorityStack(){
        this.orderedStack.displayStackWithPriority();
    }

    /**
     * Optional operators
     */
    private void addOptionalOperators() {
        int size = this.lexicalArray.size();
        
        for (int i = 0; i < size-1; i++) {
            ParsedToken current = this.lexicalArray.get(i);
            ParsedToken next = this.lexicalArray.get(i+1);
            
            if(current instanceof TOK_NUMBER && next instanceof TOK_NUMBER){
                this.lexicalArray.add(i+1, new TOK_OPERATOR_PLUS());
            }
        }
    }
}
