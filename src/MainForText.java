
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
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import vue.FenetreContener;
import vue.TelecomGrapheurVue;

public class MainForText {

    public static void main(String[] args) {

        String math = "tan(2x)+pi^2";
        Scanner s = new Scanner(System.in);

        do {
            System.out.println("Entrez une expression : [" + math + "] par défaut");
            math = s.nextLine();
        } while (math.isEmpty());

        Parser parser;
        try {
            parser = new Parser(math);

            ArrayList<ParsedToken> parsedTokenList = parser.getParsedTokenList();

            TreeConverter converter = new TreeConverter(parsedTokenList);
            TreeNode root = converter.getRoot();

            /**
             * Register start time
             */
            long startTime = System.nanoTime();

            Evaluator eval = new Evaluator(root);

            eval.resetScope();
            eval.addVariable(new Variable("x", 4));
            System.out.println(eval.evaluate());

            /**
             * Register last time
             */
            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;
            System.out.println("TOTALTIME =" + totalTime);
            System.out.println(TimeUnit.NANOSECONDS.toMillis(totalTime) + "ms");

        } catch (ParsingException ex) {
            Logger.getLogger(MainForText.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MainForText.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
