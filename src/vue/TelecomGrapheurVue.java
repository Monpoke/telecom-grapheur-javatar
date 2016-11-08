package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.TelecomGrapheurControleur;
import modele.Constantes;
import modele.PointModele;
import modele.TelecomGrapheurModele;

public class TelecomGrapheurVue extends JPanel implements Observer{

	private FenetreContener fenetre; //Frame qui contient le jeu

	private TelecomGrapheurModele modele;
	private TelecomGrapheurControleur controleur;
	JTextField text;

	public TelecomGrapheurVue (FenetreContener fenetre, TelecomGrapheurModele telecomGrapheurModele, TelecomGrapheurControleur telecomGrapheurControleur) {
		this.fenetre = fenetre;
		this.controleur = telecomGrapheurControleur;
		this.modele = telecomGrapheurModele;
		this.modele.addObserver(this);
		this.modele.getOrigin().addObserver(this);
		this.modele.getAxeModeleX().addObserver(this);
		this.modele.getAxeModeleY().addObserver(this);
		this.modele.getBornes().addObserver(this);
		this.modele.getCourbe().addObserver(this);
		this.modele.getCursor().addObserver(this);
		this.addMouseListener(telecomGrapheurControleur);
		this.addMouseMotionListener(telecomGrapheurControleur);
		this.addMouseWheelListener(telecomGrapheurControleur);
		this.fenetre.addControllerOnJTextField(telecomGrapheurControleur);
		this.setSize(new Dimension(this.fenetre.getWidth(),this.fenetre.getHeight()));
	}

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, this.fenetre.getWidth(), this.fenetre.getHeight()); //efface tout	
		this.drawQuadrillage(g);
		this.drawAxeXY(g);
		this.drawFunction(g);
		this.drawCursor(g);
		this.modele.createCourbe();
		this.drawPoints(g);
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
				if(i%2==0){
					g.drawString(""+i*this.modele.getAxeModeleX().getPas(), (int) this.modele.getOrigin().getX()+i*this.modele.getAxeModeleX().getTailleCase()-3, (int) this.modele.getOrigin().getY()+ 30);
				}
				else {
					g.drawString(""+i*this.modele.getAxeModeleX().getPas(), (int) this.modele.getOrigin().getX()+i*this.modele.getAxeModeleX().getTailleCase()-3, (int) this.modele.getOrigin().getY()+ 60);
				}
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

	public void drawFunction(Graphics g){
		// fonction y=cos(x)
		for (PointModele point : this.modele.getCourbe().getListePoints()) {
			g.drawLine((int) point.getX(),(int) point.getY(),(int) point.getX(),(int) point.getY());
		}
		this.modele.getCourbe().videListe();
	}

	public void drawCursor(Graphics g){
		g.drawLine((int) this.modele.getCursor().getX()-10, (int) this.modele.getCursor().getY(), (int) this.modele.getCursor().getX()+10, (int) this.modele.getCursor().getY());
		g.drawLine((int) this.modele.getCursor().getX(), (int) this.modele.getCursor().getY()-10, (int) this.modele.getCursor().getX(), (int) this.modele.getCursor().getY()+10);

		double x = ((this.modele.getCursor().getX()-this.modele.getOrigin().getX())/this.modele.getAxeModeleX().getTailleCase())*this.modele.getAxeModeleX().getPas();
		double y = -((this.modele.getCursor().getY()-this.modele.getOrigin().getY())/this.modele.getAxeModeleY().getTailleCase())*this.modele.getAxeModeleY().getPas();
		DecimalFormat df = new DecimalFormat ( ) ; 
		df.setMaximumFractionDigits ( 2 ) ;
		g.drawString("Coordonnées ( " + df.format(x) + " , " + df.format(y) + " ) ", 50, 50);
	}

	public void drawPoints(Graphics g){
		for (PointModele point : this.modele.getPoints()) {
			if(point.getY()>0)
			{
				g.fillOval((int) point.getX()-3, (int) point.getY()-3, 6, 6);
				double x = ((point.getX()-this.modele.getOrigin().getX())/this.modele.getAxeModeleX().getTailleCase())*this.modele.getAxeModeleX().getPas();
				double y = -((point.getY()-this.modele.getOrigin().getY())/this.modele.getAxeModeleY().getTailleCase())*this.modele.getAxeModeleY().getPas();
				DecimalFormat df = new DecimalFormat ( ) ; 
				df.setMaximumFractionDigits ( 2 ) ;
				System.out.println(y);
				g.drawString("( " + df.format(x) + " , " + df.format(y) + " ) ", (int) point.getX()+10, (int) point.getY());
			}
		}
	}
}
