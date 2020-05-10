package Strategie;

import java.util.ArrayList;
import java.util.Random;

import IG.Plateau;
import Perceptron.Perceptron;
import Perceptron.SparseVector;
import Troupes.*;

public class StrategiePerceptron extends Strategie{
	private Plateau plateau;
	
	public StrategiePerceptron(Plateau plateau) {
		super(plateau);
		this.plateau = plateau;
	}
	
	public TroupesAction coup(Troupes troupe) {
		Perceptron perceptron = new Perceptron(0.01,5,TroupesAction.values().length);
		SparseVector etat = encodageEtat(plateau,troupe);
		double maxScore = 0;
		TroupesAction meilleureAction = TroupesAction.STOP;
		ArrayList<TroupesAction> coups = super.coupsPossibles(troupe);
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
	
	public boolean estPerceptron() {
		return true;
	}
	
	
}