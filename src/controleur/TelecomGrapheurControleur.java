package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.lang.model.type.DeclaredType;

import modele.Constantes;
import modele.PointModele;
import modele.TelecomGrapheurModele;

public class TelecomGrapheurControleur implements MouseListener, MouseMotionListener, MouseWheelListener, ActionListener{

	private TelecomGrapheurModele modele;
	private int decalageXOrigin, decalageYOrigin, decalageXPoints, decalageYPoints;

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
		this.modele.setClique(true);
		int i = 0;
		boolean exist = false;
		for (PointModele p : this.modele.getListesPoints().getListePoints()) {
			//System.out.println("e.getY() : " +e.getY());
			//System.out.println("p.getY() : " +(int) (p.getY()+100000));
			if(e.getX()==p.getX() && e.getY()==((int) (p.getY()+1000000000))){
				//System.out.println("test");
				this.modele.getListesPoints().getListePoints().remove(p);
				this.modele.getListesPoints().getListePoints().add(new PointModele(e.getX(),e.getY()));
				exist = true;
			}
			i++;
		}
		if(!exist){
			this.modele.getListesPoints().getListePoints().add(new PointModele(e.getX(), e.getY()-1000000000));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.modele.setClique(false);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		ArrayList<PointModele> liste = new ArrayList<PointModele>();
		int decalX,decalY;
		for (PointModele p : this.modele.getListesPoints().getListePoints()) {
			decalX = (int) (p.getX()-this.modele.getOrigin().getX());
			decalY = (int) (p.getY()-this.modele.getOrigin().getY());
			liste.add(new PointModele(decalX,decalY));
		}
		if(this.modele.isClique()){
			this.decalageXOrigin = (int) this.modele.getOrigin().getX()-e.getX();
			this.decalageYOrigin = (int) this.modele.getOrigin().getY()-e.getY();
			if(this.decalageXOrigin>0){
				this.modele.getBornes().setBorneXLeft(this.modele.getBornes().getBorneXLeft()-this.decalageXOrigin);
			}
			else{
				this.modele.getBornes().setBorneXRight(this.modele.getBornes().getBorneXRight()-this.decalageXOrigin);
			}
			this.modele.setClique(false);
		}
		this.modele.getOrigin().setX(e.getX()+this.decalageXOrigin);
		this.modele.getOrigin().setY(e.getY()+this.decalageYOrigin);
		this.modele.getCursor().setX(e.getX());
		for (PointModele p : this.modele.getCourbe().getListePoints()) {
			if(p.getX()==e.getX()){
				this.modele.getCursor().setY(p.getY());
			}
		}
		int i = 0;
		for (PointModele p : this.modele.getListesPoints().getListePoints()) {
			p.setX(this.modele.getOrigin().getX()+(liste.get(i).getX()));
			p.setY(this.modele.getOrigin().getY()+(liste.get(i).getY()));
			i++;
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
		if(this.modele.isClique()){
			for (PointModele p : this.modele.getListesPoints().getListePoints()) {
				if(p.getX()!=e.getX() && p.getY()<=-90000){
					this.modele.getListesPoints().getListePoints().remove(p);
				}
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		ArrayList<PointModele> listeTemp = new ArrayList<PointModele>();
		int notches = e.getWheelRotation(); 
		if (notches < 0) { 
			//ici tu scroll ver le haut 
			//if(this.modele.getAxeModeleX().getCoeffZoom()<1000 && this.modele.getAxeModeleY().getCoeffZoom()<1000){
			this.modele.getAxeModeleX().setCoeffZoom(this.modele.getAxeModeleX().getCoeffZoom()+0.1*this.modele.getAxeModeleX().getCoeffZoom());
			this.modele.getAxeModeleY().setCoeffZoom(this.modele.getAxeModeleY().getCoeffZoom()+0.1*this.modele.getAxeModeleY().getCoeffZoom());
			//}
		} else { 
			//if(this.modele.getAxeModeleX().getCoeffZoom()>1000 && this.modele.getAxeModeleY().getCoeffZoom()>1000){
			//ici tu scroll vers le bas 
			this.modele.getAxeModeleX().setCoeffZoom(this.modele.getAxeModeleX().getCoeffZoom()-0.1*this.modele.getAxeModeleX().getCoeffZoom());
			this.modele.getAxeModeleY().setCoeffZoom(this.modele.getAxeModeleY().getCoeffZoom()-0.1*this.modele.getAxeModeleY().getCoeffZoom());
			//}
		}
		for (PointModele p : this.modele.getListesPoints().getListePoints()) {
			double x = ((p.getX()-this.modele.getOrigin().getX())/this.modele.getAxeModeleX().getTailleCase())*this.modele.getAxeModeleX().getPas();
			double y = -((p.getY()-this.modele.getOrigin().getY())/this.modele.getAxeModeleY().getTailleCase())*this.modele.getAxeModeleY().getPas();
			listeTemp.add(new PointModele(x,y));
//			p.setX(p.getX()-7*this.modele.getAxeModeleX().getCoeffZoom()); // soucis ici presque bon
//			p.setY(p.getY()-10*this.modele.getAxeModeleY().getCoeffZoom());
//			p.setX(p.getX()+(this.modele.getAxeModeleX().getTailleCase()-tailleCaseX)); // soucis ici
//			p.setY(p.getY()-10*this.modele.getAxeModeleY().getCoeffZoom());
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
		this.modele.getListesPoints().videListe();
		for (PointModele p : listeTemp) {
			double graphX = p.getX()*this.modele.getAxeModeleX().getTailleCase()/this.modele.getAxeModeleX().getPas()+ this.modele.getOrigin().getX();
			double graphY = -p.getY()*this.modele.getAxeModeleY().getTailleCase()/this.modele.getAxeModeleY().getPas()+ this.modele.getOrigin().getY();
			this.modele.getListesPoints().setPoints(new PointModele(graphX, graphY));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getID()==1001){ // le jtextfield
			//Write ici l'appel aux autres parties
			//System.out.println("test");
		}
	}

}
