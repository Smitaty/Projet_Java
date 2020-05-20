package Strategie;

import java.util.ArrayList;
import java.util.Random;

import Game.Jeu;
import IG.Plateau;
import Perceptron.*;
import Troupes.*;

/**
 * Stratégie qui implémente une Intelligence Artificielle en utilisant les classes du package Perceptron
 * @author Simon et Rémi
 */
public class StrategiePerceptron extends Strategie{
	private Plateau plateau;
	private Perceptron perceptron;
	
	public StrategiePerceptron(Plateau plateau) {
		super(plateau);
		this.plateau = plateau;
		perceptron = new Perceptron(0.01,5,TroupesAction.values().length);
	}
	
	/**
	 * Méthode qui retourne l'action avec le plus gros score parmi les actions possibles
	 * @param troupe
	 * @return TroupesAction
	 */
	
	public TroupesAction coup(Troupes troupe) {
		ArrayList<TroupesAction> coups = super.coupsPossibles(troupe);
		SparseVector etat = encodageEtat(plateau,troupe);
		double maxScore = 0;
		TroupesAction meilleureAction = TroupesAction.STOP;
		for(TroupesAction action : coups) {
			double score = perceptron.getScore(encodageAction(etat,action));
			if(score>maxScore || meilleureAction==TroupesAction.STOP) {
				maxScore = score;
				meilleureAction = action;
			}
			else {
				if(score==maxScore) {
					Random r = new Random();
					int res = r.nextInt(2);
					if(res==1) {
						maxScore = score;
						meilleureAction = action;
					}
				}
			}
		}
		troupe.setReward(maxScore);
		return meilleureAction;
	}
	
	/**
	 * Méthode qui encode l'environnement de la troupe dans un SparseVector
	 * @param plateau
	 * @param troupe
	 * @return SparseVector
	 */
	
	public SparseVector encodageEtat(Plateau plateau, Troupes troupe) {
		int tx = troupe.getPosition().getX();
		int ty = troupe.getPosition().getY();
		SparseVector s = new SparseVector(75);
		boolean estBleu;
		if(plateau.getTroupeBleu(troupe.getPosition())==null) {
			estBleu=false;
		}
		else {
			estBleu=true;
		}
		int cpt = 0;
		for(int y=-2; y<3; ++y) {
			for(int x=-2; x<3; ++x) {
				Coordonnees pos = new Coordonnees(tx+x,ty+y);
				if(estBleu) {
					if(plateau.getTroupeRouge(pos)!=null) {
						if(plateau.getTroupeRouge(pos).getType()=="Chateau")
							s.setValue(50+cpt, 1);
						else
							s.setValue(cpt, 1);
					}
					else {
						s.setValue(cpt, 0);
						s.setValue(50+cpt, 0);
					}
				}
				else {
					if(plateau.getTroupeBleu(pos)!=null) {
						if(plateau.getTroupeBleu(pos).getType()=="Chateau")
							s.setValue(50+cpt, 1);
						else
							s.setValue(cpt, 1);
					}
				}
				if(plateau.ArbreEn(pos) || plateau.rocherEn(pos)) {
					s.setValue(25+cpt, 1);
				}
				++cpt;
			}
		}
		return s;
	}
	
	/**
	 * Méthode qui encode un SparseVector dans un nouveau SparseVector en fonction de l'action donnée
	 * @param s
	 * @param action
	 * @return SparseVector
	 */
	
	public SparseVector encodageAction(SparseVector s, TroupesAction action) {
		SparseVector vecteur = new SparseVector(s.size()*6+1);
		vecteur.setValue(0, 1);
		switch(action) {
		case TOP:
			for(int i=1; i<=s.size(); ++i) {
				if(s.getValue(i)==1) {
					vecteur.setValue(i, 1);
				}
			}
			break;
		case BOTTOM:
			for(int i=1; i<=s.size(); ++i) {
				if(s.getValue(i)==1) {
					vecteur.setValue(75+i, 1);
				}
			}
			break;
		case LEFT:
			for(int i=1; i<=s.size(); ++i) {
				if(s.getValue(i)==1) {
					vecteur.setValue(150+i, 1);
				}
			}
			break;
		case RIGHT:
			for(int i=1; i<=s.size(); ++i) {
				if(s.getValue(i)==1) {
					vecteur.setValue(225+i, 1);
				}
			}
			break;
		case ATTACK1:
			for(int i=1; i<=s.size(); ++i) {
				if(s.getValue(i)==1) {
					vecteur.setValue(300+i, 1);
				}
			}
			break;
		case STOP:
			for(int i=1; i<=s.size(); ++i) {
				if(s.getValue(i)==1) {
					vecteur.setValue(375+i, 1);
				}
			}
			break;
		}
		return vecteur;
	}
	
	/**
	 * Méthode qui retourne liste de Quadruplet
	 * @param nbTour
	 * @param nbSimulations
	 * @param plat
	 * @return ArrayList<Quadruplet>
	 */
	
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
		return listQuad;
	}
	
	/**
	 * Méthode qui retourne un LabeledSet en fonction des Quadruplets récupérés
	 * @param troupe
	 * @return LabeledSet
	 */
	
	public LabeledSet genererExemples(int nbTour, int nbSimulations, Plateau plat){
		ArrayList<Quadruplet> listQuad = getLearningSet(nbTour,nbSimulations,plat);
		int tailleVecteur = listQuad.get(0).getEtat().size();
		LabeledSet label = new LabeledSet(tailleVecteur);
		for(int i = 0; i<listQuad.size(); i++) {
			SparseVector apprentissage = encodageAction(listQuad.get(i).getEtat(),listQuad.get(i).getAction());
			label.addExample(apprentissage, listQuad.get(i).getReward());
		}
		return label;
	}
	
	public Perceptron getPerceptron() {return perceptron;}
	
	public void setPerceptron(Perceptron p) {perceptron=p;}
	
	public boolean estPerceptron() {return true;}
}
