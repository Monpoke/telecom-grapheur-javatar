package vue;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TelecomGrapheur extends JPanel{

	private FenetreContener fenetre; //Fenetre Qui contient le jeu
	
	public TelecomGrapheur (FenetreContener fenetre) {
		this.fenetre = fenetre;
		this.setSize(new Dimension(this.fenetre.getWidth(),this.fenetre.getHeight()));
	}
	
	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, this.fenetre.getWidth(), this.fenetre.getHeight()); //efface tout	
		g.drawOval(50, 50, 50, 50);
	}
}
