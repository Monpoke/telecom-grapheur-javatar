package modele;

import java.util.Observable;

public class TelecomGrapheurModele extends Observable{

	AxeModele axeModeleX = new AxeModele();
	AxeModele axeModeleY = new AxeModele();
	PointModele origin = new PointModele();
	BorneGraphiqueModele bornes = new BorneGraphiqueModele();
	
	public TelecomGrapheurModele() {
		this.initBornes();
		this.initAxeX();
		this.initAxeY();
		this.initOrigin();
	}
	
	public AxeModele getAxeModeleX() {
		return axeModeleX;
	}
	
	public void setAxeModeleX(AxeModele axeModeleX) {
		this.axeModeleX = axeModeleX;
	}
	
	public AxeModele getAxeModeleY() {
		return axeModeleY;
	}
	
	public void setAxeModeleY(AxeModele axeModeleY) {
		this.axeModeleY = axeModeleY;
	}
	
	public PointModele getOrigin() {
		return origin;
	}
	
	public void setOrigin(PointModele origin) {
		this.origin = origin;
	}
	
	public BorneGraphiqueModele getBornes() {
		return bornes;
	}
	
	public void setBornes(BorneGraphiqueModele bornes) {
		this.bornes = bornes;
	}
	
	public void initBornes(){
		this.getBornes().setBorneXLeft(-10);
		this.getBornes().setBorneXRight(10);
		this.getBornes().setBorneYTop(-5);
		this.getBornes().setBorneYDown(5);
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
	}
	
	public void initAxeY(){
		this.getAxeModeleY().setBorneXLeft(Constantes.width/2);
		this.getAxeModeleY().setBorneXRight(Constantes.width/2);
		this.getAxeModeleY().setBorneYDown(Constantes.height);
		this.getAxeModeleY().setBorneYtop(0);
		this.getAxeModeleY().setCoeffZoom(1);
		this.getAxeModeleY().setTailleCase(80);
	}
}
