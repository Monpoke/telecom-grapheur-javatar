package graphtest;

import graphtest.parsed.ParsedToken;
import graphtest.syntaxor.PriorityTools;
import graphtest.syntaxor.Stack;
import graphtest.syntaxor.SyntaxTools;
import java.util.ArrayList;

/**
 *
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
        ArrayList<ParsedToken> priorityArray = new ArrayList<>();
        
        if(!this.lexicalArray.isEmpty()){
            syntaxArray = SyntaxTools.lexicalIntoSyntax(this.lexicalArray);
            priorityArray = PriorityTools.addPriority(syntaxArray);
            this.orderedStack = new Stack(PriorityTools.coefficientCalculator(priorityArray));

            long minPrio = priorityArray.get(0).getPriority();
            int indexMinPrio = 0;
            
            while(!this.orderedStack.isFull()){
                for(int i=0;i<priorityArray.size()-2;i++){
                    if(priorityArray.get(i).getPriority() < minPrio){
                        minPrio = priorityArray.get(i).getPriority();
                        indexMinPrio = i;
                    }
                }
                
                this.orderedStack.push(priorityArray.get(indexMinPrio));
            }
            
            orderedStack.displayStack();
        }
    }
    
    /**
     * Return the orderedStack
     * @return Stack
     */
    public Stack getOrderedStack(){
        return this.orderedStack;
    }
}
