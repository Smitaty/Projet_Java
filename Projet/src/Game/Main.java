package Game;

import IG.*;
import Perceptron.*;

public class Main {

	public static void main(String[] args) {
		
		Plateau plateau = new Plateau("src/Layout/Plateau.lay");
		IA perceptron = new IA();
		perceptron.getAverageReward(200, plateau);
		//getAverageReward2(200, plateau);
		//vizualise(100,new StrategieIntelligente(plateau), new StrategieRandom(plateau),plateau);
	}
}
