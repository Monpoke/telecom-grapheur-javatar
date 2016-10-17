package graphtest.syntaxor;

public class Stack {
    
    private int maxLength;
    
    private int top;
    
    //private AbastractOperator&Number
    
    public Stack(int maxLength){
        this.maxLength = maxLength;
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
    public /*AbstractData*/ pop(){
        if(!empty()){
            return /*this.abstractdata[top--]*/
        }else{
            return null;
        }
    }
    
    /**
     * Push at the top of the Stack the item
     */
    public void push(/**AbstractData**/){
        if(this.top < this.maxLength){
            /**this.abstractData[++this.top]**/
        }else{
            return null;
        }
    }
    
}