package modele;

import java.util.Observable;

public class BorneGraphiqueModele extends Observable{

	int borneXLeft, borneXRight, borneYTop, borneYDown;
	
	public BorneGraphiqueModele() {
		// TODO Auto-generated constructor stub
	}

	public int getBorneXLeft() {
		return borneXLeft;
	}

	public void setBorneXLeft(int borneXLeft) {
		this.borneXLeft = borneXLeft;
		setChanged ();
		notifyObservers ();
	}

	public int getBorneXRight() {
		return borneXRight;
	}

	public void setBorneXRight(int borneXRight) {
		this.borneXRight = borneXRight;
		setChanged ();
		notifyObservers ();
	}

	public int getBorneYTop() {
		return borneYTop;
	}

	public void setBorneYTop(int borneYTop) {
		this.borneYTop = borneYTop;
		setChanged ();
		notifyObservers ();
	}

	public int getBorneYDown() {
		return borneYDown;
	}

	public void setBorneYDown(int borneYDown) {
		this.borneYDown = borneYDown;
		setChanged ();
		notifyObservers ();
	}
	
	
}
