/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest;

import graphtest.evaluator.Evaluator;
import graphtest.evaluator.Variable;
import graphtest.exceptions.ParsingException;
import graphtest.parsed.ParsedToken;
import graphtest.treeverter.TreeConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author A643012
 */
public class GraphTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);

        String math = "";

        do {
            System.out.println("Phrase: ");
            math = s.nextLine();
        } while (math.length() <= 0);

        System.out.println("Lancement du parsage...");

        try {
            System.out.println("-----------\nPARSER [" + math + "]\n-----------\n");

            // FIRST STEP
            Parser parser = new Parser(math);

            // SECOND STEP
            ArrayList<ParsedToken> parsedTokenList = parser.getParsedTokenList();
            displayList(parsedTokenList);

            System.out.println("-----------\nCONVERTER [" + math + "]\n-----------\n");

            // CORRECT SYNTAX
            // TREE
            TreeConverter converter = new TreeConverter(parsedTokenList);
            TreeNode root = converter.getRoot();
            displayList(converter.getParsedTokenList());

            if (root == null) {
                System.out.println("result null");
                System.exit(1);
            }
            
            System.out.println("OUT RESULT:\n");
            
            BTreePrinter bTreePrinter = new BTreePrinter();
            bTreePrinter.printNode(root);
    
            
            
            

            System.out.println("-----------\nEVALUATOR [" + math + "]\n-----------\n");
            
            // EVALUATOR
            Evaluator evaluator = new Evaluator(root);
            evaluator.addVariable(new Variable("x", 2));
          //  System.out.println("Resultat=" + evaluator.evaluate());

        } catch (ParsingException ex) {
            Logger.getLogger(GraphTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GraphTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void displayList(ArrayList<ParsedToken> parsedTokenList) {
        for (ParsedToken parsedToken : parsedTokenList) {
            System.out.print(parsedToken.toString() + " (" + parsedToken.getPriority() + ") ");
        }

        System.out.println("");
    }


    private static class BTreePrinter {

        public <T extends Comparable<?>> void printNode(TreeNode root) {
            int maxLevel = maxLevel(root);

            printNodeInternal(Collections.singletonList(root), 1, maxLevel);
        }

        private <T extends Comparable<?>> void printNodeInternal(List<TreeNode> nodes, int level, int maxLevel) {
            if (nodes.isEmpty() || isAllElementsNull(nodes)) {
                return;
            }

            int floor = maxLevel - level;
            int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(2, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

            printWhitespaces(firstSpaces);

            List<TreeNode> newNodes = new ArrayList<TreeNode>();
            for (TreeNode node : nodes) {
                if (node != null) {
                    System.out.print(node.getToken().toString());
                    newNodes.add(node.left);
                    newNodes.add(node.right);
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print("     ");
                }

                printWhitespaces(betweenSpaces);
            }
            System.out.println("");

            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }

                    if (nodes.get(j).left != null) {
                        System.out.print("/");
                    } else {
                        printWhitespaces(3);
                    }

                    printWhitespaces(i + i - 1);

                    if (nodes.get(j).right != null) {
                        System.out.print("\\");
                    } else {
                        printWhitespaces(1);
                    }

                    printWhitespaces(endgeLines + endgeLines - i);
                }

                System.out.println("");
            }

            printNodeInternal(newNodes, level + 1, maxLevel);
        }

        private void printWhitespaces(int count) {
            for (int i = 0; i < count; i++) {
                System.out.print(" ");
            }
        }

        private int maxLevel(TreeNode node) {
            if (node == null) {
                return 0;
            }

            return Math.max(maxLevel(node.getLeft()), maxLevel(node.getRight())) + 1;
        }

        private <T> boolean isAllElementsNull(List<T> list) {
            for (Object object : list) {
                if (object != null) {
                    return false;
                }
            }

            return true;
        }

    }
}
