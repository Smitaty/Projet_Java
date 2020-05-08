package Perceptron;

import IG.*;
import Strategie.*;
import java.lang.Thread;
import java.util.ArrayList;

import Game.Jeu;
import Game.ViewGame;

public class IA {
	
	public void getAverageReward(int nbtour, Plateau plat) {
		int bleu = 0;
		int rouge = 0;
		int egalite = 0;
		double somme = 0;
		for(int i=0; i<100; ++i) {
			Plateau plateau = new Plateau(plat.getFile());
			Strategie stratbleue = new StrategieRandom(plateau);
			Strategie stratrouge = new StrategieRandom(plateau);
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
			somme+=jeu.getReward();
		}
		double avg = somme/100;
		System.out.println("Victoire bleue : "+bleu+" Victoire rouge : "+rouge+" Egalite : "+egalite);
		System.out.println("Récompense moyenne : "+avg);
	}
	
	public void getAverageReward2(int nbtour, Plateau plat) {
		int bleu = 0;
		int rouge = 0;
		int egalite = 0;
		double somme = 0;
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
			somme+=list.get(i).getReward();
		}
		double avg = somme/100;
		System.out.println(somme);
		System.out.println("Victoire bleue : "+bleu+" Victoire rouge : "+rouge+" Egalite : "+egalite);
		System.out.println("Récompense moyenne : "+avg);
	}
	
	public void vizualise(int nbtour,Plateau plateau) {
		ViewGame view = new ViewGame(plateau);
		try {
			Thread.sleep(2000);
		}catch(Exception e) {
			
		}
		Strategie stratbleue = new StrategieRandom(plateau);
		Strategie stratrouge = new StrategieRandom(plateau);
		Jeu jeu = new Jeu(plateau, stratbleue, stratrouge, nbtour, false);
		Thread t1 = new Thread(jeu);
		t1.start();
	}
}
