package Perceptron;

import IG.*;
import Strategie.*;
import Troupes.*;
import java.lang.Thread;
import java.util.ArrayList;

import Game.Jeu;
import Game.ViewGame;

public class IA {
	
	public void getAverageReward(int nbtour, Plateau plat) {
		int bleu = 0;
		int rouge = 0;
		int egalite = 0;
		int somme = 0;
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
		float avg = somme/100;
		System.out.println("Victoire bleue : "+bleu+" Victoire rouge : "+rouge+" Egalite : "+egalite);
		System.out.println("Récompense moyenne : "+avg);
	}
	
	public void getAverageReward2(int nbtour, Plateau plat) {
		int bleu = 0;
		int rouge = 0;
		int egalite = 0;
		int somme = 0;
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
		float avg = somme/100;
		System.out.println("Victoire bleue : "+bleu+" Victoire rouge : "+rouge+" Egalite : "+egalite);
		System.out.println("Récompense moyenne : "+avg);
	}
	
	public void vizualise(int nbtour, Strategie stratbleue, Strategie stratrouge, Plateau plateau) {
		ViewGame view = new ViewGame(plateau);
		try {
			Thread.sleep(2000);
		}catch(Exception e) {
			
		}
		Jeu jeu = new Jeu(plateau, stratbleue, stratrouge, nbtour, false);
		Thread t1 = new Thread(jeu);
		t1.start();
	}

	public SparseVector encodageEtat(Jeu jeu, Troupes troupe) {
		Plateau plateau = jeu.getPlateau();
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
		int cpt = 1;
		for(int y=-2; y<3; ++y) {
			for(int x=-2; x<3; ++x) {
				Coordonnees pos = new Coordonnees(tx+x,ty+y);
				if(estBleu) {
					if(plateau.getTroupeRouge(pos)!=null) {
						if(plateau.getTroupeRouge(pos).getType()=="Chateau")
							s.setValue(51+cpt, 1);
						else
							s.setValue(cpt, 1);
					}
					else {
						s.setValue(cpt, 0);
						s.setValue(51+cpt, 0);
					}
				}
				else {
					if(plateau.getTroupeBleu(pos)!=null) {
						if(plateau.getTroupeBleu(pos).getType()=="Chateau")
							s.setValue(51+cpt, 1);
						else
							s.setValue(cpt, 1);
					}
				}
				if(plateau.ArbreEn(pos) || plateau.rocherEn(pos)) {
					s.setValue(26+cpt, 1);
				}
				++cpt;
			}
		}
		return s;
	}
	
	public SparseVector encodageAction(SparseVector s, TroupesAction action) {
		SparseVector vecteur = new SparseVector(s.size()*6+1);
		vecteur.setValue(1, 1);
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
}
