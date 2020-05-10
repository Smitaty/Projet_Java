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
		System.out.println("R�compense moyenne : "+avg);
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
		System.out.println("R�compense moyenne : "+avg);
		
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

	
	public ArrayList<Quadruplet> getLearningSet(int nbTour, int nbSimulations, Plateau plat) {
		ArrayList<Quadruplet> listQuad = new ArrayList<Quadruplet>();
		
		ArrayList<Jeu> list = new ArrayList<Jeu>();
		ArrayList<Thread> thread = new ArrayList<Thread>();
		for(int i=0; i<nbSimulations; ++i) {
			Plateau plateau = new Plateau(plat.getFile());
			Strategie stratbleue = new StrategiePerceptron(plateau);
			Strategie stratrouge = new StrategieRandom(plateau);
			Jeu jeu = new Jeu(plateau, stratbleue, stratrouge, nbTour, true);
			list.add(jeu);
		}
		for(int i=0; i<nbSimulations; ++i) {
			Thread t1 = new Thread(list.get(i));
			thread.add(t1);
			t1.start();
		}
		for(int i=0; i<nbSimulations; ++i) {
			try{
				thread.get(i).join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		 	listQuad.addAll(list.get(i).getListQuad());
		}
		System.out.println("Nombre de quadruplets = " + listQuad.size());
		
		return listQuad;
	}
	
	public ArrayList<LabeledSet> genererExemples(int nbTour, int nbSimulations, Plateau plat){
		ArrayList<LabeledSet> listLabel = new ArrayList<LabeledSet>();
		ArrayList<Quadruplet> listQuad = getLearningSet(nbTour,nbSimulations,plat);
		int tailleVecteur = listQuad.get(0).getEtat().size();
		for(int i = 0; i<listQuad.size(); i++) {
			LabeledSet label = new LabeledSet(tailleVecteur);
			label.addExample(listQuad.get(i).getEtat(), listQuad.get(i).getReward());
		}
		
		return listLabel;
	}
}