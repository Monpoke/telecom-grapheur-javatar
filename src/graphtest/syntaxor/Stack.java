package graphtest.syntaxor;

import graphtest.parsed.ParsedToken;

public class Stack {
    
    private int maxLength;
    
    private int top;
    
    private ParsedToken[] parsedTokenStack;
    
    public Stack(int maxLength){
        this.maxLength = maxLength;
        this.parsedTokenStack = new ParsedToken[maxLength-1];
        this.top += 1;
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
}