package Game;
import IG.*;
import Strategie.*;
import java.lang.Thread;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		Plateau plateau = new Plateau("src/Layout/Plateau.lay");
		//getAverageReward(200,new StrategieRandom(plateau), new StrategieRandom(plateau), plateau);
		getAverageReward2(200, plateau);
		//vizualise(100,new StrategieIntelligente(plateau), new StrategieRandom(plateau),plateau);
	}

	public static void getAverageReward(int nbtour, Strategie stratbleue, Strategie stratrouge, Plateau plat) {
		int bleu = 0;
		int rouge = 0;
		int egalite = 0;
		for(int i=0; i<100; ++i) {
			Plateau plateau = new Plateau(plat.getFile());
			stratbleue.setPlateau(plateau);
			stratrouge.setPlateau(plateau);
			Jeu jeu = new Jeu(plateau, stratbleue, stratrouge, nbtour, true);
			Thread t1 = new Thread(jeu);
			t1.start();
			try{
				t1.join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			if(jeu.isPartieFini()) {
				if(jeu.isGagneBleu()) {
					++bleu;
				}
				else {
					if(jeu.isGagneRouge()) {
						++rouge;
					}
					else 
						++egalite;
				}
			}
		}
		System.out.println("Victoire bleue : "+bleu+" Victoire rouge : "+rouge+" Egalite : "+egalite);
	}
	
	public static void getAverageReward2(int nbtour, Plateau plat) {
		int bleu = 0;
		int rouge = 0;
		int egalite = 0;
		ArrayList<Jeu> list = new ArrayList<Jeu>();
		ArrayList<Thread> thread = new ArrayList<Thread>();
		for(int i=0; i<100; ++i) {
			Plateau plateau = new Plateau(plat.getFile());
			Strategie stratbleue = new StrategieRandom(plateau);
			Strategie stratrouge = new StrategieRandom(plateau);
			Jeu jeu = new Jeu(plateau, stratbleue, stratrouge, nbtour, true);
			list.add(jeu);
		}
		for(int i=0; i<100; ++i) {
			Thread t1 = new Thread(list.get(i));
			thread.add(t1);
			t1.start();
		}
		for(int i=0; i<100; ++i) {
			try{
				thread.get(i).join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			if(list.get(i).isPartieFini()) {
				if(list.get(i).isGagneBleu()) {
					++bleu;
				}
				else {
					if(list.get(i).isGagneRouge()) {
						++rouge;
					}
					else 
						++egalite;
				}
			}
		}
		System.out.println("Victoire bleue : "+bleu+" Victoire rouge : "+rouge+" Egalite : "+egalite);
	}
	
	public static void vizualise(int nbtour, Strategie stratbleue, Strategie stratrouge, Plateau plateau) {
		ViewGame view = new ViewGame(plateau);
		try {
			Thread.sleep(2000);
		}catch(Exception e) {
			
		}
		Jeu jeu = new Jeu(plateau, stratbleue, stratrouge, nbtour, false);
		Thread t1 = new Thread(jeu);
		t1.start();
	}
}
