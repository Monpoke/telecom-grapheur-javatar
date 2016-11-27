package modele;

import java.util.ArrayList;
import java.util.Observable;

public class TelecomGrapheurModele extends Observable{

	private AxeModele axeModeleX = new AxeModele();
	private AxeModele axeModeleY = new AxeModele();
	private PointModele origin = new PointModele();
	private BorneGraphiqueModele bornes = new BorneGraphiqueModele();
	private CourbeModele courbe = new CourbeModele();
	private boolean isClique = false;
	private PointModele cursor = new PointModele(0,0);
	private CourbeModele listesPoints = new CourbeModele();
	private String jtext = "";
	private boolean dialog = false;
	
	public TelecomGrapheurModele() {
		this.initBornes();
		this.initAxeX();
		this.initAxeY();
		this.initOrigin();
		this.initCourbe();
		//this.createCourbe();
	}
	
	public boolean getDialog(){
		 		return this.dialog;
		 	}
		 	
		 	public void setDialog(boolean dialog){
		 		this.dialog = dialog;
		 		setChanged ();
		 		notifyObservers ();
		 	}
		 	
	
	public CourbeModele getListesPoints() {
		return listesPoints;
	}
	
	public void setListesPoints(CourbeModele listesPoints) {
		this.listesPoints = listesPoints;
	}
	
	public String getJtext() {
		return jtext;
	}
	
	public void setJtext(String jtext) {
		this.jtext = jtext;
		setChanged ();
		notifyObservers ();
	}
	
	public void setClique(boolean isClique) {
		this.isClique = isClique;
		setChanged ();
		notifyObservers ();
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
	
	public CourbeModele getCourbe() {
		return courbe;
	}
	
	public void setCourbe(CourbeModele courbe) {
		this.courbe = courbe;
		setChanged ();
		notifyObservers ();
	}
	
	public PointModele getCursor() {
		return cursor;
	}
	
	public void setCursor(PointModele cursor) {
		this.cursor = cursor;
		setChanged ();
		notifyObservers ();
	}
	
	public void initBornes(){
		this.getBornes().setBorneXLeft(-2000); // axes caché en dehors de la fenetre pour plus de fluidité et pour ne pas tout recalculer
		this.getBornes().setBorneXRight(2000);
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
	
	public void initCourbe(){
		this.courbe = new CourbeModele();
	}
	
//	public void createCourbe(){
//		for(int i = getBornes().getBorneXLeft(); i < getBornes().getBorneXRight();i++){
//			getCourbe().setPoints(new PointModele((int) (i+getOrigin().getX()), (int) (-Math.cos(i/(getAxeModeleY().getTailleCase()/getAxeModeleY().getPas()))*getAxeModeleY().getTailleCase()/getAxeModeleY().getPas()+ getOrigin().getY())));
//		}
//	}
}
