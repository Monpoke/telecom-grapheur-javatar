package controleur;

import graphtest.MathParser;
import graphtest.evaluator.Evaluator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SwingUtilities;

import modele.Constantes;
import modele.PointModele;
import modele.TelecomGrapheurModele;

public class TelecomGrapheurControleur implements MouseListener, MouseMotionListener, MouseWheelListener, ActionListener {

    private TelecomGrapheurModele modele;
    private int decalageXOrigin, decalageYOrigin, decalageXPoints, decalageYPoints;
    private String fonction;
    private HashMap<String, Evaluator> eval = new HashMap<String, Evaluator>();
    
    public TelecomGrapheurControleur(TelecomGrapheurModele modele) {
        this.modele = modele;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) { // si c'est un clic gauche
            this.modele.setClique(true); 
            boolean exist = false;
            for (PointModele p : this.modele.getListesPoints().getListePoints()) { // On parocurt la liste de point
                if (e.getX() == p.getX() && e.getY() == ((int) (p.getY() + 1000000055))) { // On verifie si on a deja clic à cet endroit
                    this.modele.getListesPoints().getListePoints().remove(p); // si le point est trouvé, on l'enleve 
                    this.modele.getListesPoints().getListePoints().add(new PointModele(e.getX(), e.getY() - 55)); // et on le repositionne au bon endroit
                    exist = true; //On a trouve un point
                }
            }
            if (!exist) { // si aucun point n'a ete trouve
                this.modele.getListesPoints().getListePoints().add(new PointModele(e.getX(), e.getY() - 1000000055)); // On set un point à un endroit invisible
            }
        } else if (SwingUtilities.isRightMouseButton(e)) { // si c'est un clic droit
            for (PointModele p : this.modele.getListesPoints().getListePoints()) { // on parcourt tous les points
                if (e.getX() > p.getX() - 3 && e.getX() < p.getX() + 3 && e.getY() - 55 > p.getY() - 3 && e.getY() - 55 < p.getY() + 3) { // on verifie si le clic est sur un point
                    this.modele.getListesPoints().getListePoints().remove(p); // On le supprime
                    break;
                }
            }
            for (PointModele p : this.modele.getCourbe().getListePoints()) { // On parcourt les points de la courbe
                if (e.getX() > p.getX() - 3 && e.getX() < p.getX() + 3 && e.getY() - 55 > p.getY() - 3 && e.getY() - 55 < p.getY() + 3) { // On verifie si le clic est sur un point
                    this.modele.getCourbe().videListe(); // On efface les courbes
                    this.fonction = null; // On met la fonction a null
                    break;
                }
            }
            this.modele.getCourbe().setFonction(new ArrayList<String>()); // on reinitialise la liste des fonctions
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.modele.setClique(false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ArrayList<PointModele> liste = new ArrayList<PointModele>(); // liste qui contient les déclages des points par rapport à l'origine
        if (this.modele.isClique()) { // si on a fait un clic gauche
            this.decalageXOrigin = (int) this.modele.getOrigin().getX() - e.getX(); // calcul le decalage, par rapport à l'origin, de de la souris en x
            this.decalageYOrigin = (int) this.modele.getOrigin().getY() - e.getY();
            if (this.decalageXOrigin > 0) { // si on a dragg de gauche vers droite
                this.modele.getBornes().setBorneXLeft(this.modele.getBornes().getBorneXLeft() - this.decalageXOrigin); // On met à jour les bornes 
            } else { // si c'est l'inerse
                this.modele.getBornes().setBorneXRight(this.modele.getBornes().getBorneXRight() - this.decalageXOrigin); // On met à jour les bornes
            }
            this.modele.setClique(false); // Maintenant qu'on a calculé le declalage, on ne veut plus repasser dedans jusqu'au prochain clic
        }
        this.modele.getOrigin().setX(e.getX() + this.decalageXOrigin); // On deplace l'origine
        this.modele.getOrigin().setY(e.getY() + this.decalageYOrigin); // On deplace l'origine
        this.modele.getCursor().setX(e.getX()); // // On deplace le curseur
        for (PointModele p : this.modele.getCourbe().getListePoints()) { // On parcourt la liste de points pour mettre le curseur sur un point
            if (p.getX() == e.getX()) {
                this.modele.getCursor().setY(p.getY());
            }
        }
        int i = 0;
        for (PointModele p : this.modele.getListesPoints().getListePoints()) {
            p.setX(this.modele.getOrigin().getX() + (liste.get(i).getX()));
            p.setY(this.modele.getOrigin().getY() + (liste.get(i).getY()));
            i++;
        }
        this.modele.getCourbe().videListe();
        if (fonction != null && !fonction.isEmpty()) {
            Evaluator eval;
            for (String s : this.modele.getCourbe().getFonction()) {
                eval = getEval(s);
                for (int j = this.modele.getBornes().getBorneXLeft(); j < this.modele.getBornes().getBorneXRight(); j++) {
                    double x = (j + this.modele.getOrigin().getX());
                    Double y = MathParser.evaluate(eval, (j / (this.modele.getAxeModeleY().getTailleCase() / this.modele.getAxeModeleY().getPas())));
                    
                    if(y==null){
                        continue;
                    }
                    
                    this.modele.getCourbe().setPoints(new PointModele((int) (x), (int) (-y * this.modele.getAxeModeleY().getTailleCase() / this.modele.getAxeModeleY().getPas() + this.modele.getOrigin().getY())));
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.modele.getCursor().setX(e.getX());
        for (PointModele p : this.modele.getCourbe().getListePoints()) {
            if (p.getX() == e.getX()) {
                this.modele.getCursor().setY(p.getY());
            }
        }
        if (this.modele.isClique()) {
            for (PointModele p : this.modele.getListesPoints().getListePoints()) {
                if (p.getX() != e.getX() && p.getY() <= -1000000000) {
                    this.modele.getListesPoints().getListePoints().remove(p);
                }
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //this.modele.getCourbe().videListe();
        ArrayList<PointModele> listeTemp = new ArrayList<PointModele>();
        int notches = e.getWheelRotation();
        if (notches < 0) {
            //ici tu scroll ver le haut 
            if(this.modele.getAxeModeleX().getPas()>0.001 && this.modele.getAxeModeleY().getPas()>0.001){
            this.modele.getAxeModeleX().setCoeffZoom(this.modele.getAxeModeleX().getCoeffZoom() + 0.1 * this.modele.getAxeModeleX().getCoeffZoom());
            this.modele.getAxeModeleY().setCoeffZoom(this.modele.getAxeModeleY().getCoeffZoom() + 0.1 * this.modele.getAxeModeleY().getCoeffZoom());
            }
        } else {
            if(this.modele.getAxeModeleX().getPas()<1000 && this.modele.getAxeModeleY().getPas()<1000){
            //ici tu scroll vers le bas 
            this.modele.getAxeModeleX().setCoeffZoom(this.modele.getAxeModeleX().getCoeffZoom() - 0.1 * this.modele.getAxeModeleX().getCoeffZoom());
            this.modele.getAxeModeleY().setCoeffZoom(this.modele.getAxeModeleY().getCoeffZoom() - 0.1 * this.modele.getAxeModeleY().getCoeffZoom());
            }
        }
        for (PointModele p : this.modele.getListesPoints().getListePoints()) {
            double x = ((p.getX() - this.modele.getOrigin().getX()) / this.modele.getAxeModeleX().getTailleCase()) * this.modele.getAxeModeleX().getPas();
            double y = -((p.getY() - this.modele.getOrigin().getY()) / this.modele.getAxeModeleY().getTailleCase()) * this.modele.getAxeModeleY().getPas();
            listeTemp.add(new PointModele(x, y));
        }
        int tailleCaseX = (int) (Constantes.tailleCaseDefault * this.modele.getAxeModeleX().getPas() * this.modele.getAxeModeleX().getCoeffZoom());
        int tailleCaseY = (int) (Constantes.tailleCaseDefault * this.modele.getAxeModeleY().getPas() * this.modele.getAxeModeleY().getCoeffZoom());

        if (tailleCaseX < Constantes.tailleCaseDefault / 2) {
            this.modele.getAxeModeleX().setPas(this.modele.getAxeModeleX().getPas() * 2);
            tailleCaseX = Constantes.tailleCaseDefault;
        } else if (tailleCaseX > Constantes.tailleCaseDefault * 2) {
            this.modele.getAxeModeleX().setPas(this.modele.getAxeModeleX().getPas() / 2);
            tailleCaseX = Constantes.tailleCaseDefault;
        }
        if (tailleCaseY < Constantes.tailleCaseDefault / 2) {
            this.modele.getAxeModeleY().setPas(this.modele.getAxeModeleY().getPas() * 2);
            tailleCaseY = Constantes.tailleCaseDefault;
        } else if (tailleCaseY > Constantes.tailleCaseDefault * 2) {
            this.modele.getAxeModeleY().setPas(this.modele.getAxeModeleY().getPas() / 2);
            tailleCaseY = Constantes.tailleCaseDefault;
        }
        this.modele.getAxeModeleX().setTailleCase(tailleCaseX);
        this.modele.getAxeModeleY().setTailleCase(tailleCaseY);
        //this.modele.createCourbe();
        this.modele.getCursor().setX(e.getX());
        this.modele.getCourbe().videListe();
        for (PointModele p : this.modele.getCourbe().getListePoints()) {
            if (p.getX() == e.getX()) {
                this.modele.getCursor().setY(p.getY());
            }
        }
        this.modele.getListesPoints().videListe();
        for (PointModele p : listeTemp) {
            double graphX = p.getX() * this.modele.getAxeModeleX().getTailleCase() / this.modele.getAxeModeleX().getPas() + this.modele.getOrigin().getX();
            double graphY = -p.getY() * this.modele.getAxeModeleY().getTailleCase() / this.modele.getAxeModeleY().getPas() + this.modele.getOrigin().getY();
            this.modele.getListesPoints().setPoints(new PointModele(graphX, graphY));
        }
        if (fonction != null && !fonction.isEmpty()) {
            Evaluator eval = getEval(fonction);

            // error, so skip
            if (eval == null) {
                return;
            }
            for (String s : this.modele.getCourbe().getFonction()) {
                eval = MathParser.getEvaluatorFromMathString(s);
                for (int j = this.modele.getBornes().getBorneXLeft(); j < this.modele.getBornes().getBorneXRight(); j++) {
                    double x = (j + this.modele.getOrigin().getX());
                    Double y =MathParser.evaluate(eval, (j / (this.modele.getAxeModeleY().getTailleCase() / this.modele.getAxeModeleY().getPas())));
                    if(y==null){
                        continue;
                    }
                    this.modele.getCourbe().setPoints(new PointModele((int) (x), (int) (-y * this.modele.getAxeModeleY().getTailleCase() / this.modele.getAxeModeleY().getPas() + this.modele.getOrigin().getY())));
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() != "Aide") { // le jtextfield
            this.fonction = e.getActionCommand();
            //this.modele.getCourbe().videListe();
            Evaluator eval = null;
            try {
            	eval = getEval(e.getActionCommand());
            } catch (Exception e2) {
                // TODO: handle exception
            }
            if (eval == null) {
                return; // skip on error
            }
            for (int i = this.modele.getBornes().getBorneXLeft(); i < this.modele.getBornes().getBorneXRight(); i++) {
                double x = (i + this.modele.getOrigin().getX());
                Double y = MathParser.evaluate(eval, (i / (this.modele.getAxeModeleY().getTailleCase() / this.modele.getAxeModeleY().getPas())));
                if (y == null) {
                    continue;
                }

                this.modele.getCourbe().setPoints(new PointModele((int) (x), (int) (-y * this.modele.getAxeModeleY().getTailleCase() / this.modele.getAxeModeleY().getPas() + this.modele.getOrigin().getY())));
            }
            this.modele.getCourbe().getFonction().add(fonction);
        } else if (e.getActionCommand() == "Aide") {
            this.modele.setDialog(true);
        }
    }
    
    public Evaluator getEval(String s){
    	Evaluator evaluation = MathParser.getEvaluatorFromMathString(s);
    	if(!this.eval.containsKey(s) && evaluation != null){
    		this.eval.put(s, evaluation);
    	}
    	return this.eval.get(s);
    }

}
