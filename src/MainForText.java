
import modele.TelecomGrapheurModele;
import controleur.TelecomGrapheurControleur;
import graphtest.MathParser;
import graphtest.Parser;
import graphtest.TreeNode;
import graphtest.evaluator.Evaluator;
import graphtest.evaluator.Variable;
import graphtest.exceptions.ParsingException;
import graphtest.parsed.ParsedToken;
import graphtest.treeverter.TreeConverter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import vue.FenetreContener;
import vue.TelecomGrapheurVue;

public class MainForText {

    public static void main(String[] args) {

        String math = "cos(x)+2";

        Parser parser;
        try {
            parser = new Parser(math);

            ArrayList<ParsedToken> parsedTokenList = parser.getParsedTokenList();

            TreeConverter converter = new TreeConverter(parsedTokenList);
            TreeNode root = converter.getRoot();
            
            Evaluator eval = new Evaluator(root);
            eval.resetScope();
            eval.addVariable(new Variable("x", 2));          
            
            System.out.println("RESULT = " + eval.evaluate());
            
        } catch (ParsingException ex) {
            Logger.getLogger(MainForText.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MainForText.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
