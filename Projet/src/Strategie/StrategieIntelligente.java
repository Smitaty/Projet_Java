package Strategie;

import java.util.ArrayList;
import IG.Plateau;
import Perceptron.SparseVector;
import Troupes.*;


/**
 * Si une troupe est à portée de 2 ennemis ou plus il fuit
 * Si une troupe a ses PV inférieur au dégat de l'ennemi à sa portée il fuit
 * Si le château est en danger la priorité des troupes est de le défendre
 * Avant chaque déplacement on regarde si la case où la troupe veut se déplacer est risqué ou non 
 * grâce au nombre d'ennemis qui serait à la portée de la case où la troupe veut se déplacer
 * @author Simon
 */

public class StrategieIntelligente extends Strategie{
	private Plateau plateau;
	
	public StrategieIntelligente(Plateau plateau) {
		super(plateau);
		this.plateau=plateau;
	}
	
	/**
	 * Méthode qui retourne une action pour la troupe donnée
	 * @param troupe
	 * @return TroupesAction
	 */
	
	public TroupesAction coup(Troupes troupe) {
		boolean estBleu = this.estBleu(troupe);
		TroupesAction action;
		ArrayList<Troupes> ennemis = ennemiAPortee(troupe.getPosition(),estBleu);
		Troupes assaillant = rechercheEnnemi(troupe);
		if(ennemis.size()==0) {
			Troupes chateau;
			if(estBleu) {
				chateau = plateau.getChateau(plateau.getUnite_bleue());
			}
			else {
				chateau = plateau.getChateau(plateau.getUnite_rouge());
			}
			if(estEnDanger(chateau,estBleu) && estABonneDistance(troupe,chateau)) {
				assaillant = rechercheAssaillantChateau(troupe,estBleu);
				action = protegerChateau(troupe,assaillant);
			}
			else {
				action = coupOptimal(troupe,assaillant);
			}
			return action;
		}
		else {
			return coupOptimal(troupe,assaillant);
		}
	}
		
	/**
	 * Méthode qui retourne un coup si le château est attaquer,
	 * ici la troupe se déplace peut importe la dangerosité de la case
	 * @param troupe
	 * @param assaillant
	 * @return TroupesAction
	 */
	
	public TroupesAction protegerChateau(Troupes troupe, Troupes assaillant) {
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		int ax = assaillant.getPosition().getX();
		int ay = assaillant.getPosition().getY();
		String type = troupe.getType();
		Coordonnees gauche = new Coordonnees(x-1,y);
		Coordonnees droite = new Coordonnees(x+1,y);
		Coordonnees bas = new Coordonnees(x,y+1);
		Coordonnees haut = new Coordonnees(x,y-1);
		boolean estBleu = this.estBleu(troupe);
		ArrayList<TroupesAction> coups = this.coupsIntelligentsPossibles(troupe);
		if(coups.size()>0) {
			if(estPresent(TroupesAction.ATTACK1,coups)) {
				return TroupesAction.ATTACK1;
			}
			if(type=="Chevalier") {
				// Si l'ennemi est à une portée de 1 case et dans la diagonale du Chevalier
				if(Math.abs(ax-x)==1 && Math.abs(ay-y)==1) {
					if(this.deplacementValide(new Coordonnees(x,ay))) {
						if(ay<y)
							return TroupesAction.TOP;
						else
							return TroupesAction.BOTTOM;
					}
					if(this.deplacementValide(new Coordonnees(ax,y))) {
						if(ax<x)
							return TroupesAction.LEFT;
						else
							return TroupesAction.RIGHT;
					}
					return TroupesAction.STOP;
				}
				// Gestion si l'ennemi n'est pas à portée
				else {
					if(ay<y && this.deplacementValide(haut)) {
							return TroupesAction.TOP;
					}
					if(ay>y && this.deplacementValide(bas)) {
							return TroupesAction.BOTTOM;
					}	
					if(ax<x && this.deplacementValide(gauche)) {
							return TroupesAction.LEFT;
					}
					if(ax>x && this.deplacementValide(droite)) {
							return TroupesAction.RIGHT;
					}
					else 
						return TroupesAction.STOP;
				}
			}
			if(type=="Archer" || type=="Mage") {
				if(Math.abs(ax-x)==1 && Math.abs(ay-y)<=3) {
					if(ax<x && this.deplacementValide(gauche)){
							return TroupesAction.LEFT;
					}
					if(ax>x && this.deplacementValide(droite)) {
							return TroupesAction.RIGHT;
					}
					if(ay<y && this.deplacementValide(haut)) {
							return TroupesAction.TOP;
					}
					if(ay>y && this.deplacementValide(bas)) {
							return TroupesAction.BOTTOM;
					}
					else
						return TroupesAction.STOP;
				}
				else {
					if(Math.abs(ay-y)>3) {
						if(ay<y && this.deplacementValide(haut)) {
								return TroupesAction.TOP;
						}
						if(ay>y && this.deplacementValide(bas)) {
								return TroupesAction.BOTTOM;
						}
						if(ax<x && this.deplacementValide(gauche)){
								return TroupesAction.LEFT;
						}
						if(ax>x && this.deplacementValide(droite)) {
								return TroupesAction.RIGHT;
						}
						else
							return TroupesAction.STOP;
					}
					else {
						if(ax<x && this.deplacementValide(gauche)){
								return TroupesAction.LEFT;
						}
						if(ax>x && this.deplacementValide(droite)) {
								return TroupesAction.RIGHT;
						}
						if(ay<y && this.deplacementValide(haut)) {
							if(!deplacementDangereux(troupe,haut,estBleu))
								return TroupesAction.TOP;
						}
						if(ay>y && this.deplacementValide(bas)) {
								return TroupesAction.BOTTOM;
						}
						else
							return TroupesAction.STOP;
					}
				}
			}
			return TroupesAction.STOP;
		}
		else {
			return TroupesAction.STOP;
		}
	}
	
