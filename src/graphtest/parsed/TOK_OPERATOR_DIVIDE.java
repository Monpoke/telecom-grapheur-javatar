/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.parsed;

/**
 * This class reprensents a number
 */
public class TOK_OPERATOR_DIVIDE extends ParsedToken implements ParsedTokenInterface {
    
    public TOK_OPERATOR_DIVIDE() {
        this.parsedType = TokenType.OPERATOR_DIVIDE;
    }
    
    
    @Override
    public boolean isOperator() {
        return true; 
    }
}
