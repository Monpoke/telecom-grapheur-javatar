import modele.TelecomGrapheurModele;
import controleur.TelecomGrapheurControleur;
import vue.FenetreContener;
import vue.TelecomGrapheurVue;

public class Main {

	public static void main(String[] args) {
		FenetreContener fenetre = new FenetreContener();
		TelecomGrapheurControleur controleur = new TelecomGrapheurControleur();
		TelecomGrapheurModele modele = new TelecomGrapheurModele();
		TelecomGrapheurVue graph = new TelecomGrapheurVue(fenetre,modele,controleur);
		fenetre.add(graph);
	}
}