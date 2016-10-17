/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.parsed;

/**
 * This class reprensents a number
 */
public class TOK_VARIABLE extends ParsedToken implements ParsedTokenInterface {
    
    public TOK_VARIABLE(String variableName) {
        this.parsedType = TokenType.VARIABLE;
        this.setVariableName(variableName);
    }
    
    
}
