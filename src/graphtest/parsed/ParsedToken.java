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
    protected long priority = 0;

    protected boolean processed = false;

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

    public long getPriority() {
        return this.priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    /**
     * Check if it's an operator.
     *
     * @return
     */
    public boolean isOperator() {
        return false;
    }

    /**
     * Check if it's a function.
     *
     * @return
     */
    public boolean isFunction() {
        return false;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    
}