	/**
	 * Méthode qui regarde si la situation de la troupe est dangereuse ou non pour attaquer
	 * @param troupe
	 * @param estBleu
	 * @return boolean
	 */
	
	public boolean estDangereux(Troupes troupe, boolean estBleu) {
		int PV = troupe.getPV();
		ArrayList<Troupes> ennemis = ennemiAPortee(troupe.getPosition(),estBleu);
		if(ennemis.size()>=2)
			return true;
		else {
			if(ennemis.size()==1) {
				if(ennemis.get(0).getPV()<=troupe.getDegats() && estAPortee(troupe,ennemis.get(0).getPosition()))
					return false;
				else {
					if(ennemiAPortee(ennemis.get(0).getPosition(),!estBleu).size()>=2)
						return false;
					else {
						if(PV<=ennemis.get(0).getDegats())
							return true;
					}
				}
			}
			return false;
		}
	}
	
	/**
	 * Méthode qui retourne vrai si un déplacement aux coordonnées passées est dangereux pour la troupe ou non
	 * @param troupe
	 * @param pos
	 * @param estBleu
	 * @return boolean
	 */
	
	public boolean deplacementDangereux(Troupes troupe,Coordonnees pos, boolean estBleu) {
		int PV = troupe.getPV();
		ArrayList<Troupes> ennemis = ennemiAPortee(pos,estBleu);
		if(ennemis.size()>=2)
			return true;
		else {
			if(ennemis.size()==0)
				return false;
			if(ennemis.size()==1 && PV>ennemis.get(0).getDegats()) {
				return false;
			}
			else {
				return true;
			}
		}
	}
	
	/**
	 * Méthode qui retourne un coup optimal en fonction de l'environnement de la troupe
	 * pour aller attaquer son assaillant
	 * @param troupe
	 * @param assaillant
	 * @return TroupesAction
	 */
	
