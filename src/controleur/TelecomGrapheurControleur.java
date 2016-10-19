package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import modele.PointModele;
import modele.TelecomGrapheurModele;

public class TelecomGrapheurControleur implements MouseListener, MouseMotionListener{

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
			this.modele.setClique(false);
		}
		this.modele.getOrigin().setX(e.getX()+this.decalageX);
		this.modele.getOrigin().setY(e.getY()+this.decalageY);
		//System.out.println("x = " + this.modele.getOrigin().getX() + ", y = "+ this.modele.getOrigin().getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
