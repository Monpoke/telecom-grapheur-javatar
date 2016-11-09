package graphtest;

import graphtest.parsed.ParsedToken;
import graphtest.syntaxor.PriorityTools;
import graphtest.syntaxor.Stack;
import graphtest.syntaxor.SyntaxTools;
import java.util.ArrayList;

/**
 * Main class of the 2nd Grapheur part.
 * Designed to use all the syntaxTools and PriorityTools methods.
 * This class allows to go from the lexical part into the the syntaxical part.
 * Transform the lexicalArray into a priorityArray and then into a Stack (fill with the priorityArray to be in the good order)
 * @author Florent
 */
public class Syntaxor {
    
    private final ArrayList<ParsedToken> lexicalArray;
    private Stack orderedStack;
    
    public Syntaxor(ArrayList<ParsedToken> lexicalArray){
        this.lexicalArray = lexicalArray;
        startSyntaxor();
    }
    
    private void startSyntaxor(){
        ArrayList<ParsedToken> syntaxArray = new ArrayList<>();
        
        if(!this.lexicalArray.isEmpty()){
            syntaxArray = SyntaxTools.lexicalIntoSyntax(this.lexicalArray);
            PriorityTools.addPriority(syntaxArray);
            
            this.orderedStack = new Stack(PriorityTools.coefficientCalculator(syntaxArray));

            long minPrio = syntaxArray.get(0).getPriority();
            int indexMinPrio = 0;
            
            while(!this.orderedStack.isFull()){
                for(int i=0;i<syntaxArray.size()-2;i++){
                    if(syntaxArray.get(i).getPriority() < minPrio){
                        minPrio = syntaxArray.get(i).getPriority();
                        indexMinPrio = i;
                    }
                }
                
                this.orderedStack.push(syntaxArray.get(indexMinPrio));
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
    
    public void displayOrderedStack(){
        this.orderedStack.displayStack();
    }
}
