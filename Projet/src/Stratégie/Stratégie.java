package Stratégie;

import Troupes.*;
import IG.Plateau;
import java.util.ArrayList;
import Game.TroupesAction;

public abstract class Stratégie {
	private Troupes troupe;
	private Plateau plateau;
	
	public Stratégie(Troupes troup, Plateau plat) {
		troupe=troup;
		plateau=plat;
	}
	
	public boolean estBleu(Troupes troup) {
		if(plateau.getTroupeBleu(troup)!=null)
			return true;
		else 
			return false;
	}
	
	public boolean deplacementValide(Coordonnées coor) {
		int x = coor.getX();
		int y = coor.getY();
		if(x<=plateau.getLargeur()-1 && y<=plateau.getHauteur()-1 && x>0 && y>0) {
			ArrayList<Troupes> listeTroupesBleu = plateau.getUnité_bleue();
			ArrayList<Troupes> listeTroupesRouge = plateau.getUnité_rouge();
			ArrayList<Coordonnées> listeArbres = plateau.getArbres();
			ArrayList<Coordonnées> listeRochers = plateau.getRochers();
			for(int i=0; i<listeTroupesBleu.size(); ++i) {
				if(listeTroupesBleu.get(i).getPosition().getX()==x && listeTroupesBleu.get(i).getPosition().getY()==y)
					return false;
			}
			for(int i=0; i<listeTroupesRouge.size(); ++i) {
				if(listeTroupesRouge.get(i).getPosition().getX()==x && listeTroupesRouge.get(i).getPosition().getY()==y)
					return false;
			}
			for(int i=0; i<listeArbres.size(); ++i) {
				if(listeArbres.get(i).getX()==x && listeArbres.get(i).getY()==y)
					return false;
			}
			for(int i=0; i<listeRochers.size(); ++i) {
				if(listeRochers.get(i).getX()==x && listeRochers.get(i).getY()==y)
					return false;
			}
			return true;
		}
		else
			return false;
	}
	
	public boolean attaqueValide(Coordonnées coor,boolean couleur) {
		int x = coor.getX();
		int y = coor.getY();
		if(!couleur) {
			ArrayList<Troupes> listeTroupes = plateau.getUnité_bleue();
			for(int i=0; i<listeTroupes.size(); ++i) {
				if(listeTroupes.get(i).getPosition().getX()==x && listeTroupes.get(i).getPosition().getY()==y)
					return true;
			}
			return false;
		}
		else {
			ArrayList<Troupes> listeTroupes = plateau.getUnité_rouge();
			for(int i=0; i<listeTroupes.size(); ++i) {
				if(listeTroupes.get(i).getPosition().getX()==x && listeTroupes.get(i).getPosition().getY()==y)
					return true;
			}
			return false;
		}
	}
	
	public ArrayList<TroupesAction> coupsPossibles(){
		ArrayList<TroupesAction> listecoups = new ArrayList<TroupesAction>();
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		if(deplacementValide(new Coordonnées(x,y-1))) {
			listecoups.add(TroupesAction.TOP);
		}
		if(deplacementValide(new Coordonnées(x,y+1))) {
			listecoups.add(TroupesAction.BOTTOM);
		}
		if(deplacementValide(new Coordonnées(x+1,y))) {
			listecoups.add(TroupesAction.RIGHT);
		}
		if(deplacementValide(new Coordonnées(x-1,y))) {
			listecoups.add(TroupesAction.LEFT);
		}
		if(troupe.getDirection()==Direction.NORD) {
			if(attaqueValide(new Coordonnées(x,y-1),estBleu(troupe))) {
				listecoups.add(TroupesAction.ATTACK1);
			}
			else if (troupe.getType()=="Archer" || troupe.getType()=="Mage") {
				if(attaqueValide(new Coordonnées(x,y-2),estBleu(troupe))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
				else if(attaqueValide(new Coordonnées(x,y-3),estBleu(troupe))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
			}
		}
		else if(troupe.getDirection()==Direction.SUD) {
			if(attaqueValide(new Coordonnées(x,y+1),estBleu(troupe))) {
				listecoups.add(TroupesAction.ATTACK1);
			}
			else if (troupe.getType()=="Archer" || troupe.getType()=="Mage") {
				if(attaqueValide(new Coordonnées(x,y+2),estBleu(troupe))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
				else if(attaqueValide(new Coordonnées(x,y+3),estBleu(troupe))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
			}
		}
		else if(troupe.getDirection()==Direction.EST) {
			if(attaqueValide(new Coordonnées(x+1,y),estBleu(troupe))) {
				listecoups.add(TroupesAction.ATTACK1);
			}
			else if (troupe.getType()=="Archer" || troupe.getType()=="Mage") {
				if(attaqueValide(new Coordonnées(x+2,y),estBleu(troupe))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
				else if(attaqueValide(new Coordonnées(x+3,y),estBleu(troupe))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
			}
		}
		else if(troupe.getDirection()==Direction.OUEST) {
			if(attaqueValide(new Coordonnées(x-1,y),estBleu(troupe))) {
				listecoups.add(TroupesAction.ATTACK1);
			}
			else if (troupe.getType()=="Archer" || troupe.getType()=="Mage") {
				if(attaqueValide(new Coordonnées(x-2,y),estBleu(troupe))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
				else if(attaqueValide(new Coordonnées(x-3,y),estBleu(troupe))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
			}
		}
		return listecoups;
	}
}