	public TroupesAction coupOptimal(Troupes troupe, Troupes assaillant) {
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		String type = troupe.getType();
		int ax = assaillant.getPosition().getX();
		int ay = assaillant.getPosition().getY();
		Coordonnees gauche = new Coordonnees(x-1,y);
		Coordonnees droite = new Coordonnees(x+1,y);
		Coordonnees bas = new Coordonnees(x,y+1);
		Coordonnees haut = new Coordonnees(x,y-1);
		boolean estBleu = this.estBleu(troupe);
		// Gestion des attaques si les troupes sont alignés en x
		if(ax==x) {
			if(!estDangereux(troupe,estBleu)) {
				if(ay==y-1) {
						troupe.setDirection(Direction.NORD);
						return TroupesAction.ATTACK1;
				}
				if(ay==y+1) {
					troupe.setDirection(Direction.SUD);
					return TroupesAction.ATTACK1;
				}
				if(type=="Archer" || type=="Mage") {
					if(ay==y-3) {
						if(!plateau.ArbreEn(new Coordonnees(x,y-2)) && !plateau.ArbreEn(haut)) {
							troupe.setDirection(Direction.NORD);
							return TroupesAction.ATTACK1;	
						}
					}
					if(ay==y-2) {
						if(!plateau.ArbreEn(haut)) {
							troupe.setDirection(Direction.NORD);
							return TroupesAction.ATTACK1;
						}
					}
					if(ay==y+3) {
						if(!plateau.ArbreEn(new Coordonnees(x,y+2)) && !plateau.ArbreEn(bas)) {
							troupe.setDirection(Direction.SUD);
							return TroupesAction.ATTACK1;
						}
					}
					if(ay==y+2) {
						if(!plateau.ArbreEn(bas)) {
							troupe.setDirection(Direction.SUD);
							return TroupesAction.ATTACK1;
						}
					}
				}
			}
			// Gestion des d�placements si les troupes sont alignées en x
			if(ay<y) {
				if(this.deplacementValide(haut)) {
					if(!deplacementDangereux(troupe,haut,estBleu)) {
						return TroupesAction.TOP;
					}
				}
				if(!deplacementDangereux(troupe,gauche,estBleu) && this.deplacementValide(gauche))
					return TroupesAction.LEFT;
				if(!deplacementDangereux(troupe,droite,estBleu) && this.deplacementValide(droite))
					return TroupesAction.RIGHT;
				if(!deplacementDangereux(troupe,bas,estBleu) && this.deplacementValide(bas))
					return TroupesAction.BOTTOM;
				else
					return TroupesAction.STOP;
			}
			if(ay>y) {
				if(this.deplacementValide(bas)) {
					if(!deplacementDangereux(troupe,bas,estBleu)) {
						return TroupesAction.BOTTOM;
					}
				}
				if(!deplacementDangereux(troupe,gauche,estBleu) && this.deplacementValide(gauche))
					return TroupesAction.LEFT;
				if(!deplacementDangereux(troupe,droite,estBleu) && this.deplacementValide(droite))
					return TroupesAction.RIGHT;
				if(!deplacementDangereux(troupe,haut,estBleu) && this.deplacementValide(haut))
					return TroupesAction.TOP;
				else
					return TroupesAction.STOP;
			}
		}
		// Gestion des attaques si les troupes sont alignées en y
		if(ay==y) {
			if(ax==x-1) {
				troupe.setDirection(Direction.OUEST);
				return TroupesAction.ATTACK1;
			}
			if(ax==x+1) {
				troupe.setDirection(Direction.EST);
				return TroupesAction.ATTACK1;
			}
			if(type=="Archer" || type=="Mage") {
				if(ax==x-3) {
					if(!plateau.ArbreEn(new Coordonnees(x-2,y)) && !plateau.ArbreEn(gauche)) {
						troupe.setDirection(Direction.OUEST);
						return TroupesAction.ATTACK1;	
					}
				}
				if(ax==x-2) {
					if(!plateau.ArbreEn(gauche)) {
						troupe.setDirection(Direction.OUEST);
						return TroupesAction.ATTACK1;
					}
				}
				if(ax==x+3) {
					if(!plateau.ArbreEn(new Coordonnees(x+2,y)) && !plateau.ArbreEn(droite)) {
						troupe.setDirection(Direction.EST);
						return TroupesAction.ATTACK1;
					}
				}
				if(ax==x+2) {
					if(!plateau.ArbreEn(droite)) {
						troupe.setDirection(Direction.EST);
						return TroupesAction.ATTACK1;
					}
				}
			}
			// Gestion des d�placements si les troupes sont alignées en y
			if(ax>x) {
				if(this.deplacementValide(droite)) { 
					if(!deplacementDangereux(troupe,droite,estBleu)) {
						return TroupesAction.RIGHT;
					}
				}
				if(!deplacementDangereux(troupe,gauche,estBleu) && this.deplacementValide(gauche))
					return TroupesAction.LEFT;
				if(!deplacementDangereux(troupe,bas,estBleu) && this.deplacementValide(bas))
					return TroupesAction.BOTTOM;
				if(!deplacementDangereux(troupe,haut,estBleu) && this.deplacementValide(haut))
					return TroupesAction.TOP;
				else
					return TroupesAction.STOP;
			}
			if(ax<x) {
				if(this.deplacementValide(gauche)) { 
					if(!deplacementDangereux(troupe,gauche,estBleu)) {
						return TroupesAction.LEFT;
					}
				}
				if(!deplacementDangereux(troupe,bas,estBleu) && this.deplacementValide(bas))
					return TroupesAction.BOTTOM;
				if(!deplacementDangereux(troupe,droite,estBleu) && this.deplacementValide(droite))
					return TroupesAction.RIGHT;
				if(!deplacementDangereux(troupe,haut,estBleu) && this.deplacementValide(haut))
					return TroupesAction.TOP;
				else
					return TroupesAction.STOP;
			}
		}
		// Gestion des d�placements si les troupes sont alignées sur aucun axes
		else {
			// Gestion du cas où la troupe est un Chevalier
			if(type=="Chevalier") {
				// Si l'ennemi est à une portée de 1 case et dans la diagonale du Chevalier
				if(Math.abs(ax-x)==1 && Math.abs(ay-y)==1) {
					if(this.deplacementValide(new Coordonnees(x,ay))) {
						if(!deplacementDangereux(troupe,new Coordonnees(x,ay),estBleu)) {
							if(ay<y)
								return TroupesAction.TOP;
							else
								return TroupesAction.BOTTOM;
						}
					}
					if(this.deplacementValide(new Coordonnees(ax,y))) {
						if(!deplacementDangereux(troupe,new Coordonnees(ax,y),estBleu)) {
							if(ax<x)
								return TroupesAction.LEFT;
							else
								return TroupesAction.RIGHT;
						}
					}
				}
				// Gestion si l'ennemi n'est pas à port�e
				else {
					if(ay<y && this.deplacementValide(haut)) {
						if(!deplacementDangereux(troupe,haut,estBleu))
							return TroupesAction.TOP;
					}
					if(ay>y && this.deplacementValide(bas)) {
						if(!deplacementDangereux(troupe,bas,estBleu))
							return TroupesAction.BOTTOM;
					}	
					if(ax<x && this.deplacementValide(gauche)) {
						if(!deplacementDangereux(troupe,gauche,estBleu))
							return TroupesAction.LEFT;
					}
					if(ax>x && this.deplacementValide(droite)) {
						if(!deplacementDangereux(troupe,droite,estBleu))
							return TroupesAction.RIGHT;
					}
					else
						return TroupesAction.STOP;
				}
			}
			if(type=="Archer" || type=="Mage") {
				if(Math.abs(ax-x)==1 && Math.abs(ay-y)<=3) {
					if(ax<x && this.deplacementValide(gauche)){
						if(!deplacementDangereux(troupe,gauche,estBleu))
							return TroupesAction.LEFT;
					}
					if(ax>x && this.deplacementValide(droite)) {
						if(!deplacementDangereux(troupe,droite,estBleu))
							return TroupesAction.RIGHT;
					}
					if(ay<y && this.deplacementValide(haut)) {
						if(!deplacementDangereux(troupe,haut,estBleu))
							return TroupesAction.TOP;
					}
					if(ay>y && this.deplacementValide(bas)) {
						if(!deplacementDangereux(troupe,bas,estBleu))
							return TroupesAction.BOTTOM;
					}
					else
						return TroupesAction.STOP;
				}
				else {
					if(Math.abs(ay-y)>3) {
						if(ay<y && this.deplacementValide(haut)) {
							if(!deplacementDangereux(troupe,haut,estBleu))
								return TroupesAction.TOP;
						}
						if(ay>y && this.deplacementValide(bas)) {
							if(!deplacementDangereux(troupe,bas,estBleu))
								return TroupesAction.BOTTOM;
						}
						if(ax<x && this.deplacementValide(gauche)){
							if(!deplacementDangereux(troupe,gauche,estBleu))
								return TroupesAction.LEFT;
						}
						if(ax>x && this.deplacementValide(droite)) {
							if(!deplacementDangereux(troupe,droite,estBleu))
								return TroupesAction.RIGHT;
						}
						else
							return TroupesAction.STOP;
					}
					else {
						if(ax<x && this.deplacementValide(gauche)){
							if(!deplacementDangereux(troupe,gauche,estBleu))
								return TroupesAction.LEFT;
						}
						if(ax>x && this.deplacementValide(droite)) {
							if(!deplacementDangereux(troupe,droite,estBleu))
								return TroupesAction.RIGHT;
						}
						if(ay<y && this.deplacementValide(haut)) {
							if(!deplacementDangereux(troupe,haut,estBleu))
								return TroupesAction.TOP;
						}
						if(ay>y && this.deplacementValide(bas)) {
							if(!deplacementDangereux(troupe,bas,estBleu))
								return TroupesAction.BOTTOM;
						}
						else
							return TroupesAction.STOP;
					}
				}
			}
		}
		return TroupesAction.STOP;
	}
	
