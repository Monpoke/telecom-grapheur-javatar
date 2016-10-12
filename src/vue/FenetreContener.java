package vue;


import javax.swing.JFrame;

import modele.Constantes;

public class FenetreContener extends JFrame{ // Fenetre dans laquelle est contenu tous le projet

	public FenetreContener() {
		this.setSize(Constantes.width,Constantes.height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}