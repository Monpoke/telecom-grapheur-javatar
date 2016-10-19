package graphtest.syntaxor;

/**
 *
 * @author Florent
 */
public enum PriorityRules {
    TOK_PAR_OPEN_RULE(6),
    TOK_PAR_CLOSE_RULE(6),
    TOK_FCT_SIN_RULE(5),
    TOK_FCT_COS_RULE(5),
    TOK_FCT_TAN_RULE(5),
    TOK_OPERATOR_DIVIDE_RULE(3),
    TOK_OPERATOR_MULTIPLY_RULE(3),
    TOK_OPERATOR_MINUS_RULE(2),
    TOK_OPERATOR_PLUS_RULE(2),
    TOK_NUMBER_RULE(0),
    TOK_VARIABLE_RULE(0);
    
    private int priorityValue = 0;
    
    PriorityRules(int value){
        this.priorityValue = value;
    }
    
    public int getPriorityValue(){
        return this.priorityValue;
    }
}
