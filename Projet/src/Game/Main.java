package Game;

import IG.*;
import Perceptron.*;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		Plateau plateau = new Plateau("src/Layout/Perceptron.lay");
		IA perceptron = new IA();
		/*ArrayList<Jeu> list = perceptron.rechercheAleatoire(100, plateau);
		double meilleurScore = perceptron.getMeilleurScore(list);
		System.out.println("Meilleur score final= " + meilleurScore);*/
		perceptron.vizualise(100,plateau);
	}
}
