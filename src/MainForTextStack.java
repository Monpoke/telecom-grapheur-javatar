
import graphtest.BTreePrinter;
import graphtest.Parser;
import graphtest.Syntaxor;
import graphtest.TreeNode;
import graphtest.evaluator.Evaluator;
import graphtest.evaluator.EvaluatorV2;
import graphtest.evaluator.StackTreeConverter;
import graphtest.evaluator.Variable;
import graphtest.exceptions.ParsingException;
import graphtest.parsed.ParsedToken;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainForTextStack {

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

            TreeNode root = null;
           
            Syntaxor syntaxor = new Syntaxor(parsedTokenList);
            syntaxor.displayOrderedStack();
            
            StackTreeConverter stc = new StackTreeConverter(syntaxor.getOrderedStack());
            root = stc.getRoot();
            
            BTreePrinter btree = new BTreePrinter();
            btree.printNode(root);
            /**
             * Register start time
             */
            long startTime = System.nanoTime();

            EvaluatorV2 eval = new EvaluatorV2(root);

            eval.resetScope();
            eval.addVariable(new Variable("x", 4));
            System.out.println("result="+eval.evaluateV2());

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
