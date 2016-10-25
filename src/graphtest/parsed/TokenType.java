/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.parsed;

/**
 *
 * @author A643012
 */
public enum TokenType {
    
    OPERATOR_PLUS(),
    OPERATOR_MINUS,
    OPERATOR_DIVIDE(),
    OPERATOR_MULTIPLY(),
    OPERATOR_MODULO(),
    
    NUMBER,
    
    PAR_OPEN,
    PAR_CLOSE,
    
    VARIABLE,
    
    /** FUNCTIONS */
    FCT_SIN,
    FCT_COS,
    FCT_TAN,
    FCT_POW,
    
    UNKNOWN_TOKEN;
    
}
