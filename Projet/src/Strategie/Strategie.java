package Strategie;

import Troupes.*;
import IG.Plateau;
import java.util.ArrayList;
import Game.TroupesAction;

public abstract class Strategie {
	private Plateau plateau;
	
	public Strategie(Plateau plat) {
		plateau=plat;
	}
	
	public abstract TroupesAction coup(Troupes troupe);
	
	public boolean estBleu(Troupes troup) {
		if(plateau.getTroupeBleu(troup)!=null)
			return true;
		else 
			return false;
	}
	
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
	
	public boolean attaqueValide(Coordonnees coor,boolean couleur) {
		int x = coor.getX();
		int y = coor.getY();
		if(!couleur) {
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
	
	public ArrayList<TroupesAction> coupsIntelligentPosssibles(Troupes troupe){
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
		if(type=="Chevalier") {
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
		}
		else {
			boolean estPresentAttaque=false;
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
				if(attaqueValide(new Coordonnees(x,y-3),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x,y+1)) && !plateau.ArbreEn(new Coordonnees(x,y+2))) {
					Troupes ennemiPotentiel=null;
					if(estBleu) {
						ennemiPotentiel=plateau.getTroupeRouge(new Coordonnees(x,y-3));
					}
					else {
						ennemiPotentiel=plateau.getTroupeBleu(new Coordonnees(x,y-3));
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
			if(attaqueValide(new Coordonnees(x+2,y),estBleu(troupe)) && !plateau.ArbreEn(new Coordonnees(x+1,y))) {
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
		}
		return listecoups;
	}
	
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
	
	public void jouer(TroupesAction action,Troupes troupe, boolean estBleu) {
		if(estBleu) {
			Troupes unite = plateau.getTroupeBleu(troupe);
			switch (action) {
				case TOP:
					unite.setPosition(new Coordonnees(unite.getPosition().getX(),unite.getPosition().getY()-1));
					unite.setDirection(Direction.NORD);
					break;
				case BOTTOM:
					unite.setPosition(new Coordonnees(unite.getPosition().getX(),unite.getPosition().getY()+1));
					unite.setDirection(Direction.SUD);
					break;
				case LEFT:
					unite.setPosition(new Coordonnees(unite.getPosition().getX()-1,unite.getPosition().getY()));
					unite.setDirection(Direction.OUEST);
					break;
				case RIGHT:
					unite.setPosition(new Coordonnees(unite.getPosition().getX()+1,unite.getPosition().getY()));
					unite.setDirection(Direction.EST);
					break;
				case ATTACK1:
					Troupes cible = chercheCible(troupe,troupe.getDirection(),true);
					cible.setPV(cible.getPV()-troupe.getDegats());
					if(cible.getPV()<=0)
						if(cible.getType()!="Château")
							plateau.getUnite_rouge().remove(cible);
						else
							System.out.println("Château rouge détruit !");
					break;
				case ATTACK2:
					Troupes cible2 = chercheCible(troupe,troupe.getDirection(),true);
					cible2.setPV(cible2.getPV()-troupe.getDegats()+20);
					if(cible2.getPV()<=0) {
						if(cible2.getType()!="Château")
							plateau.getUnite_rouge().remove(cible2);
						else
							System.out.println("Château rouge détruit !");
					}
					break;
				case STOP:
					break;
			}
		}
		else {
			Troupes unite = plateau.getTroupeRouge(troupe);
			switch (action) {
				case TOP:
					unite.setPosition(new Coordonnees(unite.getPosition().getX(),unite.getPosition().getY()-1));
					unite.setDirection(Direction.NORD);
					break;
				case BOTTOM:
					unite.setPosition(new Coordonnees(unite.getPosition().getX(),unite.getPosition().getY()+1));
					unite.setDirection(Direction.SUD);
					break;
				case LEFT:
					unite.setPosition(new Coordonnees(unite.getPosition().getX()-1,unite.getPosition().getY()));
					unite.setDirection(Direction.OUEST);
					break;
				case RIGHT:
					unite.setPosition(new Coordonnees(unite.getPosition().getX()+1,unite.getPosition().getY()));
					unite.setDirection(Direction.EST);
					break;
				case ATTACK1:
					Troupes cible = chercheCible(troupe,troupe.getDirection(),false);
					cible.setPV(cible.getPV()-troupe.getDegats());
					if(cible.getPV()<=0) {
						if(cible.getType()!="Château")
							plateau.getUnite_bleue().remove(cible);
						else
							System.out.println("Château bleu détruit !");
					}
					break;
				case ATTACK2:
					Troupes cible2 = chercheCible(troupe,troupe.getDirection(),false);
					cible2.setPV(cible2.getPV()-troupe.getDegats());
					if(cible2.getPV()<=0) {
						if(cible2.getType()!="Château")
							plateau.getUnite_bleue().remove(cible2);
						else
							System.out.println("Château bleu détruit !");
					}
					break;
				case STOP:
					break;
			}
		}
	}
	
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
}
