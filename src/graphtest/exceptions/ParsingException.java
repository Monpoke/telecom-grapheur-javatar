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
public class ParsingException extends Exception {

    /**
     * Creates a new instance of <code>ParsingException</code> without detail
     * message.
     */
    public ParsingException() {
    }

    /**
     * Constructs an instance of <code>ParsingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ParsingException(String msg) {
        super(msg);
    }

    public ParsingException(Exception parserException) {
        super(parserException);
    }
}
