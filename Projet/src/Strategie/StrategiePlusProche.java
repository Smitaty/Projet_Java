package Strategie;

import IG.Plateau;
import Troupes.Troupes;
import Game.*;
import java.util.ArrayList;
import java.util.Random;

public class StrategiePlusProche extends Strategie{
	private Plateau plateau;
	
	public StrategiePlusProche(Plateau plateau) {
		super(plateau);
		this.plateau=plateau;
	}
	
	public boolean estPresent(TroupesAction action, ArrayList<TroupesAction> coups) {
		for(int i=0; i<coups.size(); ++i) {
			if(coups.get(i)==action) {
				return true;
			}
		}
		return false;
	}
	
	public TroupesAction coup(Troupes troupe) {
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		Troupes ennemi = rechercheEnnemi(troupe);
		int ex = ennemi.getPosition().getX();
		int ey = ennemi.getPosition().getY();
		
		ArrayList<TroupesAction> coups = this.coupsIntelligentPosssibles(troupe);
		if(estPresent(TroupesAction.ATTACK1,coups)) {
			return TroupesAction.ATTACK1;
		}
		if(y<ey) {
			if(estPresent(TroupesAction.BOTTOM,coups))
				return TroupesAction.BOTTOM;
			int r = new Random().nextInt(2);
			if(r==0 && estPresent(TroupesAction.LEFT,coups)) {
				return TroupesAction.LEFT;
			}
			else {
				if(estPresent(TroupesAction.RIGHT,coups))
					return TroupesAction.RIGHT;
			}
		}
		if(y>ey) {
			if(estPresent(TroupesAction.TOP,coups))
				return TroupesAction.TOP;
			int r = new Random().nextInt(2);
			if(r==0 && estPresent(TroupesAction.LEFT,coups)) {
				return TroupesAction.LEFT;
			}
			else {
				if(estPresent(TroupesAction.RIGHT,coups))
					return TroupesAction.RIGHT;
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
		System.out.println("Random");
		int coup = new Random().nextInt(coups.size());
		return coups.get(coup);
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
		System.out.println(ennemiPlusProche.toString());
		return ennemiPlusProche;
	}
}
