package Strategie;

import IG.Plateau;
import Perceptron.SparseVector;
import Troupes.*;
import java.util.ArrayList;
import java.util.Random;

/*
 * Strat�gie intelligente qui cherche l'ennemi le plus proche et ayant le moins de PV en cas d'�galit�.
 * Les troupes n'attaque pas que dans la direction dans laquelle elles sont tourn�es mais elles peuvent se tourner 
 * dans la bonne direction si elles trouvent un ennemi � port�e.
 */

public class StrategiePlusProche extends Strategie{
	private Plateau plateau;
	
	public StrategiePlusProche(Plateau plateau) {
		super(plateau);
		this.plateau=plateau;
	}
	
	public TroupesAction coup(Troupes troupe) {
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		Troupes ennemi = rechercheEnnemi(troupe);
		int ex = ennemi.getPosition().getX();
		int ey = ennemi.getPosition().getY();
		
		ArrayList<TroupesAction> coups = this.coupsIntelligentsPossibles(troupe);
		if(coups.size()>0) {
			if(estPresent(TroupesAction.ATTACK1,coups)) {
				return TroupesAction.ATTACK1;
			}
			if(y<ey) {
				if(estPresent(TroupesAction.BOTTOM,coups))
					return TroupesAction.BOTTOM;
				if(x<ex && estPresent(TroupesAction.RIGHT,coups))
					return TroupesAction.RIGHT;
				if(x>ex && estPresent(TroupesAction.LEFT,coups))
					return TroupesAction.LEFT;
				else{
					int r = new Random().nextInt(2);
					if(r==0 && estPresent(TroupesAction.LEFT,coups)) {
						return TroupesAction.LEFT;
					}
					else {
						if(estPresent(TroupesAction.RIGHT,coups))
							return TroupesAction.RIGHT;
						else if(estPresent(TroupesAction.TOP,coups))
							return TroupesAction.TOP;
					}
				}
			}
			if(y>ey) {
				if(estPresent(TroupesAction.TOP,coups))
					return TroupesAction.TOP;
				if(x<ex && estPresent(TroupesAction.RIGHT,coups))
					return TroupesAction.RIGHT;
				if(x>ex && estPresent(TroupesAction.LEFT,coups))
					return TroupesAction.LEFT;
				else{
					int r = new Random().nextInt(2);
					if(r==0 && estPresent(TroupesAction.LEFT,coups)) {
						return TroupesAction.LEFT;
					}
					else {
						if(estPresent(TroupesAction.RIGHT,coups))
							return TroupesAction.RIGHT;
					}
				}
			}
			else {
				if(x<ex) {
					if(estPresent(TroupesAction.RIGHT,coups))
						return TroupesAction.RIGHT;
					int r = new Random().nextInt(2);
					if(r==0 && estPresent(TroupesAction.TOP,coups)) {
						return TroupesAction.TOP;
					}
					else {
						if(estPresent(TroupesAction.BOTTOM,coups))
							return TroupesAction.BOTTOM;
					}
				}
				if(x>ex) {
					if(estPresent(TroupesAction.LEFT,coups))
						return TroupesAction.LEFT;
					int r = new Random().nextInt(2);
					if(r==0 && estPresent(TroupesAction.TOP,coups)) {
						return TroupesAction.TOP;
					}
					else {
						if(estPresent(TroupesAction.BOTTOM,coups))
							return TroupesAction.BOTTOM;
					}
				}
			}
			int coup = new Random().nextInt(coups.size());
			return coups.get(coup);
		}
		else {
			return TroupesAction.STOP;
		}
	}
	
	public Troupes rechercheEnnemi(Troupes troupe) {
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		double distanceMin=-1;
		Troupes ennemiPlusProche=null;
		ArrayList<Troupes> ennemi;
		if(this.estBleu(troupe))
			ennemi = plateau.getUnite_rouge();
		else
			ennemi = plateau.getUnite_bleue();
		for(Troupes unite : ennemi) {
			int ux = unite.getPosition().getX();
			int uy = unite.getPosition().getY();
			double terme1 = Math.pow((ux-x),2);
			double terme2 = Math.pow((uy-y),2);
			double distance = Math.sqrt(terme1+terme2);
			if(distanceMin==-1) {
				distanceMin=distance;
				ennemiPlusProche=unite;
			}
			else {
				if(distance<distanceMin) {
					distanceMin=distance;
					ennemiPlusProche=unite;
				}
				if(distance==distanceMin) {
					if(unite.getPV()<ennemiPlusProche.getPV())
						ennemiPlusProche=unite;
				}
			}
		}
		return ennemiPlusProche;
	}
	
	public boolean estPerceptron() {return false;}
	
	public SparseVector encodageEtat(Plateau plateau, Troupes troupe) {return null;}
}
