package Game;
import IG.*;
import Strategie.*;
import java.lang.Thread;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		/*Plateau plateau = new Plateau("src/Layout/Plateau.lay");
		ViewGame view = new ViewGame(plateau);
		try {
			Thread.sleep(2000);
		}catch(Exception e) {
			
		}		
		Jeu jeu = new Jeu(plateau, new StrategieIntelligente(plateau), new StrategieRandom(plateau));
		Thread t1 = new Thread(jeu);
		t1.start();*/
		getAverageReward();
	}

	public static void getAverageReward() {
		int bleu = 0;
		int rouge = 0;
		ArrayList<Jeu> list = new ArrayList<Jeu>();
		ArrayList<Thread> thread = new ArrayList<Thread>();
		for(int i=0; i<100; ++i) {
			Plateau plateau = new Plateau("src/Layout/Plateau.lay");
			list.add(new Jeu(plateau, new StrategieRandom(plateau), new StrategieRandom(plateau),100));
		}
		for (int i=0; i<100; ++i) {
			Thread t1 = new Thread(list.get(i));
			thread.add(t1);
			t1.start();
		}
		for (int i=0; i<100; ++i) {
			try{
				thread.get(i).join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			if(list.get(i).isPartieFini()) {
				if(list.get(i).isGagneBleu()) {
					++bleu;
					System.out.println("Partie "+i+" victoire bleu");
				}
				else {
					if(list.get(i).isGagneRouge()) {
						++rouge;
						System.out.println("Partie "+i+" victoire rouge");
					}
					else {
						System.out.println("Partie "+i+" nulle");
					}
				}
			}
		}
		int egalite = 100-bleu+rouge;
		System.out.println("Victoire bleue : "+bleu+" Victoire rouge : "+rouge+" Egalite : "+egalite);
	}
}