	/**
	 * Méthode qui retourne vrai si la troupe est à une bonne distance pour aller défendre le château
	 * @param troupe
	 * @param chateau
	 * @return boolean
	 */
	
	public boolean estABonneDistance(Troupes troupe, Troupes chateau) {
		ArrayList<Troupes> ennemis = ennemiAPortee(chateau.getPosition(),estBleu(troupe));
		double d1 = calculDistance(troupe,chateau);
		if(ennemis.size()>0) {
			if(estBleu(troupe)) {
				double d2 = calculDistance (troupe,plateau.getChateau(plateau.getUnite_rouge()));
				if(d1<=d2)
					return true;
				else
					return false;
			}
			else {
				double d2 = calculDistance (troupe,plateau.getChateau(plateau.getUnite_bleue()));
				if(d1<=d2)
					return true;
				else
					return false;
			}
		}
		else 
			return false;
	}
	
	/**
	 * Méthode qui calule la distance entre deux troupes
	 * @param troupe
	 * @param unite
	 * @return double
	 */
	
	public double calculDistance (Troupes troupe, Troupes unite) {
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		int ux = unite.getPosition().getX();
		int uy = unite.getPosition().getY();
		double terme1 = Math.pow((ux-x),2);
		double terme2 = Math.pow((uy-y),2);
		double d = Math.sqrt(terme1+terme2);
		return d;
	}
	
