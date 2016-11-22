package modele;

import java.util.Observable;

public class PointModele extends Observable{

	private double x,y;
	
	public PointModele() {

	}
	
	public PointModele(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double d) {
		this.x = d;
		setChanged ();
		notifyObservers (d);
	}
	
	public void setY(double y) {
		this.y = y;
		setChanged ();
		notifyObservers (y);
	}
}
