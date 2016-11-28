package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class CourbeModele extends Observable{
	
	private ArrayList<PointModele> listePoints;
	private ArrayList<String> fonction = new ArrayList<String>();
	
	public CourbeModele() {
		this.listePoints = new ArrayList<PointModele>();
	}
	
	public ArrayList<PointModele> getListePoints() {
		return listePoints;
	}
	
	public void setListePoints(ArrayList<PointModele> listePoints) {
		this.listePoints = listePoints;
		setChanged ();
		notifyObservers ();
	}
	
	public void setPoints(PointModele point) {
		this.listePoints.add(point);
		setChanged ();
		notifyObservers ();
	}
	
	public void videListe(){
		this.listePoints = new ArrayList<PointModele>();
		setChanged ();
		notifyObservers ();
	}
	
	public ArrayList<String> getFonction() {
		return fonction;
	}
	
	public void setFonction(ArrayList<String> fonction) {
		this.fonction = fonction;
	}
}
