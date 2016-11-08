package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import modele.Constantes;
import modele.PointModele;
import modele.TelecomGrapheurModele;

public class TelecomGrapheurControleur implements MouseListener, MouseMotionListener, MouseWheelListener, ActionListener{

	private TelecomGrapheurModele modele;
	private int decalageX, decalageY;
	
	public TelecomGrapheurControleur(TelecomGrapheurModele modele) {
		this.modele = modele;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {


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
		this.modele.setClique(true);
		int i = 0;
		boolean exist = false;
		for (PointModele p : this.modele.getPoints()) {
			if(e.getX()==p.getX() && e.getY()==-p.getY()){
				this.modele.getPoints().remove(p);
				this.modele.getPoints().add(new PointModele(e.getX(),e.getY()));
				exist = true;
			}
			i++;
		}
		if(!exist){
			this.modele.getPoints().add(new PointModele(e.getX(), -e.getY()));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.modele.setClique(false);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.modele.isClique()){
			this.decalageX = (int) this.modele.getOrigin().getX()-e.getX();
			this.decalageY = (int) this.modele.getOrigin().getY()-e.getY();
			if(this.decalageX>0){
			this.modele.getBornes().setBorneXLeft(this.modele.getBornes().getBorneXLeft()-this.decalageX);
			}
			else{
			this.modele.getBornes().setBorneXRight(this.modele.getBornes().getBorneXRight()-this.decalageX);
			}
			this.modele.setClique(false);
		}
		this.modele.getOrigin().setX(e.getX()+this.decalageX);
		this.modele.getOrigin().setY(e.getY()+this.decalageY);
		this.modele.getCursor().setX(e.getX());
		for (PointModele p : this.modele.getCourbe().getListePoints()) {
			if(p.getX()==e.getX()){
				this.modele.getCursor().setY(p.getY());
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.modele.getCursor().setX(e.getX());
		for (PointModele p : this.modele.getCourbe().getListePoints()) {
			if(p.getX()==e.getX()){
				this.modele.getCursor().setY(p.getY());
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation(); 
        if (notches < 0) { 
             //ici tu scroll ver le haut 
        	this.modele.getAxeModeleX().setCoeffZoom(this.modele.getAxeModeleX().getCoeffZoom()+0.1*this.modele.getAxeModeleX().getCoeffZoom());
        	this.modele.getAxeModeleY().setCoeffZoom(this.modele.getAxeModeleY().getCoeffZoom()+0.1*this.modele.getAxeModeleY().getCoeffZoom());
        } else { 
             //ici tu scroll vers le bas 
        	this.modele.getAxeModeleX().setCoeffZoom(this.modele.getAxeModeleX().getCoeffZoom()-0.1*this.modele.getAxeModeleX().getCoeffZoom());
        	this.modele.getAxeModeleY().setCoeffZoom(this.modele.getAxeModeleY().getCoeffZoom()-0.1*this.modele.getAxeModeleY().getCoeffZoom());
        }
        int tailleCaseX = (int) (Constantes.tailleCaseDefault*this.modele.getAxeModeleX().getPas()*this.modele.getAxeModeleX().getCoeffZoom());
        int tailleCaseY = (int) (Constantes.tailleCaseDefault*this.modele.getAxeModeleY().getPas()*this.modele.getAxeModeleY().getCoeffZoom());
        if(tailleCaseX<Constantes.tailleCaseDefault/2){
        	this.modele.getAxeModeleX().setPas(this.modele.getAxeModeleX().getPas()*2);
        	tailleCaseX = Constantes.tailleCaseDefault;
        }
        else if(tailleCaseX>Constantes.tailleCaseDefault*2){
        	this.modele.getAxeModeleX().setPas(this.modele.getAxeModeleX().getPas()/2);
        	tailleCaseX = Constantes.tailleCaseDefault;
        }
        if(tailleCaseY<Constantes.tailleCaseDefault/2){
        	this.modele.getAxeModeleY().setPas(this.modele.getAxeModeleY().getPas()*2);
        	tailleCaseY = Constantes.tailleCaseDefault;
        }
        else if(tailleCaseY>Constantes.tailleCaseDefault*2){
        	this.modele.getAxeModeleY().setPas(this.modele.getAxeModeleY().getPas()/2);
        	tailleCaseY = Constantes.tailleCaseDefault;
        }
        this.modele.getAxeModeleX().setTailleCase(tailleCaseX);
    	this.modele.getAxeModeleY().setTailleCase(tailleCaseY);
    	this.modele.createCourbe();
    	this.modele.getCursor().setX(e.getX());
		for (PointModele p : this.modele.getCourbe().getListePoints()) {
			if(p.getX()==e.getX()){
				this.modele.getCursor().setY(p.getY());
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getID()==1001){ // le jtextfield
			//Write ici l'appel aux autres parti
			System.out.println("test");
		}
	}

}
