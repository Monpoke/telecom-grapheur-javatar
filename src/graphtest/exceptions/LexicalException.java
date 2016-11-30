package graphtest.exceptions;

/**
 *
 * @author Florent
 */
public class LexicalException extends Exception{
    
    /**
     * Creates a new instance of <code>LexicalException</code> without detail
     * message.
     */
    public LexicalException() {
    }

    /**
     * Constructs an instance of <code>LexicalException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LexicalException(String msg) {
        super(msg);
    }

    public LexicalException(Exception parserException) {
        super(parserException);
    }
}
