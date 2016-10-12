import vue.FenetreContener;
import vue.TelecomGrapheur;

public class Main {

	public static void main(String[] args) {
		FenetreContener fenetre = new FenetreContener();
		TelecomGrapheur graph = new TelecomGrapheur(fenetre);
		fenetre.add(graph);
	}
}