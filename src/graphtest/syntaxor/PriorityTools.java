package graphtest.syntaxor;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_OPERATOR_DIVIDE;
import graphtest.parsed.TOK_OPERATOR_MINUS;
import graphtest.parsed.TOK_OPERATOR_MULTIPLY;
import graphtest.parsed.TOK_OPERATOR_PLUS;
import graphtest.parsed.TOK_PAR_CLOSE;
import graphtest.parsed.TOK_PAR_OPEN;
import graphtest.parsed.TOK_VARIABLE;
import java.util.ArrayList;

/**
 *
 * @author Florent
 */
public class PriorityTools {
    
    /**
     * Attribute priority to the token in the expression
     * @param lexicalArray
     */
    public static void addPriority(ArrayList<ParsedToken> lexicalArray){
        ArrayList<ParsedToken> priorityArray = new ArrayList<>();
        // priorityArray = setPriorityParenthesis(lexicalArray); Commented for now resolve setPriorityParenthesis
        priorityArray = lexicalArray;   // to replace by above when debugged
        
        int coefNbToken = coefficientCalculator(priorityArray);
        long coefTokenRule = coefNbToken;
        
        for(int i=0;i<priorityArray.size();i++){
            System.out.println("i :"+i+"size : "+priorityArray.get(i));
            
            if(priorityArray.get(i) instanceof TOK_PAR_OPEN){
                System.out.println("1st");
                // incrementing prio value on '('
                coefTokenRule *= PriorityRules.TOK_PAR_OPEN_RULE.getPriorityValue();
            }else if(priorityArray.get(i) instanceof TOK_PAR_CLOSE){
                System.out.println("2nd");
                // decrementing prio value on ')'
                coefTokenRule /= PriorityRules.TOK_PAR_CLOSE_RULE.getPriorityValue();
            }
            
            // Adding priority into ParsedToken
            if((priorityArray.get(i) instanceof TOK_OPERATOR_MULTIPLY || priorityArray.get(i) instanceof TOK_OPERATOR_DIVIDE )&& priorityArray.get(i+1) instanceof TOK_PAR_OPEN){
                System.out.println("if");
                // Avoiding [(y+x)*(y+x)] multiply's problem priority -> Multiply Token shall have a coef of priority as high as the next parenthesis expression
                priorityArray.get(i).setPriority(i+coefTokenRule*PriorityRules.TOK_OPERATOR_MULTIPLY_RULE.getPriorityValue());  // Multiply and Divide Token Rule shall be the same for mathemical priority reason
            }else if(schemaXOperatorY(priorityArray,i)){
                System.out.println("else if");
                // When x+y the lexical order prevail -> no use of the token values
                priorityArray.get(i).setPriority(i+coefTokenRule);
            }else{
                System.out.println("else");
                // Regular case of calculation
                priorityArray.get(i).setPriority(regularPriorityCalculation(priorityArray,coefTokenRule,i));
            }
        }
        
        // Now that the priorities are set we don't need parenthesis anymore (Stack shall not have any '()' in it)
        for(int i=priorityArray.size()-1;i>=0;i--){
            if(priorityArray.get(i) instanceof TOK_PAR_OPEN || priorityArray.get(i) instanceof TOK_PAR_CLOSE){
                priorityArray.remove(i);
            }
        }
    }
    
    /**
     * Add some parenthesis in the expression in order to respect the priority setter rules
     * See @Florent for more details about the priority setter rules
     * 
     * Try remember what it does ... : put parenthesis around x and / to ensure their working
     * 
     * @param lexicalArray
     * @return ArrayList<ParsedToken>
     */
    /*public static ArrayList<ParsedToken> setPriorityParenthesis(ArrayList<ParsedToken> lexicalArray){
        ArrayList<ParsedToken> parenthesisArray = new ArrayList<>();
        parenthesisArray = removeUselessParenthesis(lexicalArray);
        
        for(int i=1;i<parenthesisArray.size()-1;i++){
            if(parenthesisArray.get(i).isOperator()){
                if(parenthesisArray.get(i-1) instanceof TOK_PAR_CLOSE && parenthesisArray.get(i+2) instanceof TOK_PAR_OPEN){
                    parenthesisArray.add(i+2, new TOK_PAR_CLOSE());
                    parenthesisArray.add(indexOpenParenthesis(parenthesisArray,i), new TOK_PAR_OPEN());
                }else if(parenthesisArray.get(i+1) instanceof TOK_PAR_OPEN && !(parenthesisArray.get(i-2) instanceof TOK_PAR_OPEN)){
                    parenthesisArray.add(indexCloseParenthesis(parenthesisArray,i), new TOK_PAR_CLOSE());
                    parenthesisArray.add(i-2, new TOK_PAR_OPEN());
                }
            }
        }
        
        return parenthesisArray;
    }*/
    
    /**
     * Remove the useless parenthesis in the expression
     * @param lexicalArray
     * @return ArrayList<ParsedToken>
     */
    public static ArrayList<ParsedToken> removeUselessParenthesis(ArrayList<ParsedToken> lexicalArray){
        ArrayList<ParsedToken> parenthesisArray = new ArrayList<>();
        parenthesisArray = lexicalArray;
        
        // Allow to redo the function to remove multiple surrounding parenthesis : '((x))'
        boolean existingUselessParenthesis = false;
        
        // Remove useless parenthesis at start and end : for exemple '(3+1)' -> '3+1'
        if(parenthesisArray.get(0) instanceof TOK_PAR_OPEN 
                && parenthesisArray.get(parenthesisArray.size()-1) instanceof TOK_PAR_CLOSE){
            parenthesisArray.remove(parenthesisArray.size()-1);
            parenthesisArray.remove(0);
        }
        
        // Remove useless parenthesis surrounding number/variables : for exemple '3+(1)' -> '3+1'
        for(int i=0;i+2<parenthesisArray.size();i++){
            if(parenthesisArray.get(i) instanceof TOK_PAR_OPEN
                    && parenthesisArray.get(i+2) instanceof TOK_PAR_CLOSE 
                        && (parenthesisArray.get(i+1) instanceof TOK_VARIABLE || parenthesisArray.get(i+1) instanceof TOK_NUMBER)){
                parenthesisArray.remove(i+2);   // remove i+2 first because of shifting remove()
                parenthesisArray.remove(i);
                existingUselessParenthesis = true;
            }
        }
        
        while(existingUselessParenthesis){
            return removeUselessParenthesis(parenthesisArray);
        }
        
        return parenthesisArray;
    }
    
