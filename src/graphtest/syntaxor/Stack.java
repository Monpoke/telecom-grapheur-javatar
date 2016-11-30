package graphtest.syntaxor;

import graphtest.parsed.ParsedToken;

public class Stack {
    
    private int maxLength;
    
    private int top;
    
    private ParsedToken[] parsedTokenStack;
    
    public Stack(int maxLength){
        this.maxLength = maxLength;
        this.parsedTokenStack = new ParsedToken[maxLength];
        this.top = -1;
    }
    
    /**
     * If Stack is empty return true
     * @return boolean
     */
    public boolean empty(){
        return this.top == -1;
    }
    
    /**
     * Unstack the first item of the stack
     * @return AbstractData
     */
    public ParsedToken pop(){
        if(!empty()){
            return this.parsedTokenStack[top--];
        }else{
            return null;
        }
    }
    
    /**
     * Push at the top of the Stack the item
     * @param parsedToken
     */
    public void push(ParsedToken parsedToken){
        if(this.top < this.maxLength){
            this.parsedTokenStack[++this.top] = parsedToken;
        }
    }
    
    /**
     * Show the last item in the stack whitout removing it
     * @return ParsedToken 
     */
    public ParsedToken peek(){
        if(!empty()){
            return this.parsedTokenStack[this.top];
        }else{
            return null;
        }
    }
    
    /**
     * Return value of the max stack length
     * @return int
     */
    public int getMaxLength(){
        return this.maxLength;
    }
    
    /**
     * Check if the Stack is full
     * @return boolean
     */
    public boolean isFull(){
        return this.top==this.maxLength-1;
    }
    
    /**
     * Display the values in the stack
     */
    public void displayStack(){
        if(!empty()){
            System.out.println("\n---Stack---");
            
            int i= this.maxLength-1;
            ParsedToken[] ptCopy = new ParsedToken[this.maxLength];
            
            /* Display and copy depop values */
            while(!empty()){
                ptCopy[i] = pop();
                System.out.println("|   "+ptCopy[i]+"   |");
                i--;
            }
            /* Recopy values into the original stack */
            while(i<this.maxLength-1){
                push(ptCopy[++i]);
            }
        }else{
            System.out.println("\nEmpty Stack : Nothing to display\n");
        }
    }
    
    /**
     * Debug fonction : display the values in the stack and their priority
     */
    public void displayStackWithPriority(){
        if(!empty()){
            System.out.println("\n---Stack---|---Priority---");
            
            int i= this.maxLength-1;
            ParsedToken[] ptCopy = new ParsedToken[this.maxLength];
            
            /* Display and copy depop values */
            while(!empty()){
                ptCopy[i] = pop();
                System.out.print("|   "+ptCopy[i]+"   |");
                System.out.println("|   "+ptCopy[i].getPriority()+"  |");
                i--;
            }
            /* Recopy values into the original stack */
            while(i<this.maxLength-1){
                push(ptCopy[++i]);
            }
        }else{
            System.out.println("\nEmpty Stack : Nothing to display\n");
        }
    }
}