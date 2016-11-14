/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.treeverter;

import graphtest.parsed.ParsedToken;
import graphtest.parsed.TOK_NUMBER;
import graphtest.parsed.TOK_OPERATOR_MULTIPLY;
import graphtest.parsed.TOK_OPERATOR_PLUS;
import graphtest.parsed.TOK_PAR_CLOSE;
import graphtest.parsed.TOK_PAR_OPEN;
import graphtest.parsed.TOK_VARIABLE;
import java.util.ArrayList;

/**
 *
 * @author A643012
 */
class TokenSimplifier {

    static void simplify(ArrayList<ParsedToken> parsedTokenList) {
        
        for(int i=0;i<parsedTokenList.size()-1;i++){
            
            ParsedToken current = parsedTokenList.get(i);
            ParsedToken next = parsedTokenList.get(i+1);
            
            // 2 numbers, add a + operator => needed for this type: 12 - 10  (TOK_NUMBER(12) TOK_NUMBER(-10))
            if(current instanceof TOK_NUMBER && next instanceof TOK_NUMBER){
                parsedTokenList.add(i+1, new TOK_OPERATOR_PLUS());
            }
            
            /*
            In order to add a multiplication for these cases:
                - 12 ( XXXXX )
                - myVar ( XXXXX )
                - ( XXX ) ( XXX )
                - ( XXX) $variable
                - 12x
            */
            
            else if((
                    next instanceof TOK_PAR_OPEN ||
                    next instanceof  TOK_VARIABLE
                    ) 
                    &&
                    (
                    current instanceof TOK_NUMBER ||
                    current instanceof TOK_PAR_CLOSE ||
                    current instanceof TOK_VARIABLE
                    )) {
                
                parsedTokenList.add(i+1, new TOK_OPERATOR_MULTIPLY());
            }
            
            
        }
        
    }
    
}