    /**
     * Calculate the number of token, except the parenthesis and functions.
     * This has to be done to multiply or rules of priority by this coef to avoid duplicate priority
     * @param parenthesisArray
     * @return int
     */
    public static int coefficientCalculator(ArrayList<ParsedToken> parenthesisArray){
        int nbOperandNumberVar = 0;
        
        for(int i=0;i<parenthesisArray.size();i++){
            if(parenthesisArray.get(i) instanceof TOK_NUMBER 
                || parenthesisArray.get(i) instanceof TOK_VARIABLE 
                    || parenthesisArray.get(i).isFunction()
                        || parenthesisArray.get(i).isOperator()){
                nbOperandNumberVar++;
            }
        }
        
        return nbOperandNumberVar;
    }
    
    /**
     * Give the index where the setPriority method should place the TOK_PAR_CLOSE
     * @param parenthesisArray
     * @param index
     * @return int
     */
    public static int indexCloseParenthesis(ArrayList<ParsedToken> parenthesisArray, int index){
        int nbOpen = 0;
        int nbClose = 0;
        int i = index+1;
        
        do{
            if(parenthesisArray.get(i) instanceof TOK_PAR_OPEN){
                nbOpen++;
            }else if(parenthesisArray.get(i) instanceof TOK_PAR_CLOSE){
                nbClose++;
            }
            i++;
        }while(nbOpen > nbClose && !parenthesisArray.isEmpty());
        
        return i;
    }
    
    /**
     * Give the index where the setPriority method should place the TOK_PAR_OPEN
     * @param parenthesisArray
     * @param index
     * @return int
     */
    public static int indexOpenParenthesis(ArrayList<ParsedToken> parenthesisArray, int index){
        int nbOpen = 0;
        int nbClose = 0;
        int i = index-1;
        
        do{
            if(parenthesisArray.get(i) instanceof TOK_PAR_OPEN){
                nbOpen++;
            }else if(parenthesisArray.get(i) instanceof TOK_PAR_CLOSE){
                nbClose++;
            }
            i++;
        }while(nbClose > nbOpen && i>=0);
        
        return i;
    }
    
    /**
     * Check if there is parenthesis before or after an operator
     * If so, this mean that it doesn't match the schema 'x+y'
     * i has to be index of an operator
     * @param priorityArray
     * @param i
     * @return boolean
     */
    public static boolean schemaXOperatorY(ArrayList<ParsedToken> priorityArray, int i){
        if(priorityArray.get(i).isOperator()){
            if((priorityArray.get(i+1) instanceof TOK_VARIABLE || priorityArray.get(i+1) instanceof TOK_NUMBER)
                    &&(priorityArray.get(i-1) instanceof TOK_VARIABLE || priorityArray.get(i-1) instanceof TOK_NUMBER)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    
    /**
     * Associate the token with his priority + calculate the priority at this time
     * On error return 0
     * @param priorityArray
     * @param coefTokenRule
     * @param i
     * @return long
     */
    public static long regularPriorityCalculation(ArrayList<ParsedToken> priorityArray, long coefTokenRule, int i){
        long regularPriority = i+coefTokenRule;
        
        switch(priorityArray.get(i).getParsedType()){
            case PAR_OPEN :
                regularPriority += PriorityRules.TOK_PAR_OPEN_RULE.getPriorityValue();
                break;
            case PAR_CLOSE :
                regularPriority += PriorityRules.TOK_PAR_CLOSE_RULE.getPriorityValue();
                break;
            case FCT_COS :
                regularPriority += PriorityRules.TOK_FCT_COS_RULE.getPriorityValue();
                break;
            case FCT_SIN :
                regularPriority += PriorityRules.TOK_FCT_SIN_RULE.getPriorityValue();
                break;
            case FCT_TAN :
                regularPriority += PriorityRules.TOK_FCT_TAN_RULE.getPriorityValue();
                break;
            case OPERATOR_DIVIDE :
                regularPriority += PriorityRules.TOK_OPERATOR_DIVIDE_RULE.getPriorityValue();
                break;
            case OPERATOR_MULTIPLY :
                regularPriority += PriorityRules.TOK_OPERATOR_MULTIPLY_RULE.getPriorityValue();
                break;
            case OPERATOR_MINUS :
                regularPriority += PriorityRules.TOK_OPERATOR_MINUS_RULE.getPriorityValue();
                break;
            case OPERATOR_PLUS :
                regularPriority += PriorityRules.TOK_OPERATOR_PLUS_RULE.getPriorityValue();
                break;
            case NUMBER :
                regularPriority += PriorityRules.TOK_NUMBER_RULE.getPriorityValue();
                break;
            case VARIABLE :
                regularPriority += PriorityRules.TOK_VARIABLE_RULE.getPriorityValue();
                break;
            default :
                regularPriority = 0;
                break;
        }        
        
        return regularPriority;
    }
}