	/**
	 * Méthode qui retourne si le château est à portée d'un ennemi ou non
	 * @param chateau
	 * @param estBleu
	 * @return boolean
	 */
	
	public boolean estEnDanger(Troupes chateau, boolean estBleu) {
		if(chateau.getType()=="Chateau") {
			if(estBleu) {
				ArrayList<Troupes> uniteRouge = plateau.getUnite_rouge();
				int x = chateau.getPosition().getX();
				int y = chateau.getPosition().getY();
				for(Troupes unite : uniteRouge) {
					int ux = unite.getPosition().getX();
					int uy = unite.getPosition().getY();
					if(unite.getType()=="Chevalier") {
						if(ux==x-1 && uy==y)
							return true;
						if(ux==x+1 && uy==y)
							return true;
						if(ux==x && uy==y+1)
							return true;
					}
					if(unite.getType()=="Archer" || unite.getType()=="Mage") {
						if((ux==x-1 || ux==x-2 || ux==x-3) && uy==y)
							return true;
						if((ux==x+1 || ux==x+2 || ux==x+3) && uy==y)
							return true;
						if((uy==y-1 || uy==y-2 || uy==y-3) && ux==x)
							return true;
						if((uy==y+1 || uy==y+2 || uy==y+3) && ux==x)
							return true;
					}
				}
				return false;
			}
			else {
				ArrayList<Troupes> uniteBleue = plateau.getUnite_bleue();
				int x = chateau.getPosition().getX();
				int y = chateau.getPosition().getY();
				for(Troupes unite : uniteBleue) {
					int ux = unite.getPosition().getX();
					int uy = unite.getPosition().getY();
					if(unite.getType()=="Chevalier") {
						if(ux==x-1 && uy==y)
							return true;
						if(ux==x+1 && uy==y)
							return true;
						if(ux==x && uy==y+1)
							return true;
					}
				}
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Méthode qui retourne l'ennemi le plus proche du château et de la troupe
	 * @param troupe
	 * @param estBleu
	 * @return Troupes
	 */
	
	public Troupes rechercheAssaillantChateau(Troupes troupe, boolean estBleu) {
		if(estBleu) {
			Troupes ennemi = null;
			Troupes chateau = plateau.getChateau(plateau.getUnite_bleue());
			int cx = chateau.getPosition().getX();
			int cy = chateau.getPosition().getY();
			ArrayList<Troupes> uniteRouge = plateau.getUnite_rouge();
			for(Troupes unite : uniteRouge) {
				int ux = unite.getPosition().getX();
				int uy = unite.getPosition().getY();
				if(Math.abs(ux-cx)<6 && Math.abs(uy-cy)<6) {
					if(ennemi == null)
						ennemi = unite;
					else {
						double d1 = calculDistance(troupe,unite);
						double d2 = calculDistance(troupe,ennemi);
						if(d1<d2)
							ennemi=unite;
						else {
							if(d1==d2) {
								if(unite.getPV()<ennemi.getPV())
									ennemi=unite;
								else {
									if(unite.getPV()==ennemi.getPV()) {
										if((unite.getType()=="Archer" || unite.getType()=="Mage") && (ennemi.getType()=="Chevalier")) {
											ennemi=unite;
										}
									}
								}
							}
						}
					}
				}
			}
			return ennemi;
		}
		else {
			Troupes ennemi = null;
			Troupes chateau = plateau.getChateau(plateau.getUnite_bleue());
			int cx = chateau.getPosition().getX();
			int cy = chateau.getPosition().getY();
			ArrayList<Troupes> uniteBleue = plateau.getUnite_bleue();
			for(Troupes unite : uniteBleue) {
				int ux = unite.getPosition().getX();
				int uy = unite.getPosition().getY();
				if(Math.abs(ux-cx)<6 && Math.abs(uy-cy)<6) {
					if(ennemi == null)
						ennemi = unite;
					else {
						double d1 = calculDistance(troupe,unite);
						double d2 = calculDistance(troupe,ennemi);
						if(d1<d2)
							ennemi=unite;
						else {
							if(d1==d2) {
								if(unite.getPV()<ennemi.getPV())
									ennemi=unite;
								else {
									if(unite.getPV()==ennemi.getPV()) {
										if((unite.getType()=="Archer" || unite.getType()=="Mage") && (ennemi.getType()=="Chevalier")) {
											ennemi=unite;
										}
									}
								}
							}
						}
					}
				}
			}
			return ennemi;
		}
	}
	
	/**
	 * Méthode qui retourne une liste des ennemis à portée des coordonnées passées
	 * @param coor
	 * @param estBleu
	 * @return ArrayList<Troupes>
	 */
	
	public ArrayList<Troupes> ennemiAPortee(Coordonnees coor, boolean estBleu) {
		int x = coor.getX();
		int y = coor.getY();
		ArrayList<Troupes> ennemis = new ArrayList<Troupes>();
		if(estBleu) {
			ArrayList<Troupes> uniteRouge = plateau.getUnite_rouge();
			for(Troupes unite : uniteRouge) {
				int ux = unite.getPosition().getX();
				int uy = unite.getPosition().getY();
				String type = unite.getType();
				if(type!="Chateau") {
					if(ux==x) {
						if(uy==y-1 || uy==y+1) {
							ennemis.add(unite);
						}
						else {
							if(type=="Archer" || type=="Mage") {
								if(uy==y-3) {
									if(!plateau.ArbreEn(new Coordonnees(x,y-2)) && !plateau.ArbreEn(new Coordonnees(x,y-1))) {
											ennemis.add(unite);
									}
								}
								if(uy==y-2) {
									if(!plateau.ArbreEn(new Coordonnees(x,y-1))) {
											ennemis.add(unite);
									}
								}
								if(uy==y+3) {
									if(!plateau.ArbreEn(new Coordonnees(x,y+2)) && !plateau.ArbreEn(new Coordonnees(x,y+1))) {
										ennemis.add(unite);
									}
								}
								if(uy==y+2) {
									if(!plateau.ArbreEn(new Coordonnees(x,y+1))) {
										ennemis.add(unite);
									}
								}
							}
						}
					}
					if(uy==y) {
						if(ux==x-1 || ux==x+1) {
							ennemis.add(unite);
						}
						if(type=="Archer" || type=="Mage") {
							if(ux==x-3) {
								if(!plateau.ArbreEn(new Coordonnees(x-2,y)) && !plateau.ArbreEn(new Coordonnees(x-1,y))){
									ennemis.add(unite);
								}
							}
							if(ux==x-2) {
								if(!plateau.ArbreEn(new Coordonnees(x-1,y))) {
									ennemis.add(unite);
								}
							}
							if(ux==x+3) {
								if(!plateau.ArbreEn(new Coordonnees(x+2,y)) && !plateau.ArbreEn(new Coordonnees(x+1,y))){
									ennemis.add(unite);
								}
							}
							if(ux==x+2) {
								if(!plateau.ArbreEn(new Coordonnees(x+1,y))) {
									ennemis.add(unite);
								}
							}
						}
					}
				}
			}
			return ennemis;
		}
		else {
			ArrayList<Troupes> uniteBleue = plateau.getUnite_bleue();
			for(Troupes unite : uniteBleue) {
				int ux = unite.getPosition().getX();
				int uy = unite.getPosition().getY();
				String type = unite.getType();
				if(ux==x) {
					if(uy==y-1 || uy==y+1) {
						ennemis.add(unite);
					}
					else {
						if(type=="Archer" || type=="Mage") {
							if(uy==y-3) {
								if(!plateau.ArbreEn(new Coordonnees(x,y-2)) && !plateau.ArbreEn(new Coordonnees(x,y-1))) {
										ennemis.add(unite);
								}
							}
							if(uy==y-2) {
								if(!plateau.ArbreEn(new Coordonnees(x,y-1))) {
										ennemis.add(unite);
								}
							}
							if(uy==y+3) {
								if(!plateau.ArbreEn(new Coordonnees(x,y+2)) && !plateau.ArbreEn(new Coordonnees(x,y+1))) {
									ennemis.add(unite);
								}
							}
							if(uy==y+2) {
								if(!plateau.ArbreEn(new Coordonnees(x,y+1))) {
									ennemis.add(unite);
								}
							}
						}
					}
				}
				if(uy==y) {
					if(ux==x-1 || ux==x+1) {
						ennemis.add(unite);
					}
					if(type=="Archer" || type=="Mage") {
						if(ux==x-3) {
							if(!plateau.ArbreEn(new Coordonnees(x-2,y)) && !plateau.ArbreEn(new Coordonnees(x-1,y))){
								ennemis.add(unite);
							}
						}
						if(ux==x-2) {
							if(!plateau.ArbreEn(new Coordonnees(x-1,y))) {
								ennemis.add(unite);
							}
						}
						if(ux==x+3) {
							if(!plateau.ArbreEn(new Coordonnees(x+2,y)) && !plateau.ArbreEn(new Coordonnees(x+1,y))){
								ennemis.add(unite);
							}
						}
						if(ux==x+2) {
							if(!plateau.ArbreEn(new Coordonnees(x+1,y))) {
								ennemis.add(unite);
							}
						}
					}
				}
			}
			return ennemis;
		}
	}	
	
	/**
	 * Méthode qui retourne vrai si un ennemi est à portée d'attaque de la troupe
	 * @param troupe
	 * @param pos
	 * @return boolean
	 */
	
	public boolean estAPortee(Troupes troupe, Coordonnees pos) {
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		int px = pos.getX();
		int py = pos.getY();
		if(troupe.getType()=="Chevalier") {
			if((px==x+1 || px==x-1) && py==y)
				return true;
			if((py==y-1 || py==y+1) && px==x)
				return true;
			return false;
		}
		else {
			if(troupe.getType()=="Archer" || troupe.getType()=="Mage") {
				if((px==x+1 || px==x-1 || px==x+2 || px==x-2 || px==x+3 || px==x-3) && py==y)
					return true;
				if((py==y-1 || py==y+1 || py==y+2 || py==y-2 || py==y+3 || py==y-3) && px==x)
					return true;
				return false;
			}
		}
		return false;
	}	
	
	/**
	 * Méthode qui retourne l'ennemi le plus proche de la troupe
	 * @param troupe
	 * @return Troupes
	 */
	
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
