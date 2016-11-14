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

    OPERATOR_PLUS(3),
    OPERATOR_MINUS(3),
    OPERATOR_DIVIDE(5),
    OPERATOR_MULTIPLY(6),
    OPERATOR_MODULO(5),
    NUMBER,
    PAR_OPEN(8),
    PAR_CLOSE(8),
    VARIABLE,
    /**
     * FUNCTIONS
     */
    FCT_SIN,
    FCT_COS,
    FCT_TAN,
    FCT_POW,
    UNKNOWN_TOKEN;

    private int defaultPriority = 1;

    private TokenType(int priority) {
        defaultPriority = priority;
    }

    private TokenType() {
    }

    /**
     * Returns the default priority of the token.
     *
     * @return
     */
    public int getDefaultPriority() {
        return defaultPriority;
    }

}
