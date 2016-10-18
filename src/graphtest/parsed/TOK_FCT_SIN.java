/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.parsed;

/**
 * This class reprensents a sin function
 */
public class TOK_FCT_SIN extends ParsedToken implements ParsedTokenInterface {
    
    public TOK_FCT_SIN() {
        this.parsedType = TokenType.FCT_SIN;
    }
    
    
    @Override
    public boolean isFunction() {
        return true;
    }
}
