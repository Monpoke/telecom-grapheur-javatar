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

	private TelecomGrapheurModele modele;
	private TelecomGrapheurControleur controleur;

	public TelecomGrapheurVue (FenetreContener fenetre, TelecomGrapheurModele telecomGrapheurModele, TelecomGrapheurControleur telecomGrapheurControleur) {
		this.fenetre = fenetre;
		this.controleur = telecomGrapheurControleur;
		this.modele = telecomGrapheurModele;
		this.modele.addObserver(this);
		this.modele.getOrigin().addObserver(this);
		this.modele.getAxeModeleX().addObserver(this);
		this.modele.getAxeModeleY().addObserver(this);
		this.modele.getBornes().addObserver(this);
		this.addMouseListener(telecomGrapheurControleur);
		this.addMouseMotionListener(telecomGrapheurControleur);
		this.setSize(new Dimension(this.fenetre.getWidth(),this.fenetre.getHeight()));
	}

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, this.fenetre.getWidth(), this.fenetre.getHeight()); //efface tout	
		this.drawAxeXY(g);
	}

	public void drawAxeXY(Graphics g){
		g.drawLine((int) this.modele.getAxeModeleX().getBorneXLeft(), (int) this.modele.getOrigin().getY(), (int) this.modele.getAxeModeleX().getBorneXRight(), (int) this.modele.getOrigin().getY()); // Axe horizontale
		g.drawLine((int) this.modele.getOrigin().getX(), (int) this.modele.getAxeModeleY().getBorneYtop(), (int) this.modele.getOrigin().getX(), (int) this.modele.getAxeModeleY().getBorneYDown()); // Axe verticale
		this.drawEchelonX(g);
		this.drawEchelonY(g);
	}

	public void drawEchelonX(Graphics g){
		int taille = this.modele.getAxeModeleX().getTailleCase()/this.modele.getAxeModeleX().getCoeffZoom(); // 80 pixels par default
		for(int i = this.modele.getBornes().getBorneXLeft(); i<this.modele.getBornes().getBorneXRight();i++){
			g.drawLine((int) this.modele.getOrigin().getX()+i*taille, (int) this.modele.getOrigin().getY()-10, (int) this.modele.getOrigin().getX()+i*taille, (int) this.modele.getOrigin().getY()+10);
			if(i!=0){
				g.drawString(""+i, (int) this.modele.getOrigin().getX()+i*taille-3, (int) this.modele.getOrigin().getY()+ 30);
			}
		}
	}

	public void drawEchelonY(Graphics g){
		int taille = this.modele.getAxeModeleY().getTailleCase()/this.modele.getAxeModeleY().getCoeffZoom(); // 80 pixels par default
		for(int i = this.modele.getBornes().getBorneYTop(); i<=this.modele.getBornes().getBorneYDown();i++){
			g.drawLine((int) this.modele.getOrigin().getX()-10, (int) this.modele.getOrigin().getY()+i*taille,(int) this.modele.getOrigin().getX()+10, (int) this.modele.getOrigin().getY()+i*taille);
			if(i!=0){
				g.drawString(""+i*(-1), (int) this.modele.getOrigin().getX()+ 20, (int) this.modele.getOrigin().getY()+i*taille); // *-1 car invers� (haut les plus, bas les -)
			}
		}
	}
	
	public void drawQuadrillageVerticale(Graphics g){
		for(int i = this.modele.getBornes().getBorneXLeft(); i <this.modele.getBornes().getBorneXRight();i++){
			
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
