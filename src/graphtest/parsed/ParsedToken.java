/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.parsed;

import java.math.BigInteger;

/**
 *
 * @author A643012
 */
public abstract class ParsedToken {
    
    protected TokenType parsedType = TokenType.UNKNOWN_TOKEN;
    
    /**
     * Used for variables
     */
    protected String variableName = "";
    
    /**
     * Used for numeric values
     */
    protected double value = 0;
    
    /**
     * Used for the expression priority
     */
    protected BigInteger priority = new BigInteger("0");

    public TokenType getParsedType() {
        return parsedType;
    }

    public void setParsedType(TokenType parsedType) {
        this.parsedType = parsedType;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    public BigInteger getPriority(){
        return this.priority;
    }
    
    public void setPriority(BigInteger priority){
        this.priority = priority;
    }
    
    /**
     * Check if it's an operator.
     * @return 
     */
    public boolean isOperator(){
        return false;
    }
    
    /**
     * Check if it's a function.
     * @return 
     */
    public boolean isFunction(){
        return false;
    }
    
}
