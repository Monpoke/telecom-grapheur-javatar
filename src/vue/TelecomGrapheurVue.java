package vue;

import java.awt.Color;
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
		this.addMouseWheelListener(telecomGrapheurControleur);
		this.setSize(new Dimension(this.fenetre.getWidth(),this.fenetre.getHeight()));
	}

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, this.fenetre.getWidth(), this.fenetre.getHeight()); //efface tout	
		this.drawQuadrillage(g);
		this.drawAxeXY(g);
		drawFunction(g);
	}

	public void drawQuadrillage(Graphics g){
		g.setColor(Color.GRAY.brighter());
		this.drawQuadrillageVerticale(g);
		this.drawQuadrillageHorizontale(g);
		g.setColor(Color.BLACK);
	}
	
	public void drawAxeXY(Graphics g){
		g.drawLine((int) this.modele.getAxeModeleX().getBorneXLeft(), (int) this.modele.getOrigin().getY(), (int) this.modele.getAxeModeleX().getBorneXRight(), (int) this.modele.getOrigin().getY()); // Axe horizontale
		g.drawLine((int) this.modele.getOrigin().getX(), (int) this.modele.getAxeModeleY().getBorneYtop(), (int) this.modele.getOrigin().getX(), (int) this.modele.getAxeModeleY().getBorneYDown()); // Axe verticale
		this.drawEchelonX(g);
		this.drawEchelonY(g);
	}

	public void drawEchelonX(Graphics g){
		for(int i = this.modele.getBornes().getBorneXLeft(); i<this.modele.getBornes().getBorneXRight();i++){
			g.drawLine((int) this.modele.getOrigin().getX()+i*this.modele.getAxeModeleX().getTailleCase(), (int) this.modele.getOrigin().getY()-10, (int) this.modele.getOrigin().getX()+i*this.modele.getAxeModeleX().getTailleCase(), (int) this.modele.getOrigin().getY()+10);
			if(i!=0){
				g.drawString(""+i*this.modele.getAxeModeleX().getPas(), (int) this.modele.getOrigin().getX()+i*this.modele.getAxeModeleX().getTailleCase()-3, (int) this.modele.getOrigin().getY()+ 30);
			}
		}
	}

	public void drawEchelonY(Graphics g){
		for(int i = this.modele.getBornes().getBorneYTop(); i<=this.modele.getBornes().getBorneYDown();i++){
			g.drawLine((int) this.modele.getOrigin().getX()-10, (int) this.modele.getOrigin().getY()+i*this.modele.getAxeModeleY().getTailleCase(),(int) this.modele.getOrigin().getX()+10, (int) this.modele.getOrigin().getY()+i*this.modele.getAxeModeleY().getTailleCase());
			if(i!=0){
				g.drawString(""+i*(-1)*this.modele.getAxeModeleY().getPas(), (int) this.modele.getOrigin().getX()+ 20, (int) this.modele.getOrigin().getY()+i*this.modele.getAxeModeleY().getTailleCase()); // *-1 car inversé (haut les plus, bas les -)
			}
		}
	}
	
	public void drawQuadrillageVerticale(Graphics g){
		for(int i = this.modele.getBornes().getBorneXLeft(); i <this.modele.getBornes().getBorneXRight();i++){
			g.drawLine((int) (this.modele.getOrigin().getX()+i*this.modele.getAxeModeleX().getTailleCase()), this.modele.getBornes().getBorneYTop(), (int) (this.modele.getOrigin().getX()+i*this.modele.getAxeModeleX().getTailleCase()), this.modele.getBornes().getBorneYDown());
		}
	}
	
	public void drawQuadrillageHorizontale(Graphics g){
		for(int i = this.modele.getBornes().getBorneYTop(); i <this.modele.getBornes().getBorneYDown();i++){
			g.drawLine(this.modele.getAxeModeleX().getBorneXLeft(), (int) (this.modele.getOrigin().getY()+i*this.modele.getAxeModeleY().getTailleCase()), this.modele.getAxeModeleX().getBorneXRight(), (int) (this.modele.getOrigin().getY()+i*this.modele.getAxeModeleY().getTailleCase()));
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
	
//	public void drawFunction(Graphics g){
//		// fonction x=y
//		for(int i = this.modele.getAxeModeleX().getBorneXLeft(); i < this.modele.getAxeModeleX().getBorneXRight();i++){
//			g.drawLine((int) (i*this.modele.getAxeModeleX().getPas()+this.modele.getOrigin().getX()), (int) ((-i*this.modele.getAxeModeleY().getTailleCase())/this.modele.getAxeModeleY().getPas()+ this.modele.getOrigin().getY()), (int) (i*this.modele.getAxeModeleX().getPas()+this.modele.getOrigin().getX()), (int) ((-i*this.modele.getAxeModeleY().getTailleCase())/this.modele.getAxeModeleY().getPas()+ this.modele.getOrigin().getY())); // - car y vers le bas
//		}
//	}
	
	public void drawFunction(Graphics g){
		// fonction x=y
		for(int i = this.modele.getBornes().getBorneXLeft(); i < this.modele.getBornes().getBorneXRight();i++){
			System.out.println("x = " + i + ", y = " + -i);
			g.drawLine((int) (i+this.modele.getOrigin().getX()), (int) (-Math.cos(i/(this.modele.getAxeModeleY().getTailleCase()/this.modele.getAxeModeleY().getPas()))*this.modele.getAxeModeleY().getTailleCase()/this.modele.getAxeModeleY().getPas()+ this.modele.getOrigin().getY()), (int) (i+this.modele.getOrigin().getX()), (int) (-Math.cos(i/(this.modele.getAxeModeleY().getTailleCase()/this.modele.getAxeModeleY().getPas()))*this.modele.getAxeModeleY().getTailleCase()/this.modele.getAxeModeleY().getPas()+ this.modele.getOrigin().getY())); // - car y vers le bas
		}
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
