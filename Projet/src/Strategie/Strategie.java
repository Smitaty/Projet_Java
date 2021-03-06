package Strategie;

import Troupes.*;
import IG.Plateau;
import Perceptron.SparseVector;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe abstraite qui choisit le coup à jouer pour une troupe donnée 
 * et qui actualise les informations du plateau en fonction du coup
 * @author Simon et Rémi
 */

public abstract class Strategie {
	private Plateau plateau;
	
	public Strategie(Plateau plat) {
		plateau=plat;
	}
	
	/**
	 * Méthode abstraite qui retourne l'action que la troupe va effectuée
	 * @param troupe
	 * @return TroupesAction
	 */
	
	public abstract TroupesAction coup(Troupes troupe);
	
	/**
	 * Méthode qui regarde si une action est dans une liste d'actions
	 * @param action
	 * @param coups
	 * @return boolean
	 */
	
	public boolean estPresent(TroupesAction action, ArrayList<TroupesAction> coups) {
		for(int i=0; i<coups.size(); ++i) {
			if(coups.get(i)==action) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Méthode qui retourne si une troupe est dans l'équipe bleue ou non
	 * @param troup
	 * @return boolean
	 */
	
	public boolean estBleu(Troupes troup) {
		if(plateau.getTroupeBleu(troup.getPosition())!=null)
			return true;
		else 
			return false;
	}
	
	/**
	 * Méthode qui retourne si un deplacement aux coordonnées passées est valide ou non
	 * @param coor
	 * @return boolean
	 */
	
	public boolean deplacementValide(Coordonnees coor) {
		int x = coor.getX();
		int y = coor.getY();
		if(x<plateau.getLargeur() && y<plateau.getHauteur() && x>0 && y>0) {
			ArrayList<Troupes> listeTroupesBleu = plateau.getUnite_bleue();
			ArrayList<Troupes> listeTroupesRouge = plateau.getUnite_rouge();
			ArrayList<Coordonnees> listeArbres = plateau.getArbres();
			ArrayList<Coordonnees> listeRochers = plateau.getRochers();
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
	
	/**
	 * Méthode qui retourne si une attaque aux coordonnées passées est valide ou non
	 * @param coor
	 * @param estBleu
	 * @return boolean
	 */
	
	public boolean attaqueValide(Coordonnees coor,boolean estBleu) {
		int x = coor.getX();
		int y = coor.getY();
		if(!estBleu) {
			ArrayList<Troupes> listeTroupes = plateau.getUnite_bleue();
			for(int i=0; i<listeTroupes.size(); ++i) {
				if(listeTroupes.get(i).getPosition().getX()==x && listeTroupes.get(i).getPosition().getY()==y)
					return true;
			}
			return false;
		}
		else {
			ArrayList<Troupes> listeTroupes = plateau.getUnite_rouge();
			for(int i=0; i<listeTroupes.size(); ++i) {
				if(listeTroupes.get(i).getPosition().getX()==x && listeTroupes.get(i).getPosition().getY()==y)
					return true;
			}
			return false;
		}
	}
	
	/**
	 * Méthode qui retourne une liste d'actions en donnant à la troupe la possibilitée de se tourner
	 * dans la bonne direction si un ennemi est à portée d'attaque
	 * @param troupe
	 * @return ArrayList<TroupesAction>
	 */
	
	public ArrayList<TroupesAction> coupsIntelligentsPossibles(Troupes troupe){
		ArrayList<TroupesAction> listecoups = new ArrayList<TroupesAction>();
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		String type = troupe.getType();
		Troupes ennemi=null;
		boolean estBleu = estBleu(troupe);

		if(deplacementValide(new Coordonnees(x,y-1))) {
			listecoups.add(TroupesAction.TOP);
		}
		if(deplacementValide(new Coordonnees(x,y+1))) {
			listecoups.add(TroupesAction.BOTTOM);
		}
		if(deplacementValide(new Coordonnees(x+1,y))) {
			listecoups.add(TroupesAction.RIGHT);
		}
		if(deplacementValide(new Coordonnees(x-1,y))) {
			listecoups.add(TroupesAction.LEFT);
		}
		boolean estPresentAttaque = false;
		if(attaqueValide(new Coordonnees(x,y-1),estBleu)) {
			Troupes ennemiPotentiel=null;
			if(estBleu) {
				ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x,y-1));
			}
			else {
				ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x,y-1));
			}
			listecoups.add(TroupesAction.ATTACK1);
			estPresentAttaque=true;
			troupe.setDirection(Direction.NORD);
			ennemi=ennemiPotentiel;
		}
		if(attaqueValide(new Coordonnees(x,y+1),estBleu)) {
			Troupes ennemiPotentiel=null;
			if(estBleu) {
				ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x,y+1));
			}
			else {
				ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x,y+1));
			}
			if(ennemi==null) {
				if(estPresentAttaque==false) {
					listecoups.add(TroupesAction.ATTACK1);
					estPresentAttaque=true;
				}
				troupe.setDirection(Direction.SUD);
				ennemi=ennemiPotentiel;
			}
			else {
				if(ennemi.getPV()>ennemiPotentiel.getPV()) {
					if(estPresentAttaque==false) {
						listecoups.add(TroupesAction.ATTACK1);
						estPresentAttaque=true;
					}
					troupe.setDirection(Direction.SUD);
					ennemi=ennemiPotentiel;
				}
			}
		}
		if(attaqueValide(new Coordonnees(x+1,y),estBleu)) {
			Troupes ennemiPotentiel=null;
			if(estBleu) {
				ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x+1,y));
			}
			else {
				ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x+1,y));
			}
			if(ennemi==null) {
				if(estPresentAttaque==false) {
					listecoups.add(TroupesAction.ATTACK1);
					estPresentAttaque=true;
				}
				troupe.setDirection(Direction.EST);
				ennemi=ennemiPotentiel;
			}
			else {
				if(ennemi.getPV()>ennemiPotentiel.getPV()) {
					if(estPresentAttaque==false) {
						listecoups.add(TroupesAction.ATTACK1);
						estPresentAttaque=true;
					}
					troupe.setDirection(Direction.EST);
					ennemi=ennemiPotentiel;
				}
			}
		}
		if(attaqueValide(new Coordonnees(x-1,y),estBleu)) {
			Troupes ennemiPotentiel=null;
			if(estBleu) {
				ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x-1,y));
			}
			else {
				ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x-1,y));
			}
			if(ennemi==null) {
				if(estPresentAttaque==false) {
					listecoups.add(TroupesAction.ATTACK1);
					estPresentAttaque=true;
				}
				troupe.setDirection(Direction.OUEST);
				ennemi=ennemiPotentiel;
			}
			else {
				if(ennemi.getPV()>ennemiPotentiel.getPV()) {
					if(estPresentAttaque==false) {
						listecoups.add(TroupesAction.ATTACK1);
						estPresentAttaque=true;
					}
					troupe.setDirection(Direction.OUEST);
					ennemi=ennemiPotentiel;
				}
			}
		}
		if(type=="Archer" || type=="Mage") {
			if(attaqueValide(new Coordonnees(x,y-2),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x,y-1))) {
				Troupes ennemiPotentiel=null;
				if(estBleu) {
					ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x,y-2));
				}
				else {
					ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x,y-2));
				}
				listecoups.add(TroupesAction.ATTACK1);
				estPresentAttaque=true;
				troupe.setDirection(Direction.NORD);
				ennemi=ennemiPotentiel;
			}
			else {
				if(attaqueValide(new Coordonnees(x,y-3),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x,y-1)) && !plateau.ArbreEn(new Coordonnees(x,y-2))) {
					Troupes ennemiPotentiel=null;
					if(estBleu) {
						ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x,y-3));
					}
					else {
						ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x,y-3));
					}
					if(estPresentAttaque==false) {
						listecoups.add(TroupesAction.ATTACK1);
						estPresentAttaque=true;
					}
					troupe.setDirection(Direction.NORD);
					ennemi=ennemiPotentiel;
				}
			}
			if(attaqueValide(new Coordonnees(x,y+2),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x,y+1))) {
				Troupes ennemiPotentiel=null;
				if(estBleu) {
					ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x,y+2));
				}
				else {
					ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x,y+2));
				}
				if(ennemi==null) {
					if(estPresentAttaque==false) {
						listecoups.add(TroupesAction.ATTACK1);
						estPresentAttaque=true;
					}
					troupe.setDirection(Direction.SUD);
					ennemi=ennemiPotentiel;
				}
				else {
					if(ennemi.getPV()>ennemiPotentiel.getPV()) {
						if(estPresentAttaque==false) {
							listecoups.add(TroupesAction.ATTACK1);
							estPresentAttaque=true;
						}
						troupe.setDirection(Direction.SUD);
						ennemi=ennemiPotentiel;
					}
				}
			}
			else {
				if(attaqueValide(new Coordonnees(x,y+3),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x,y+1)) && !plateau.ArbreEn(new Coordonnees(x,y+2))) {
					Troupes ennemiPotentiel=null;
					if(estBleu) {
						ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x,y+3));
					}
					else {
						ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x,y+3));
					}
					if(ennemi==null) {
						if(estPresentAttaque==false) {
							listecoups.add(TroupesAction.ATTACK1);
							estPresentAttaque=true;
						}
						troupe.setDirection(Direction.SUD);
						ennemi=ennemiPotentiel;
					}
					else {
						if(ennemi.getPV()>ennemiPotentiel.getPV()) {
							if(estPresentAttaque==false) {
								listecoups.add(TroupesAction.ATTACK1);
								estPresentAttaque=true;
							}
							troupe.setDirection(Direction.SUD);
							ennemi=ennemiPotentiel;
						}
					}
				}
			}
			if(attaqueValide(new Coordonnees(x-2,y),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x-1,y))) {
				Troupes ennemiPotentiel=null;
				if(estBleu) {
					ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x-2,y));
				}
				else {
					ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x-2,y));
				}
				if(ennemi==null) {
					if(estPresentAttaque==false) {
						listecoups.add(TroupesAction.ATTACK1);
						estPresentAttaque=true;
					}
					troupe.setDirection(Direction.OUEST);
					ennemi=ennemiPotentiel;
				}
				else {
					if(ennemi.getPV()>ennemiPotentiel.getPV()) {
						listecoups.add(TroupesAction.ATTACK1);
						troupe.setDirection(Direction.OUEST);
						ennemi=ennemiPotentiel;
					}
				}
			}
			else {
				if(attaqueValide(new Coordonnees(x-3,y),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x-1,y)) && !plateau.ArbreEn(new Coordonnees(x-2,y))) {
					Troupes ennemiPotentiel=null;
					if(estBleu) {
						ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x-3,y));
					}
					else {
						ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x-3,y));
					}
					if(ennemi==null) {
						if(estPresentAttaque==false) {
							listecoups.add(TroupesAction.ATTACK1);
							estPresentAttaque=true;
						}
						troupe.setDirection(Direction.OUEST);
						ennemi=ennemiPotentiel;
					}
					else {
						if(ennemi.getPV()>ennemiPotentiel.getPV()) {
							if(estPresentAttaque==false) {
								listecoups.add(TroupesAction.ATTACK1);
								estPresentAttaque=true;
							}
							troupe.setDirection(Direction.OUEST);
							ennemi=ennemiPotentiel;
						}
					}
				}
			}
			if(attaqueValide(new Coordonnees(x+2,y),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x+1,y))) {
				Troupes ennemiPotentiel=null;
				if(estBleu) {
					ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x+2,y));
				}
				else {
					ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x+2,y));
				}
				if(ennemi==null) {
					if(estPresentAttaque==false) {
						listecoups.add(TroupesAction.ATTACK1);
						estPresentAttaque=true;
					}
					troupe.setDirection(Direction.EST);
					ennemi=ennemiPotentiel;
				}
				else {
					if(ennemi.getPV()>ennemiPotentiel.getPV()) {
						listecoups.add(TroupesAction.ATTACK1);
						troupe.setDirection(Direction.EST);
						ennemi=ennemiPotentiel;
					}
				}
			}
			else {
				if(attaqueValide(new Coordonnees(x+3,y),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x+1,y)) && !plateau.ArbreEn(new Coordonnees(x+2,y))) {
					Troupes ennemiPotentiel=null;
					if(estBleu) {
						ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x+3,y));
					}
					else {
						ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x+3,y));
					}
					if(ennemi==null) {
						if(estPresentAttaque==false) {
							listecoups.add(TroupesAction.ATTACK1);
							estPresentAttaque=true;
						}
						troupe.setDirection(Direction.EST);
						ennemi=ennemiPotentiel;
					}
					else {
						if(ennemi.getPV()>ennemiPotentiel.getPV()) {
							if(estPresentAttaque==false) {
								listecoups.add(TroupesAction.ATTACK1);
								estPresentAttaque=true;
							}
							troupe.setDirection(Direction.EST);
							ennemi=ennemiPotentiel;
						}
					}
				}
			}
		}
		return listecoups;
	}
	
	/**
	 * Méthode qui retourne une liste d'actions
	 * La troupe peut seulement attaquer dans la direction dans laquelle elle est
	 * @param troupe
	 * @return ArrayList<TroupesAction>
	 */
	
	public ArrayList<TroupesAction> coupsPossibles(Troupes troupe){
		ArrayList<TroupesAction> listecoups = new ArrayList<TroupesAction>();
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		if(deplacementValide(new Coordonnees(x,y-1))) {
			listecoups.add(TroupesAction.TOP);
		}
		if(deplacementValide(new Coordonnees(x,y+1))) {
			listecoups.add(TroupesAction.BOTTOM);
		}
		if(deplacementValide(new Coordonnees(x+1,y))) {
			listecoups.add(TroupesAction.RIGHT);
		}
		if(deplacementValide(new Coordonnees(x-1,y))) {
			listecoups.add(TroupesAction.LEFT);
		}
		if(troupe.getDirection()==Direction.NORD) {
			if(attaqueValide(new Coordonnees(x,y-1),estBleu(troupe))) {
				listecoups.add(TroupesAction.ATTACK1);
			}
			else if (troupe.getType()=="Archer" || troupe.getType()=="Mage") {
				if(attaqueValide(new Coordonnees(x,y-2),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x,y-1))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
				else if(attaqueValide(new Coordonnees(x,y-3),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x,y-1)) && !plateau.ArbreEn(new Coordonnees(x,y-2))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
			}
		}
		else if(troupe.getDirection()==Direction.SUD) {
			if(attaqueValide(new Coordonnees(x,y+1),estBleu(troupe))) {
				listecoups.add(TroupesAction.ATTACK1);
			}
			else if (troupe.getType()=="Archer" || troupe.getType()=="Mage") {
				if(attaqueValide(new Coordonnees(x,y+2),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x,y+1))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
				else if(attaqueValide(new Coordonnees(x,y+3),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x,y+1)) && !plateau.ArbreEn(new Coordonnees(x,y+2))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
			}
		}
		else if(troupe.getDirection()==Direction.EST) {
			if(attaqueValide(new Coordonnees(x+1,y),estBleu(troupe))) {
				listecoups.add(TroupesAction.ATTACK1);
			}
			else if (troupe.getType()=="Archer" || troupe.getType()=="Mage") {
				if(attaqueValide(new Coordonnees(x+2,y),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x+1,y))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
				else if(attaqueValide(new Coordonnees(x+3,y),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x+1,y)) && !plateau.ArbreEn(new Coordonnees(x+2,y))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
			}
		}
		else if(troupe.getDirection()==Direction.OUEST) {
			if(attaqueValide(new Coordonnees(x-1,y),estBleu(troupe))) {
				listecoups.add(TroupesAction.ATTACK1);
			}
			else if (troupe.getType()=="Archer" || troupe.getType()=="Mage") {
				if(attaqueValide(new Coordonnees(x-2,y),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x-1,y))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
				else if(attaqueValide(new Coordonnees(x-3,y),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x-1,y)) && !plateau.ArbreEn(new Coordonnees(x-2,y))) {
					listecoups.add(TroupesAction.ATTACK1);
				}
			}
		}
		return listecoups;
	}
	
	/**
	 * Méthode qui actualise les informations du plateau et de la troupe en fonction de l'action
	 * @param action
	 * @param troupe
	 */
	
	public void jouer(TroupesAction action,Troupes troupe) {
		troupe.setAction(action);
		switch (action) {
			case TOP:
				troupe.setPosition(new Coordonnees(troupe.getPosition().getX(),troupe.getPosition().getY()-1));
				troupe.setDirection(Direction.NORD);
				break;
			case BOTTOM:
				troupe.setPosition(new Coordonnees(troupe.getPosition().getX(),troupe.getPosition().getY()+1));
				troupe.setDirection(Direction.SUD);
				break;
			case LEFT:
				troupe.setPosition(new Coordonnees(troupe.getPosition().getX()-1,troupe.getPosition().getY()));
				troupe.setDirection(Direction.OUEST);
				break;
			case RIGHT:
				troupe.setPosition(new Coordonnees(troupe.getPosition().getX()+1,troupe.getPosition().getY()));
				troupe.setDirection(Direction.EST);
				break;
			case ATTACK1:
				Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu(troupe));
				cible.setPV(cible.getPV()-troupe.getDegats());
				if(cible.getType()!="Chateau") {
					if(cible.getPV()<=0) {
						troupe.setReward(troupe.getReward()+20);
					}
					else {
						troupe.setReward(troupe.getReward()+10);
					}
				}
				else {
					if(cible.getPV()<=0) {
						troupe.setReward(troupe.getReward()+50);
					}
					else {
						troupe.setReward(troupe.getReward()+30);
					}
				}
				break;
			case STOP:
				break;
		}
	}
	
	/**
	 * Méthode qui retourne vrai si la troupe réussi à esquiver ou non
	 * @return boolean
	 */
	
	public boolean esquive() {
		Random r = new Random();
		float esquive = r.nextFloat();
		if(esquive<0.2)
			return true;
		else
			return false;
	}
	
	/**
	 * Méthode qui regarde s'il y a un ennemi à portée de la troupe
	 * @param troupe
	 * @param dir
	 * @param estBleu
	 * @return Troupes
	 */
	
	public Troupes chercheCible(Troupes troupe,Direction dir, boolean estBleu) {
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		String type = troupe.getType();
		Troupes cible = null;
		if(estBleu) {
			switch (dir) {
				case NORD:
					cible = plateau.getTroupeRouge(new Coordonnees(x, y-1));
					if(cible==null && (type=="Archer" || type=="Mage")) {
						cible = plateau.getTroupeRouge(new Coordonnees(x,y-2));
						if(cible==null)
							cible = plateau.getTroupeRouge(new Coordonnees(x,y-3));
					}
					break;
				case SUD:
					cible = plateau.getTroupeRouge(new Coordonnees(x, y+1));
					if(cible==null && (type=="Archer" || type=="Mage")) {
						cible = plateau.getTroupeRouge(new Coordonnees(x,y+2));
						if(cible==null)
							cible = plateau.getTroupeRouge(new Coordonnees(x,y+3));
					}
					break;
				case OUEST:
					cible = plateau.getTroupeRouge(new Coordonnees(x-1, y));
					if(cible==null && (type=="Archer" || type=="Mage")) {
						cible = plateau.getTroupeRouge(new Coordonnees(x-2,y));
						if(cible==null)
							cible = plateau.getTroupeRouge(new Coordonnees(x-3,y));
					}
					break;
				case EST:
					cible = plateau.getTroupeRouge(new Coordonnees(x+1, y));
					if(cible==null && (type=="Archer" || type=="Mage")) {
						cible = plateau.getTroupeRouge(new Coordonnees(x+2,y));
						if(cible==null)
							cible = plateau.getTroupeRouge(new Coordonnees(x+3,y));
					}
					break;
			}
		}
		else {
			switch (dir) {
			case NORD:
				cible = plateau.getTroupeBleu(new Coordonnees(x, y-1));
				if(cible==null && (type=="Archer" || type=="Mage")) {
					cible = plateau.getTroupeBleu(new Coordonnees(x,y-2));
					if(cible==null)
						cible = plateau.getTroupeBleu(new Coordonnees(x,y-3));
				}
				break;
			case SUD:
				cible = plateau.getTroupeBleu(new Coordonnees(x, y+1));
				if(cible==null && (type=="Archer" || type=="Mage")) {
					cible = plateau.getTroupeBleu(new Coordonnees(x,y+2));
					if(cible==null)
						cible = plateau.getTroupeBleu(new Coordonnees(x,y+3));
				}
				break;
			case OUEST:
				cible = plateau.getTroupeBleu(new Coordonnees(x-1, y));
				if(cible==null && (type=="Archer" || type=="Mage")) {
					cible = plateau.getTroupeBleu(new Coordonnees(x-2,y));
					if(cible==null)
						cible = plateau.getTroupeBleu(new Coordonnees(x-3,y));
				}
				break;
			case EST:
				cible = plateau.getTroupeBleu(new Coordonnees(x+1, y));
				if(cible==null && (type=="Archer" || type=="Mage")) {
					cible = plateau.getTroupeBleu(new Coordonnees(x+2,y));
					if(cible==null)
						cible = plateau.getTroupeBleu(new Coordonnees(x+3,y));
				}
				break;
			}
		}
		return cible;
	}

	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}
	
	/**
	 * Méthode abstraite qui retourn vrai si la stratégie est une stratégie perceptron
	 * @return boolean
	 */
	
	public abstract boolean estPerceptron();
	
	public abstract SparseVector encodageEtat(Plateau plateau, Troupes troupe);
	
}