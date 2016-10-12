/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest;

import java.util.Scanner;

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
        
        new Parser(math);
        
        
    }

}
