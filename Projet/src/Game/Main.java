package Game;
import IG.*;
import Game.*;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import javax.swing.JFrame;
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
		JeuRandom jeu = new JeuRandom(plateau);
	}

}
