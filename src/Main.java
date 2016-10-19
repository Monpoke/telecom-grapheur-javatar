import modele.TelecomGrapheurModele;
import controleur.TelecomGrapheurControleur;
import vue.FenetreContener;
import vue.TelecomGrapheurVue;

public class Main {

	public static void main(String[] args) {
		FenetreContener fenetre = new FenetreContener();
		TelecomGrapheurModele modele = new TelecomGrapheurModele();
		TelecomGrapheurControleur controleur = new TelecomGrapheurControleur(modele);
		TelecomGrapheurVue graph = new TelecomGrapheurVue(fenetre,modele,controleur);
		fenetre.add(graph);
	}
}