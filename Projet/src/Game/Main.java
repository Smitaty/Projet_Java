package Game;

import IG.*;
import Perceptron.*;
import Strategie.StrategieType;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		Plateau plateau = new Plateau("src/Layout/Plateau.lay");
		IA perceptron = new IA();
		//ArrayList<Jeu> list = perceptron.rechercheAleatoire(100, plateau);
		//double meilleurScore = perceptron.getMeilleurScore(list);
		//System.out.println("Meilleur score final= " + meilleurScore);
		//perceptron.vizualise(100,StrategieType.PLUSPROCHE,StrategieType.RANDOM,plateau);
		perceptron.getAverageReward(1000,1000,StrategieType.RANDOM,StrategieType.FOCUS,plateau);
	}
}