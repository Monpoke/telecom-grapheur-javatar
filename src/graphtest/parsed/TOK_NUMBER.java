/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.parsed;

/**
 * This class reprensents a number
 */
public class TOK_NUMBER extends ParsedToken implements ParsedTokenInterface {
    
    public TOK_NUMBER(double value) {
        this.parsedType = TokenType.NUMBER;
        this.setValue(value);
    }

    public TOK_NUMBER() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public String toString() {
        return  ""+this.getValue() ;
    }
    
}
