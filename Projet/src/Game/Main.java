package Game;

import IG.*;
import Perceptron.*;
import Strategie.StrategieType;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		Plateau plateau = new Plateau("src/Layout/Plateau.lay");
		IA perceptron = new IA();

		//perceptron.vizualise(100,StrategieType.PERCEPTRON,StrategieType.RANDOM,plateau);
		perceptron.getAverageReward(1000,10,StrategieType.PERCEPTRON,StrategieType.RANDOM,plateau);
	}
}