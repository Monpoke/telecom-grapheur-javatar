/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.exceptions;

/**
 *
 * @author A643012
 */
public class VariableException extends Exception {

    /**
     * Creates a new instance of <code>ParsingException</code> without detail
     * message.
     */
    public VariableException() {
    }

    /**
     * Constructs an instance of <code>ParsingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public VariableException(String msg) {
        super(msg);
    }

    public VariableException(Exception parserException) {
        super(parserException);
    }
}
