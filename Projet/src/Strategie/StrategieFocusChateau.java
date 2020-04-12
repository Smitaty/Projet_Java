package Strategie;

import IG.Plateau;
import Game.TroupesAction;
import Troupes.*;
import java.util.ArrayList;
/*
 * Les unites se dirigent toujours vers le chateau ennemi et attaquent des qu'un ennemi est a portee.
 * Les unites ne prennent pas en compte les positions des unites ennemies, elles se concentrent uniquemenet sur le chateau ennemi.
 */

public class StrategieFocusChateau extends Strategie{
	
	public StrategieFocusChateau(Plateau plateau) {
		super(plateau);
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
		ArrayList<TroupesAction> coups = this.coupsPossibles(troupe);
		
		if(estPresent(TroupesAction.ATTACK1,coups)) {
			return TroupesAction.ATTACK1;
		}
		
		
		Coordonnees chateauEn = rechercheChateauEn(troupe);

		if(y == chateauEn.getY()) {
			if(x < chateauEn.getX() && estPresent(TroupesAction.RIGHT,coups)) return TroupesAction.RIGHT;
			if(x > chateauEn.getX() && estPresent(TroupesAction.LEFT,coups)) return TroupesAction.LEFT;
		}
		else if(y > chateauEn.getY()) {
			if(estPresent(TroupesAction.TOP,coups)) return TroupesAction.TOP;
			if(x < chateauEn.getX() && estPresent(TroupesAction.RIGHT,coups)) return TroupesAction.RIGHT;
			if(x > chateauEn.getX() && estPresent(TroupesAction.LEFT,coups)) return TroupesAction.LEFT;
		}
		else {
			if(estPresent(TroupesAction.BOTTOM,coups)) return TroupesAction.BOTTOM;
			if(x < chateauEn.getX() && estPresent(TroupesAction.RIGHT,coups)) return TroupesAction.RIGHT;
			if(x > chateauEn.getX() && estPresent(TroupesAction.LEFT,coups)) return TroupesAction.LEFT;
		}
		return TroupesAction.STOP;
		
		
	}
	
	
	
	public Coordonnees rechercheChateauEn(Troupes troupe) {
		ArrayList<Troupes> ennemi;
		if(this.estBleu(troupe))
			ennemi = this.getPlateau().getUnite_rouge();
		else
			ennemi = this.getPlateau().getUnite_bleue();
		
		for(Troupes unite : ennemi) {
			if(unite.getType() == "Chateau") {
				System.out.println("Chateau trouve");
				return unite.getPosition();
			}
		}
		System.out.println("Chateau non trouve");

		return null;
	}
	
	
	
	
	
	
	
}