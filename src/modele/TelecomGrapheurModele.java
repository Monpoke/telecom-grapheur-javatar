package modele;

import java.util.Observable;

public class TelecomGrapheurModele extends Observable{

	AxeModele axeModeleX = new AxeModele();
	AxeModele axeModeleY = new AxeModele();
	PointModele origin = new PointModele();
	BorneGraphiqueModele bornes = new BorneGraphiqueModele();
	boolean isClique = false;
	
	public TelecomGrapheurModele() {
		this.initBornes();
		this.initAxeX();
		this.initAxeY();
		this.initOrigin();
	}
	
	public void setClique(boolean isClique) {
		this.isClique = isClique;
	}
	
	public boolean isClique(){
		return this.isClique;
	}

	public AxeModele getAxeModeleX() {
		return axeModeleX;
	}
	
	public void setAxeModeleX(AxeModele axeModeleX) {
		this.axeModeleX = axeModeleX;
		setChanged ();
		notifyObservers ();
	}
	
	public AxeModele getAxeModeleY() {
		return axeModeleY;
	}
	
	public void setAxeModeleY(AxeModele axeModeleY) {
		this.axeModeleY = axeModeleY;
		setChanged ();
		notifyObservers ();
	}
	
	public PointModele getOrigin() {
		return origin;
	}
	
	public void setOrigin(PointModele origin) {
		this.origin = origin;
		setChanged ();
		notifyObservers ();
	}
	
	public BorneGraphiqueModele getBornes() {
		return bornes;
	}
	
	public void setBornes(BorneGraphiqueModele bornes) {
		this.bornes = bornes;
		setChanged ();
		notifyObservers ();
	}
	
	public void initBornes(){
		this.getBornes().setBorneXLeft(-1000); // axes caché en dehors de la fenetre pour plus de fluidité et pour ne pas tout recalculer
		this.getBornes().setBorneXRight(1000);
		this.getBornes().setBorneYTop(-1000);
		this.getBornes().setBorneYDown(1000);
	}
	
	public void initOrigin(){
		this.getOrigin().setX(Constantes.width/2);
		this.getOrigin().setY(Constantes.height/2);
	}
	
	public void initAxeX(){
		this.getAxeModeleX().setBorneXLeft(0);
		this.getAxeModeleX().setBorneXRight(Constantes.width);
		this.getAxeModeleX().setBorneYDown(Constantes.height/2);
		this.getAxeModeleX().setBorneYtop(Constantes.height/2);
		this.getAxeModeleX().setCoeffZoom(1);
		this.getAxeModeleX().setTailleCase(80);
		this.getAxeModeleX().setPas(1);
	}
	
	public void initAxeY(){
		this.getAxeModeleY().setBorneXLeft(Constantes.width/2);
		this.getAxeModeleY().setBorneXRight(Constantes.width/2);
		this.getAxeModeleY().setBorneYDown(Constantes.height);
		this.getAxeModeleY().setBorneYtop(0);
		this.getAxeModeleY().setCoeffZoom(1);
		this.getAxeModeleY().setTailleCase(80);
		this.getAxeModeleY().setPas(1);
	}
}
