package graphtest;

import graphtest.parsed.ParsedToken;
import graphtest.syntaxor.Stack;
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
        
        if(!this.lexicalArray.isEmpty()){
            
        }
        
        displayOrderedStack();
    }
    
    public Stack getOrderedStack(){
        return this.orderedStack;
    }
    
    private void displayOrderedStack(){
        System.out.println("\n\nSECOND STEP : ANALYSE SYNTAXIQUE\n");
        
        Stack copiedStack = this.orderedStack;
        String stackContains = "";
        int i = 0;
        
        while(copiedStack.empty()){
            stackContains += copiedStack.pop();
            i++;
        }
        
        String reverseContains = "-----";
        
        while(i >= 0){
            reverseContains += "\n"+ stackContains.charAt(i);
            i--;
        }
        
        System.out.println("\n"+reverseContains+"\n-----");
    }
}
