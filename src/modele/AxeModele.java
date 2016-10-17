package modele;

import java.util.Observable;

public class AxeModele extends Observable{

	public int borneXLeft, borneXRight, borneYtop, borneYDown;	
	private int coeffZoom; 
	private int tailleCase;
	
	public AxeModele() {
	}
	
	public int getTailleCase() {
		return tailleCase;
	}
	
	public void setTailleCase(int tailleCase) {
		this.tailleCase = tailleCase;
	}
	
	public int getCoeffZoom() {
		return coeffZoom;
	}
	
	public void setCoeffZoom(int coeffZoom) {
		this.coeffZoom = coeffZoom;
	}
	
	public int getBorneXLeft() {
		return borneXLeft;
	}
	
	public int getBorneXRight() {
		return borneXRight;
	}
	
	public int getBorneYDown() {
		return borneYDown;
	}
	
	public int getBorneYtop() {
		return borneYtop;
	}
	
	public void setBorneXLeft(int borneXLeft) {
		this.borneXLeft = borneXLeft;
	}
	
	public void setBorneXRight(int borneXRight) {
		this.borneXRight = borneXRight;
	}
	
	public void setBorneYDown(int borneYDown) {
		this.borneYDown = borneYDown;
	}
	
	public void setBorneYtop(int borneYtop) {
		this.borneYtop = borneYtop;
	}
}
