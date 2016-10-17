package vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import controleur.TelecomGrapheurControleur;
import modele.Constantes;
import modele.PointModele;
import modele.TelecomGrapheurModele;

public class TelecomGrapheurVue extends JPanel implements Observer{

	private FenetreContener fenetre; //Frame qui contient le jeu
	
	private TelecomGrapheurModele telecomGrapheurModele = new TelecomGrapheurModele();
	private TelecomGrapheurControleur telecomGrapheurControleur = new TelecomGrapheurControleur();

	public TelecomGrapheurVue (FenetreContener fenetre, TelecomGrapheurModele telecomGrapheurModele, TelecomGrapheurControleur telecomGrapheurControleur) {
		this.fenetre = fenetre;
		this.telecomGrapheurControleur = telecomGrapheurControleur;
		this.telecomGrapheurModele = telecomGrapheurModele;
		this.telecomGrapheurModele.addObserver(this);
		this.addMouseListener(telecomGrapheurControleur);
		this.setSize(new Dimension(this.fenetre.getWidth(),this.fenetre.getHeight()));
	}

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, this.fenetre.getWidth(), this.fenetre.getHeight()); //efface tout	
		this.drawAxeXY(g);
	}

	public void drawAxeXY(Graphics g){
		g.drawLine((int) this.telecomGrapheurModele.getAxeModeleX().getBorneXLeft(), (int) this.telecomGrapheurModele.getAxeModeleX().getBorneYtop(), (int) this.telecomGrapheurModele.getAxeModeleX().getBorneXRight(), (int) this.telecomGrapheurModele.getAxeModeleX().getBorneYDown()); // Axe vertical
		g.drawLine((int) this.telecomGrapheurModele.getAxeModeleY().getBorneXLeft(), (int) this.telecomGrapheurModele.getAxeModeleY().getBorneYtop(), (int) this.telecomGrapheurModele.getAxeModeleY().getBorneXRight(), (int) this.telecomGrapheurModele.getAxeModeleY().getBorneYDown()); // Axe horizontale
		this.drawEchelonX(g);
		this.drawEchelonY(g);
	}
	
	public void drawEchelonX(Graphics g){
		int taille = this.telecomGrapheurModele.getAxeModeleX().getTailleCase()/this.telecomGrapheurModele.getAxeModeleX().getCoeffZoom(); // 80 pixels par default
		for(int i = this.telecomGrapheurModele.getBornes().getBorneXLeft(); i<this.telecomGrapheurModele.getBornes().getBorneXRight();i++){
			g.drawLine((int) this.telecomGrapheurModele.getOrigin().getX()+i*taille, (int) this.telecomGrapheurModele.getOrigin().getY()-10, (int) this.telecomGrapheurModele.getOrigin().getX()+i*taille, (int) this.telecomGrapheurModele.getOrigin().getY()+10);
		}
	}
	
	public void drawEchelonY(Graphics g){
		int taille = this.telecomGrapheurModele.getAxeModeleY().getTailleCase()/this.telecomGrapheurModele.getAxeModeleY().getCoeffZoom(); // 80 pixels par default
		for(int i = this.telecomGrapheurModele.getBornes().getBorneYTop(); i<=this.telecomGrapheurModele.getBornes().getBorneYDown();i++){
			g.drawLine((int) this.telecomGrapheurModele.getOrigin().getX()-10, (int) this.telecomGrapheurModele.getOrigin().getY()+i*taille,(int) this.telecomGrapheurModele.getOrigin().getX()+10, (int) this.telecomGrapheurModele.getOrigin().getY()+i*taille);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}

	//public void drawFunction(Graphics g){
		
		/* fonction : sin(x) */
		
		/*this.point = new Point(this.borneXLeft, Math.sin(this.borneXLeft));
		this.coeffX = this.fenetre.getWidth()/(this.borneXRight-this.borneXLeft);
		double r = 0;
		for(double i = this.borneXLeft;i<this.borneXRight;i=i+0.5){
			r = r + 0.5;
			System.out.println("x = " + point.getX() + " : y = " + point.getY());
			
			point.setX(r*this.coeffX+0.5);
			point.setY(Math.sin(r)*this.coeffX+this.origin.getY());
			
			g.drawLine((int) point.getX(), (int) point.getY(), (int) point.getX(), (int) point.getY());
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
}
