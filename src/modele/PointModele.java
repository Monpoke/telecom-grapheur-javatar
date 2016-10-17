package modele;

import java.util.Observable;

public class PointModele extends Observable{

	private double x,y;
	
	public PointModele() {

	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double d) {
		this.x = d;
	}
	
	public void setY(double y) {
		this.y = y;
	}
}
