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
		Jeu jeu = new Jeu(plateau, new StrategieIntelligente(plateau), new StrategieFocusChateau(plateau));
		Thread t1 = new Thread(jeu);
		t1.start();
		/*int nbpartie = 0;
		int gagneBleu = 0;
		int gagneRouge = 0;
		while(nbpartie!=100) {
			Jeu jeu = new Jeu(plateau, new StrategiePlusProche(plateau), new StrategieFocusChateau(plateau));
			Thread t1 = new Thread(jeu);
			t1.start();
			try {
				t1.join();
			}catch (InterruptedException e) {
			      e.printStackTrace();
		    }
			if(jeu.isPartieFini()) {
				nbpartie++;
				if(jeu.isGagneBleu())
					gagneBleu++;
				else
					gagneRouge++;
			}
			Thread t2 = new Thread(jeu);
			t2.start();
			try {
				t2.join();
			}catch (InterruptedException e) {
			      e.printStackTrace();
		    }
			if(jeu.isPartieFini()) {
				nbpartie++;
				if(jeu.isGagneBleu())
					gagneBleu++;
				else
					gagneRouge++;
			}
		}
		System.out.println("Nombre de partie : "+nbpartie+" Victoire équipe rouge : "+gagneRouge+ " Victoire équipe bleue : "+gagneBleu);
	*/
	}

}
