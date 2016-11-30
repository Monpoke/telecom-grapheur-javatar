package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sun.util.logging.resources.logging;
import modele.PointModele;
import modele.TelecomGrapheurModele;
import controleur.TelecomGrapheurControleur;

public class TelecomGrapheurVue extends JPanel implements Observer{

	private FenetreContener fenetre; //Frame qui contient le jeu

	private TelecomGrapheurModele modele;
	private TelecomGrapheurControleur controleur;
	private JTextField text = new JTextField();

	public TelecomGrapheurVue (FenetreContener fenetre, TelecomGrapheurModele telecomGrapheurModele, TelecomGrapheurControleur telecomGrapheurControleur) {
		this.fenetre = fenetre;
		this.controleur = telecomGrapheurControleur;
		this.modele = telecomGrapheurModele;
		this.ajoutObserver();
		this.fenetre.addControllerOnJTextField(telecomGrapheurControleur);
		this.fenetre.addControllerOnBtn(telecomGrapheurControleur);
		this.fenetre.addMouseListener(telecomGrapheurControleur);
		this.fenetre.addMouseMotionListener(telecomGrapheurControleur);
		this.fenetre.addMouseWheelListener(telecomGrapheurControleur);
		this.setSize(new Dimension(this.fenetre.getWidth(),this.fenetre.getHeight()-60));
		this.text.addActionListener(telecomGrapheurControleur); 
	}

	/**
	 * Ajout les observers aux modeles
	 */
	public void ajoutObserver(){
		this.modele.addObserver(this);
		this.modele.getOrigin().addObserver(this);
		this.modele.getAxeModeleX().addObserver(this);
		this.modele.getAxeModeleY().addObserver(this);
		this.modele.getBornes().addObserver(this);
		this.modele.getCourbe().addObserver(this);
		this.modele.getCursor().addObserver(this);
		this.modele.getListesPoints().addObserver(this);
	}

	/**
	 * dessine sur la fenetre
	 */
	@Override
	public void paint(Graphics g) {
		if(this.modele.getDialog()){
			this.modele.setDialog(false);
			try {
				JOptionPane.showMessageDialog(this,
						"- Le zoom. Grâce à la molette, il est possible de zoomer ainsi que de dezoomer. La grille s’adaptera pour permettre une meilleure visibilité. La grille est bornée de 0.001 à 1000. \n" +
	"- Le déplacement du graphique. En restant appuyer sur le clic gauche et en déplaçant la souris, cela permet de voir d’autre partie du graphique. Ccela est aussi possible avec le clic droit. \n" +
	"- L’ajout de points. Il est possible, en double cliquant au même endroit, de créer un point sur le graphique. Son nom ainsi que ses coordonnées s’affichent à côté du point.\n" +
	"- Le retrait du point. En faisant un clic droit sur un point créé, le point disparaitra. Cela renomme les autres points pour ne pas avoir de conflit de noms. \n " +
	"- Le curseur. A tout moment, si une courbe est dessinée, un curseur suit la souris sur la courbe pour avoir plus de précision sur un point de la courbe. Ces coordonnées s’affichent en haut à gauche.\n" +
	"- Le retrait des courbes. Il est possible à l'aide d'un clic droit, de supprimer les courbes");
			} catch (NullPointerException e) {
				
			}

		}
		g.clearRect(0, 0, this.fenetre.getWidth(), this.fenetre.getHeight()); //efface tout	
		this.drawQuadrillage(g);
		this.drawAxeXY(g);
		this.drawFunction(g);
		if(!this.modele.getCourbe().getListePoints().isEmpty()){
			this.drawCursor(g);
		}
		//this.modele.createCourbe();
		this.drawPoints(g);
	}

	/**
	 * dessine le quadrillage en gris clair
	 * @param g
	 */
	public void drawQuadrillage(Graphics g){
		g.setColor(Color.GRAY.brighter());
		this.drawQuadrillageVerticale(g);
		this.drawQuadrillageHorizontale(g);
		g.setColor(Color.BLACK);
	}

	/**
	 * dessine les axes X et Y
	 * @param g
	 */
	public void drawAxeXY(Graphics g){
		g.drawLine((int) this.modele.getAxeModeleX().getBorneXLeft(), (int) this.modele.getOrigin().getY(), (int) this.modele.getAxeModeleX().getBorneXRight(), (int) this.modele.getOrigin().getY()); // Axe horizontale
		g.drawLine((int) this.modele.getOrigin().getX(), (int) this.modele.getAxeModeleY().getBorneYtop(), (int) this.modele.getOrigin().getX(), (int) this.modele.getAxeModeleY().getBorneYDown()); // Axe verticale
		this.drawEchelonX(g);
		this.drawEchelonY(g);
	}

	/**
	 * dessine les échelons pour l'axe des X
	 * @param g
	 */
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

