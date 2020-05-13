package Perceptron;

import IG.*;
import Strategie.*;
import java.lang.Thread;
import java.util.ArrayList;

import Game.Jeu;
import Game.ViewGame;

public class IA {
	
	public void getAverageReward(int nbtour, int nbPartie,StrategieType strategieBleu, StrategieType strategieRouge, Plateau plat) {
		int bleu = 0;
		int rouge = 0;
		int egalite = 0;
		double somme = 0;
		ArrayList<Jeu> list = new ArrayList<Jeu>();
		ArrayList<Thread> thread = new ArrayList<Thread>();
		for(int i=0; i<nbPartie; ++i) {
			Plateau plateau = new Plateau(plat.getFile());
			Strategie stratbleue = affecteStrategie(strategieBleu,plateau);
			Strategie stratrouge = affecteStrategie(strategieRouge,plateau);
			Jeu jeu = new Jeu(plateau, stratbleue, stratrouge, nbtour, true);
			list.add(jeu);
		}
		for(int i=0; i<nbPartie; ++i) {
			Thread t1 = new Thread(list.get(i));
			thread.add(t1);
			t1.start();
		}
		for(int i=0; i<nbPartie; ++i) {
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
		//double avg = somme/nbPartie;
		double avgBleu = (bleu*100)/nbPartie;
		double avgRouge = (rouge*100)/nbPartie;
		double avgEgalite = (egalite*100)/nbPartie;
		System.out.println(bleu+","+rouge+","+egalite);
		System.out.println("Victoire bleue : "+avgBleu+"% Victoire rouge : "+avgRouge+"% Egalite : "+avgEgalite+"%");
		//System.out.println("Récompense moyenne : "+avg);
	}
	
	public Strategie affecteStrategie(StrategieType strat, Plateau plateau) {
		switch(strat) {
			case RANDOM:
				return new StrategieRandom(plateau);
			case PLUSPROCHE:
				return new StrategiePlusProche(plateau);
			case FOCUS:
				return new StrategieFocusChateau(plateau);
			case INTELLIGENTE:
				return new StrategieIntelligente(plateau);
			case GROUPE:
				return new StrategieGroupe(plateau);
			case PERCEPTRON:
				StrategiePerceptron strategie = new StrategiePerceptron(plateau);
				LabeledSet training = strategie.genererExemples(100, 100, plateau);
				strategie.getPerceptron().setNb_iteration(20);
				strategie.getPerceptron().train(training);
			default:
				return new StrategieRandom(plateau);
		}
	}
	
	public ArrayList<Jeu> getAverageReward2(int nbtour, Plateau plat, int nbPerceptrons, int nbMeilleurs) {
		int bleu = 0;
		int rouge = 0;
		int egalite = 0;
		double somme = 0;
		ArrayList<Jeu> list = new ArrayList<Jeu>();
		ArrayList<Thread> thread = new ArrayList<Thread>();
		for(int i=0; i<nbPerceptrons; ++i) {
			Plateau plateau = new Plateau(plat.getFile());
			Strategie stratbleue = new StrategieRandom(plateau);
			Strategie stratrouge = new StrategieRandom(plateau);
			Jeu jeu = new Jeu(plateau, stratbleue, stratrouge, nbtour, true);
			list.add(jeu);
		}
		for(int i=0; i<nbPerceptrons; ++i) {
			Thread t1 = new Thread(list.get(i));
			thread.add(t1);
			t1.start();
		}
		for(int i=0; i<nbPerceptrons; ++i) {
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
		double avg = somme/nbPerceptrons;
		System.out.println(somme);
		System.out.println("Victoire bleue : "+bleu+" Victoire rouge : "+rouge+" Egalite : "+egalite);
		System.out.println("Récompense moyenne : "+avg);
		
		ArrayList<Jeu> listReturn = new ArrayList<Jeu>();
		for(int i=0; i<nbMeilleurs; i++) {
			Jeu meilleurJeu = getMeilleurJeu(list);
			listReturn.add(meilleurJeu);
			list.remove(meilleurJeu);
		}
		System.out.println("Meilleur score = " + getMeilleurScore(list));
		
		return listReturn;
	}
	
	public Jeu getMeilleurJeu(ArrayList<Jeu> list) {
		Jeu meilleur = list.get(0);
		for(int i=1; i < list.size(); i++) {
			if(meilleur.getReward() < list.get(i).getReward()) meilleur = list.get(i);
		}
		return meilleur;
	}
	
	public double getMeilleurScore(ArrayList<Jeu> jeux) {
		double meilleur = 0;
		for(int i=1; i<jeux.size(); i++) {
			if(jeux.get(i).getReward() > meilleur)
				meilleur = jeux.get(i).getReward();
		}
		return meilleur;
	}
	
	// tailleMax = N perceptrons dans la population
	public ArrayList<Jeu> rechercheAleatoire(int tailleMax, Plateau plateau){
		ArrayList<Jeu> meilleursJeux = new ArrayList<Jeu>();
		
		while(meilleursJeux.size() < tailleMax) {
			int nbPerceptrons = tailleMax - meilleursJeux.size(); // N - M
			int tailleListAverage = 10;
			ArrayList<Jeu> jeux = getAverageReward2(100,plateau,nbPerceptrons,tailleListAverage);
			for(int i=0; i < tailleListAverage; i++) {
				meilleursJeux.add(jeux.get(i));
			}			
		}
		return meilleursJeux;
	}
	
	public void vizualise(int nbtour, StrategieType strategieBleu, StrategieType strategieRouge, Plateau plateau) {
		Strategie stratbleue = affecteStrategie(strategieBleu,plateau);
		Strategie stratrouge = affecteStrategie(strategieRouge,plateau);
		Jeu jeu = new Jeu(plateau, stratbleue, stratrouge, nbtour, false);
		ViewGame view = new ViewGame(plateau);
		try {
			Thread.sleep(2000);
		}catch(Exception e) {
			
		}
		Thread t1 = new Thread(jeu);
		t1.start();
	}

}