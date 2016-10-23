package modele;

import java.util.Observable;

public class AxeModele extends Observable{

	public int borneXLeft, borneXRight, borneYtop, borneYDown;	
	private double coeffZoom; 
	private int tailleCase;
	private int pas;
	
	public AxeModele() {
	}
	
	public int getPas() {
		return pas;
	}
	
	public void setPas(int pas) {
		this.pas = pas;
		setChanged ();
		notifyObservers ();
	}
	
	public int getTailleCase() {
		return tailleCase;
	}
	
	public void setTailleCase(int tailleCase) {
		this.tailleCase = tailleCase;
		setChanged ();
		notifyObservers ();
	}
	
	public double getCoeffZoom() {
		return coeffZoom;
	}
	
	public void setCoeffZoom(double coeffZoom) {
		this.coeffZoom = coeffZoom;
		setChanged ();
		notifyObservers ();
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
		setChanged ();
		notifyObservers ();
	}
	
	public void setBorneXRight(int borneXRight) {
		this.borneXRight = borneXRight;
		setChanged ();
		notifyObservers ();
	}
	
	public void setBorneYDown(int borneYDown) {
		this.borneYDown = borneYDown;
		setChanged ();
		notifyObservers ();
	}
	
	public void setBorneYtop(int borneYtop) {
		this.borneYtop = borneYtop;
		setChanged ();
		notifyObservers ();
	}
}
