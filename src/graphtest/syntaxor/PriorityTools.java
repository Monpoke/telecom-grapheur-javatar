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
 * Tools class which allows on a syntaxical parsed phrase to set up priorities
 * in the operation. Priorities will be used to read the phrase in the mathemical
 * correct order.
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
        // priorityArray = setPriorityParenthesis(lexicalArray); Commented for now resolve setPriorityParenthesis -> Useless now
        priorityArray = lexicalArray;   // to replace by above when debugged -> SetParenthesis not used anymore in the calculation of priority
        
        int coefNbToken = coefficientCalculator(priorityArray);
        long coefTokenRule = coefNbToken;
        boolean reverseDivide = false;  // if there is a divide operator in the expression
        
        for(int i=0;i<priorityArray.size();i++){
            
            if(priorityArray.get(i) instanceof TOK_PAR_OPEN){
                // incrementing prio value on '('
                coefTokenRule *= PriorityRules.TOK_PAR_OPEN_RULE.getPriorityValue();
            }else if(priorityArray.get(i) instanceof TOK_PAR_CLOSE){
                // decrementing prio value on ')'
                coefTokenRule /= PriorityRules.TOK_PAR_CLOSE_RULE.getPriorityValue();
            }
            
            // Adding priority into ParsedToken
            if((priorityArray.get(i) instanceof TOK_OPERATOR_MULTIPLY || priorityArray.get(i) instanceof TOK_OPERATOR_DIVIDE )&& priorityArray.get(i+1) instanceof TOK_PAR_OPEN){
                // Avoiding [(y+x)*(y+x)] multiply's problem priority -> Multiply Token shall have a coef of priority as high as the next parenthesis expression
                priorityArray.get(i).setPriority(i+coefTokenRule*PriorityRules.TOK_OPERATOR_MULTIPLY_RULE.getPriorityValue());  // Multiply and Divide Token Rule shall be the same for mathemical priority reason
            }else if(schemaXOperatorY(priorityArray,i)){
                // When x+y the lexical order prevail -> no use of the token values
                priorityArray.get(i).setPriority(i+coefTokenRule);
            }else{
                // Regular case of calculation
                priorityArray.get(i).setPriority(regularPriorityCalculation(priorityArray,coefTokenRule,i));
            }
            
            // Avoiding multiple check, if there is at least one divide, redo a for on all the priorityArray. If not, allow to avoid
            if(!reverseDivide && priorityArray.get(i) instanceof TOK_OPERATOR_DIVIDE){
                reverseDivide = true;
            }
        }
        
        // Reverse divide priority
        if(reverseDivide){
            for(int i=0;i<priorityArray.size();i++){
                if(priorityArray.get(i) instanceof TOK_OPERATOR_DIVIDE){
                    reverseDividePriority(priorityArray,i);
                }
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
     * Put parenthesis around * and / to ensure their working
     * 
     * @param lexicalArray
     * @return ArrayList<ParsedToken>
     */
    public static ArrayList<ParsedToken> setPriorityParenthesis(ArrayList<ParsedToken> lexicalArray){
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
    }
    
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
     * Reverse the priority of the numerator and divisor
     * of the detected division directly in the list
     * @param priorityArray
     * @param index 
     */
    public static void reverseDividePriority(ArrayList<ParsedToken> priorityArray, int index){
        int numeratorStartIndex = index-1;
        int denominatorEndIndex = index+1;
        int nbTokenDenominator = 1;
        int nbTokenNumerator = 1;
        int saveNbOpen = 0;
        int saveNbClose = 0;
        
        // Classical case x/y swap
        if(!(priorityArray.get(index-1) instanceof TOK_PAR_CLOSE) && !(priorityArray.get(index+1) instanceof TOK_PAR_OPEN)){
            long prioNumerator = priorityArray.get(index-1).getPriority();
            priorityArray.get(index-1).setPriority(priorityArray.get(index+1).getPriority());
            priorityArray.get(index+1).setPriority(prioNumerator);
        }else{
            // Numerator multiple
            if(priorityArray.get(index-1) instanceof TOK_PAR_CLOSE){
                int nbClose = 1;
                int j = index-2;

                while(j>=0 && priorityArray.get(j) instanceof TOK_PAR_CLOSE){
                    nbClose++;
                    j--;
                }

                saveNbClose = nbClose;
                
                while(j>=0 && nbClose>0){
                    numeratorStartIndex = j;

                    if(priorityArray.get(j) instanceof TOK_PAR_OPEN){
                        nbClose--;
                    }
                    if(!(priorityArray.get(j) instanceof TOK_PAR_OPEN) && !(priorityArray.get(0) instanceof TOK_PAR_CLOSE)){
                        nbTokenNumerator++;
                }
                    j--;
                }
            }
            // Denominator multiple
            if(priorityArray.get(index+1) instanceof TOK_PAR_OPEN){
                int nbOpen = 1;
                int k=index+2;

                while(k<priorityArray.size() && priorityArray.get(k) instanceof TOK_PAR_OPEN){
                    nbOpen++;
                    k++;
                }
                
                saveNbOpen = nbOpen;

                while(k<priorityArray.size() && nbOpen>0){
                    denominatorEndIndex = k;

                    if(priorityArray.get(k) instanceof TOK_PAR_CLOSE){
                        nbOpen--;
                    }
                    if(!(priorityArray.get(k) instanceof TOK_PAR_CLOSE) && !(priorityArray.get(k) instanceof TOK_PAR_OPEN)){
                        nbTokenDenominator++;
                    }
                    k++;
                }
            }
            // Regular multiple expression swap : (x+y)/(z+w)
            if(nbTokenDenominator==nbTokenNumerator){
                int indexCp = index;
                System.out.println("regular");
                for(int nsi=numeratorStartIndex+saveNbClose;nsi<index;nsi++){
                    long tmp = priorityArray.get(nsi).getPriority();
                    priorityArray.get(nsi).setPriority(priorityArray.get(indexCp+1).getPriority());
                    priorityArray.get(indexCp+1).setPriority(tmp);
                    indexCp++;
                }
            }else if(nbTokenDenominator>nbTokenNumerator){
                // Denominator or Numerator tokens number different, expression swap like : x/(y+z)
                // Numerator shall have more parenthesis
                int indexEndSwap =1;
                
                // Swap the max we can, then add
                for(int nsi=numeratorStartIndex+saveNbClose, dei=index+1+saveNbOpen;(nsi<index && dei<=denominatorEndIndex);nsi++,dei++){
                    long tmp = priorityArray.get(nsi).getPriority();
                    priorityArray.get(nsi).setPriority(priorityArray.get(dei).getPriority());
                    priorityArray.get(dei).setPriority(tmp);
                    indexEndSwap++;
                }
                
                if(numeratorStartIndex+indexEndSwap>=index){
                    // Add 1 to the rest of the unswap denominator priority
                    for(int i=index+indexEndSwap+1;i<=denominatorEndIndex-saveNbOpen;i++){
                        priorityArray.get(i).setPriority(priorityArray.get(i-1).getPriority()+1);
                    }
                }else{
                    // Add 1 to the rest of the unswap numerator priority
                    for(int i=numeratorStartIndex+indexEndSwap;i<index-saveNbClose;i++){
                        priorityArray.get(i).setPriority(priorityArray.get(i-1).getPriority()+1);
                    }
                }
            }
        }
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
