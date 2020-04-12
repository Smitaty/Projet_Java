package Game;
import IG.*;
import Strategie.*;
import java.lang.Thread;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Plateau plateau = new Plateau("src/Layout/Plateau.lay");
		ViewGame view = new ViewGame(plateau);
		try {
			Thread.sleep(2000);
		}catch(Exception e) {
			
		} 
		Jeu jeu = new Jeu(plateau,new StrategiePlusProche(plateau),new StrategieFocusChateau(plateau));
	}

}
