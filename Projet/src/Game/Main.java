package Game;
import IG.*;
import Strategie.*;
import java.lang.Thread;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		Plateau plateau = new Plateau("src/Layout/Plateau.lay");
		ViewGame view = new ViewGame(plateau);
		try {
			Thread.sleep(2000);
		}catch(Exception e) {
			
		}		
		Jeu jeu = new Jeu(plateau, new StrategieIntelligente(plateau), new StrategieRandom(plateau));
		Thread t1 = new Thread(jeu);
		t1.start();
		//getAverageReward(plateau);
	}

	public void getAverageReward(Plateau plateau) {
		ArrayList<Game> list = new ArrayList<Game>();
		ArrayList<Thread> thread = new ArrayList<Thread>();
		for(int i=0; i<100; ++i) {
			list.add(new Jeu(plateau, new StrategieIntelligente(plateau), new StrategieRandom(plateau)));
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
		}
	}
}
