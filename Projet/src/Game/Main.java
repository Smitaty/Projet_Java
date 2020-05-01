package Game;
import IG.*;
import Strategie.*;
import java.lang.Thread;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		Plateau plateau = new Plateau("src/Layout/Plateau.lay");
		/*ViewGame view = new ViewGame(plateau);
		try {
			Thread.sleep(2000);
		}catch(Exception e) {
			
		}		
		Jeu jeu = new Jeu(plateau, new StrategieIntelligente(plateau), new StrategieRandom(plateau));
		Thread t1 = new Thread(jeu);
		t1.start();*/
		getAverageReward(plateau);
	}

	public static void getAverageReward(Plateau plateau) {
		int bleu = 0;
		int rouge = 0;
		ArrayList<Jeu> list = new ArrayList<Jeu>();
		ArrayList<Thread> thread = new ArrayList<Thread>();
		System.out.println("Jeu");
		for(int i=0; i<100; ++i) {
			list.add(new Jeu(plateau, new StrategieIntelligente(plateau), new StrategieRandom(plateau)));
		}
		System.out.println("Thread");
		for (int i=0; i<100; ++i) {
			Thread t1 = new Thread(list.get(i));
			thread.add(t1);
			t1.start();
		}
		System.out.println("Join");
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
					++rouge;
					System.out.println("Partie "+i+" victoire rouge");
				}
			}
		}
		System.out.println("Victoire bleue : "+bleu+" Victoire rouge : "+rouge);
	}
}
