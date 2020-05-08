package Game;

import IG.*;
import Perceptron.*;

public class Main {

	public static void main(String[] args) {
		
		Plateau plateau = new Plateau("src/Layout/Perceptron.lay");
		IA perceptron = new IA();
		//perceptron.getAverageReward2(200, plateau);
		perceptron.vizualise(100,plateau);
	}
}
