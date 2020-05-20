package Perceptron;

import IG.*;
import Strategie.*;
import java.lang.Thread;
import java.util.ArrayList;

import Game.Jeu;
import Game.ViewGame;


/**
 * Cette classe permet de choisir de lancer une partie avec l'interface graphique
 * ou de lancer un grand nombre de simulation
 * @author Simon et Rémi
 */

public class IA {
	
	/**
	 * Méthode qui permet de lancer un grand nombre de partie sans interface graphique 
	 * et qui affiche la récompense moyenne et les stats de victoire de chaque équipe
	 * @param nbtour
	 * @param nbPartie
	 * @param strategieBleu
	 * @param strategieRouge
	 * @param plat
	 */
	
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
		double avg = somme/nbPartie;
		double avgBleu = (bleu*100)/nbPartie;
		double avgRouge = (rouge*100)/nbPartie;
		double avgEgalite = (egalite*100)/nbPartie;
		System.out.println("Victoire bleue : "+avgBleu+"% Victoire rouge : "+avgRouge+"% Egalite : "+avgEgalite+"%");
		System.out.println("Récompense moyenne : "+avg);
	}
	
	/**
	 * Méthode qui retourne une stratégie en fonction de la stratégie demandée 
	 * @param strat
	 * @param plateau
	 * @return Strategie
	 */
	
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
	
	/**
	 * Méthode qui permet de lancer un grand nombre de partie sans interface graphique 
	 * qui affiche la récompense moyenne et les stats de victoire de chaque équipe 
	 * et retourne une liste des meilleurs parties
	 * @param nbtour
	 * @param plat
	 * @param nbPerceptrons
	 * @param nbMeilleurs
	 * @return ArrayList<Jeu>
	 */
	
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
	
	/**
	 * Méthode qui retourne la meilleure partie parmi une liste de parties
	 * @param list
	 * @return Jeu
	 */
	
	public Jeu getMeilleurJeu(ArrayList<Jeu> list) {
		Jeu meilleur = list.get(0);
		for(int i=1; i < list.size(); i++) {
			if(meilleur.getReward() < list.get(i).getReward()) meilleur = list.get(i);
		}
		return meilleur;
	}
	
	/**
	 * Méthode qui retourne le meilleur score obtenu dans une liste de partie
	 * @param list
	 * @return double
	 */
	
	public double getMeilleurScore(ArrayList<Jeu> jeux) {
		double meilleur = 0;
		for(int i=1; i<jeux.size(); i++) {
			if(jeux.get(i).getReward() > meilleur)
				meilleur = jeux.get(i).getReward();
		}
		return meilleur;
	}
	
	/**
	 * Cette méthode effectue N-M simulations jusqu'à ce que le nombre de simulation soit inférieure à tailleMax
	 * N correspond à tailleMax
	 * M correspond à la taille de la liste des meilleurs parties
	 * Après avoir effectué les simulations on fait un tri en gardant les meilleurs parties
	 * parmi celles déjà simulées et les nouvelles
	 * @param tailleMax
	 * @param plateau
	 * @return ArrayList<Jeu>
	 */
	
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
	
	/**
	 * Méthode qui permet de lancer une partie avec l'interface graphique
	 * @param nbtour
	 * @param strategieBleu
	 * @param strategieRouge
	 * @param plateau
	 */
	
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