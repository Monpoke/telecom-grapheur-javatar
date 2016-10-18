package graphtest.syntaxor.exception;

public class MultipleTokenException extends Exception{

    public MultipleTokenException(){
        System.out.println("Multiple Tokens in a row detected\n");
    }
}