	/**
	 * dessine les échelons pour l'axe des Y
	 * @param g
	 */
	public void drawEchelonY(Graphics g){
		for(int i = this.modele.getBornes().getBorneYTop(); i<=this.modele.getBornes().getBorneYDown();i++){
			g.drawLine((int) this.modele.getOrigin().getX()-10, (int) this.modele.getOrigin().getY()+i*this.modele.getAxeModeleY().getTailleCase(),(int) this.modele.getOrigin().getX()+10, (int) this.modele.getOrigin().getY()+i*this.modele.getAxeModeleY().getTailleCase());
			if(i!=0){
				g.drawString(""+i*(-1)*this.modele.getAxeModeleY().getPas(), (int) this.modele.getOrigin().getX()+ 20, (int) this.modele.getOrigin().getY()+i*this.modele.getAxeModeleY().getTailleCase()); // *-1 car inversé (haut les plus, bas les -)
			}
		}
	}

	/**
	 * Dessine les axes verticaux
	 * @param g
	 */
	public void drawQuadrillageVerticale(Graphics g){
		for(int i = this.modele.getBornes().getBorneXLeft(); i <this.modele.getBornes().getBorneXRight();i++){
			g.drawLine((int) (this.modele.getOrigin().getX()+i*this.modele.getAxeModeleX().getTailleCase()), this.modele.getBornes().getBorneYTop(), (int) (this.modele.getOrigin().getX()+i*this.modele.getAxeModeleX().getTailleCase()), this.modele.getBornes().getBorneYDown());
		}
	}

	/**
	 * dessine les axes horizontaux
	 * @param g
	 */
	public void drawQuadrillageHorizontale(Graphics g){
		for(int i = this.modele.getBornes().getBorneYTop(); i <this.modele.getBornes().getBorneYDown();i++){
			g.drawLine(this.modele.getAxeModeleX().getBorneXLeft(), (int) (this.modele.getOrigin().getY()+i*this.modele.getAxeModeleY().getTailleCase()), this.modele.getAxeModeleX().getBorneXRight(), (int) (this.modele.getOrigin().getY()+i*this.modele.getAxeModeleY().getTailleCase()));
		}
	}

	/**
	 * A chaque fois qu'une donnée change dans le modele, cette méthode est appelée. Elle redessine avec les nouvelles valeurs
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}

	/**
	 * Dessine la fonction
	 * @param g
	 */
	public void drawFunction(Graphics g){
		PointModele pPrec = null;
		for (PointModele point : this.modele.getCourbe().getListePoints()) {
			g.fillOval((int) point.getX()-3,(int) point.getY()-3,6,6);
			if(pPrec!=null){
				if(pPrec.getX()+1==point.getX()){
				g.drawLine((int) pPrec.getX(),(int) pPrec.getY(),(int) point.getX(), (int) point.getY());
				}
			}
			pPrec = point;
		}
	}

	/**
	 * dessine le curseur
	 * @param g
	 */
	public void drawCursor(Graphics g){
		g.drawLine((int) this.modele.getCursor().getX()-10, (int) this.modele.getCursor().getY(), (int) this.modele.getCursor().getX()+10, (int) this.modele.getCursor().getY()); // dessine l'axe horizontale
		g.drawLine((int) this.modele.getCursor().getX(), (int) this.modele.getCursor().getY()-10, (int) this.modele.getCursor().getX(), (int) this.modele.getCursor().getY()+10); // dessine l'axe verticale

		double x = ((this.modele.getCursor().getX()-this.modele.getOrigin().getX())/this.modele.getAxeModeleX().getTailleCase())*this.modele.getAxeModeleX().getPas(); //permet de changer le x du graph en sa valeur sur le graphique
		double y = -((this.modele.getCursor().getY()-this.modele.getOrigin().getY())/this.modele.getAxeModeleY().getTailleCase())*this.modele.getAxeModeleY().getPas(); //permet de changer le x du graph en sa valeur sur le graphique
		DecimalFormat df = new DecimalFormat ( ) ; 
		df.setMaximumFractionDigits ( 3 ) ; // formate pour n'afficher que 3 chiffre après la virgule
		g.drawString("Coordonnées ( " + df.format(x) + " , " + df.format(y) + " ) ", 50, 50);
	}

	/**
	 * dessine l'ensemble de points créé par l'utilisateur
	 * @param g
	 */
	public void drawPoints(Graphics g){
		int i = 65;
		for (PointModele point : this.modele.getListesPoints().getListePoints()) {
			if(point.getY()>0)
			{
				g.fillOval((int) point.getX()-3, (int) point.getY()-3, 6, 6); //dessine le point
				double x = ((point.getX()-this.modele.getOrigin().getX())/this.modele.getAxeModeleX().getTailleCase())*this.modele.getAxeModeleX().getPas(); //permet de changer le x du graph en sa valeur sur le graphique
				double y = -((point.getY()-this.modele.getOrigin().getY())/this.modele.getAxeModeleY().getTailleCase())*this.modele.getAxeModeleY().getPas(); //permet de changer le y du graph en sa valeur sur le graphique
				DecimalFormat df = new DecimalFormat ( ) ; 
				df.setMaximumFractionDigits ( 2 ) ; // permet de limiter à 2 chiffre après la virgule
				g.drawString("( " + df.format(x) + " , " + df.format(y) + " ) ", (int) point.getX()+10, (int) point.getY()-5); //dessine les coordonnées
				g.drawString(""+(char) i, (int) point.getX()+2,(int) point.getY()-5); //dessine sa lettre associée
				i++; //permet de passer à la lettre d'après
			}
		}
	}

	public FenetreContener getFenetre() {
		return fenetre;
	}
}
