package Strategie;

import IG.Plateau;
import Perceptron.SparseVector;
import Troupes.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Si un ennemi peut attaquer le chevalier celui-ci recule.
 * Les archers et mages se regroupent derriere les chevaliers.
 * Les unites attaquent des qu'elles le peuvent.
 * @author Rémi
 */

public class StrategieGroupe extends Strategie{
	public StrategieGroupe(Plateau plateau) {
		super(plateau);
	}
	
	/**
	 * Méthode qui retourne une action pour la troupe donnée
	 * @param troupe
	 * @return TroupesAction
	 */
	
	public TroupesAction coup(Troupes troupe) {
		ArrayList<TroupesAction> coups = this.coupsPossibles(troupe);
		String color;
		if(this.estBleu(troupe)) color = "bleu";
		else color = "rouge";
		boolean ennemisTousMorts = true;
		if(color == "bleu") {
			for(Troupes unite : this.getPlateau().getUnite_rouge()) {
				if(unite.getType() != "Chateau") ennemisTousMorts = false;
			}
		}
		if(color == "rouge") {
			for(Troupes unite : this.getPlateau().getUnite_bleue()) {
				if(unite.getType() != "Chateau") ennemisTousMorts = false;
			}
		}
		if(ennemisTousMorts) {		
			Coordonnees chateauEn = rechercheChateauEn(troupe);

			if(chateauEn.getY() == troupe.getPosition().getY()) {
				if(chateauEn.getX() == troupe.getPosition().getX()+1 || chateauEn.getX() == troupe.getPosition().getX()-1) return TroupesAction.ATTACK1;
			}
			if(chateauEn.getX() == troupe.getPosition().getX()) {
				if(chateauEn.getY() == troupe.getPosition().getY()+1 || chateauEn.getY() == troupe.getPosition().getY()-1) return TroupesAction.ATTACK1;
			}		
			if(troupe.getPosition().getY() == chateauEn.getY()) {
				if(troupe.getPosition().getX() < chateauEn.getX() && this.estPresent(TroupesAction.RIGHT,coups)) return TroupesAction.RIGHT;
				if(troupe.getPosition().getX() > chateauEn.getX() && this.estPresent(TroupesAction.LEFT,coups)) return TroupesAction.LEFT;
			}
			else if(troupe.getPosition().getY() > chateauEn.getY()) {
				if(this.estPresent(TroupesAction.TOP,coups)) return TroupesAction.TOP;
				if(troupe.getPosition().getX() < chateauEn.getX() && this.estPresent(TroupesAction.RIGHT,coups)) return TroupesAction.RIGHT;
				if(troupe.getPosition().getX() > chateauEn.getX() && this.estPresent(TroupesAction.LEFT,coups)) return TroupesAction.LEFT;
			}
			else {
				if(this.estPresent(TroupesAction.BOTTOM,coups)) return TroupesAction.BOTTOM;
				if(troupe.getPosition().getX() < chateauEn.getX() && this.estPresent(TroupesAction.RIGHT,coups)) return TroupesAction.RIGHT;
				if(troupe.getPosition().getX() > chateauEn.getX() && this.estPresent(TroupesAction.LEFT,coups)) return TroupesAction.LEFT;
			}
			return TroupesAction.STOP;
		}
		else {
			////////////////// CHEVALIER 
			if(troupe.getType() == "Chevalier") {
				// Si le chevalier peut se faire attaquer
				if(this.estPresent(TroupesAction.ATTACK1,coups)) {
					return TroupesAction.ATTACK1;
				}
				else if(peutEtreAttaque(troupe.getPosition(), color)) {
					if(color == "bleu") {
						if(this.estPresent(TroupesAction.TOP,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX(),troupe.getPosition().getY()+1), color)) {
							return TroupesAction.TOP;
						}
						else if(this.estPresent(TroupesAction.ATTACK1,coups)) {
							return TroupesAction.ATTACK1;
						}
						else if(this.estPresent(TroupesAction.BOTTOM,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX(),troupe.getPosition().getY()-1), color)) {
							return TroupesAction.BOTTOM;
						}
					}
					else {
						if(this.estPresent(TroupesAction.BOTTOM,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX(),troupe.getPosition().getY()-1), color)) {
							return TroupesAction.BOTTOM;
						}
						else if(this.estPresent(TroupesAction.ATTACK1,coups)) {
							return TroupesAction.ATTACK1;
						}
						else if(this.estPresent(TroupesAction.TOP,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX(),troupe.getPosition().getY()+1), color)) {
							return TroupesAction.TOP;
						}					
					}
					if(this.estPresent(TroupesAction.RIGHT,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX()+1,troupe.getPosition().getY()), color)) {
						return TroupesAction.RIGHT;
					}
					else if(this.estPresent(TroupesAction.LEFT,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX()-1,troupe.getPosition().getY()), color)) {
						return TroupesAction.LEFT;
					}
				}
				// S'il ne peut pas se faire attaquer
				else {
					if(this.estPresent(TroupesAction.ATTACK1,coups)) {
						return TroupesAction.ATTACK1;
					}
					else if(color == "bleu") {
						if(this.estPresent(TroupesAction.BOTTOM,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX(),troupe.getPosition().getY()-1), color)) {
							return TroupesAction.BOTTOM;
						}
						else return TroupesAction.STOP;
					}
					else {
						if(this.estPresent(TroupesAction.TOP,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX(),troupe.getPosition().getY()+1), color)) {
							return TroupesAction.TOP;
						}
						else return TroupesAction.STOP;
					}
				}
			}
			////////////////// ARCHER
			else if (troupe.getType() == "Archer") {
				if(this.estPresent(TroupesAction.ATTACK1,coups)) {
					return TroupesAction.ATTACK1;
				}
				else if(peutEtreAttaque(troupe.getPosition(), color)) { // Cas ou l'archer est a portee d'un chevalier : il ne peut pas attaquer mais peut se faire attaquer
					if(color == "bleu") {
						if(this.estPresent(TroupesAction.TOP,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX(),troupe.getPosition().getY()+1), color)) {
							return TroupesAction.TOP;
						}
						else if(this.estPresent(TroupesAction.BOTTOM,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX(),troupe.getPosition().getY()-1), color)) {
							return TroupesAction.BOTTOM;
						}
						else if(this.estPresent(TroupesAction.LEFT,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX()-1,troupe.getPosition().getY()), color)) {
							return TroupesAction.LEFT;
						}
						else if(this.estPresent(TroupesAction.RIGHT,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX()+1,troupe.getPosition().getY()), color)) {
							return TroupesAction.RIGHT;
						}
						else return TroupesAction.STOP;
					}
					else {
						if(this.estPresent(TroupesAction.BOTTOM,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX(),troupe.getPosition().getY()-1), color)) {
							return TroupesAction.BOTTOM;
						}
						else if(this.estPresent(TroupesAction.TOP,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX(),troupe.getPosition().getY()+1), color)) {
							return TroupesAction.TOP;
						}
						else if(this.estPresent(TroupesAction.LEFT,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX()-1,troupe.getPosition().getY()), color)) {
							return TroupesAction.LEFT;
						}
						else if(this.estPresent(TroupesAction.RIGHT,coups) && !peutEtreAttaque(new Coordonnees(troupe.getPosition().getX()+1,troupe.getPosition().getY()), color)) {
							return TroupesAction.RIGHT;
						}
						else return TroupesAction.STOP;					
					}
				}
				// S'il ne peut pas se faire attaquer
				else {
					Troupes chevalierPlusProche = chevalierAllie(troupe);
					if(chevalierPlusProche!=null) {
						if(color == "bleu") {
							if(chevalierPlusProche.getPosition().getY() - troupe.getPosition().getY() > 2) {
								if(this.estPresent(TroupesAction.BOTTOM,coups)) {
									return TroupesAction.BOTTOM;
								}
								else if(chevalierPlusProche.getPosition().getX()-1 > troupe.getPosition().getX()) {
									if(this.estPresent(TroupesAction.RIGHT,coups)) {
										return TroupesAction.RIGHT;
									}
								}
								else if(chevalierPlusProche.getPosition().getX()+1 < troupe.getPosition().getX()) {
									if(this.estPresent(TroupesAction.LEFT,coups)) {
										return TroupesAction.LEFT;
									}
								}
								else return TroupesAction.STOP;
							}
							else if(chevalierPlusProche.getPosition().getX()-1 > troupe.getPosition().getX()) {
								if(this.estPresent(TroupesAction.RIGHT,coups)) {
									return TroupesAction.RIGHT;
								}
							}
							else if(chevalierPlusProche.getPosition().getX()+1 < troupe.getPosition().getX()) {
								if(this.estPresent(TroupesAction.LEFT,coups)) {
									return TroupesAction.LEFT;
								}
							}
							else return TroupesAction.STOP;
						}
						else {
							if(chevalierPlusProche.getPosition().getY() - troupe.getPosition().getY() > 2) {
								if(this.estPresent(TroupesAction.TOP,coups)) {
									return TroupesAction.TOP;
								}
								else if(chevalierPlusProche.getPosition().getX()-1 > troupe.getPosition().getX()) {
									if(this.estPresent(TroupesAction.RIGHT,coups)) {
										return TroupesAction.RIGHT;
									}
								}
								else if(chevalierPlusProche.getPosition().getX()+1 < troupe.getPosition().getX()) {
									if(this.estPresent(TroupesAction.LEFT,coups)) {
										return TroupesAction.LEFT;
									}
								}
								else return TroupesAction.STOP;
							}
							else if(chevalierPlusProche.getPosition().getX()-1 > troupe.getPosition().getX()) {
								if(this.estPresent(TroupesAction.RIGHT,coups)) {
									return TroupesAction.RIGHT;
								}
							}
							else if(chevalierPlusProche.getPosition().getX()+1 < troupe.getPosition().getX()) {
								if(this.estPresent(TroupesAction.LEFT,coups)) {
									return TroupesAction.LEFT;
								}
							}
							else return TroupesAction.STOP;
							
						}
					}
					else {
						ArrayList<TroupesAction> allCoups = coupsPossibles(troupe);
						if(this.estPresent(TroupesAction.ATTACK1, allCoups))
							return TroupesAction.ATTACK1;
						else {
							int nbcoups = allCoups.size();
							Random r = new Random();
							int coup = r.nextInt(nbcoups);
							return allCoups.get(coup);
						}
					}
				}
			}
			////////////////// MAGE
			else if(troupe.getType() == "Mage") {
				if(this.estPresent(TroupesAction.ATTACK1,coups)) {
					return TroupesAction.ATTACK1;
				}
				else {
					int xMage = troupe.getPosition().getX();
					int yMage = troupe.getPosition().getY();
					Troupes enPlusProche = rechercheEnnemi(troupe);
					int enX = enPlusProche.getPosition().getX();
					int enY = enPlusProche.getPosition().getY();
					if(enY < troupe.getPosition().getY()) {
						Coordonnees posTest = new Coordonnees(xMage, yMage+1);
						if(this.estPresent(TroupesAction.TOP,coups) && !peutEtreAttaque(posTest, color)) {
							return TroupesAction.TOP;
						}
					}
					else if(enY > troupe.getPosition().getY()) {
						Coordonnees posTest = new Coordonnees(xMage, yMage-1);
						if(this.estPresent(TroupesAction.BOTTOM,coups) && !peutEtreAttaque(posTest, color)) {
							return TroupesAction.BOTTOM;
						}
					}
					else if(enX < troupe.getPosition().getX()) {
						Coordonnees posTest = new Coordonnees(xMage+1,yMage);
						if(this.estPresent(TroupesAction.RIGHT,coups) && !peutEtreAttaque(posTest, color)) {
							return TroupesAction.RIGHT;
						}
					}
					else if(enX > troupe.getPosition().getX()) {
						Coordonnees posTest = new Coordonnees(xMage-1, yMage);
						if(this.estPresent(TroupesAction.LEFT,coups) && !peutEtreAttaque(posTest, color)) {
							return TroupesAction.LEFT;
						}
					}
				}
			}
		}
		return TroupesAction.STOP;
	}
	
	/**
	 * Méthode qui retourne la position du château ennemi
	 * @param troupe
	 * @return Coordonnees
	 */
	
	public Coordonnees rechercheChateauEn(Troupes troupe) {
		ArrayList<Troupes> ennemi;
		if(this.estBleu(troupe))
			ennemi = this.getPlateau().getUnite_rouge();
		else
			ennemi = this.getPlateau().getUnite_bleue();
		
		for(Troupes unite : ennemi) {
			if(unite.getType() == "Chateau") {
				return unite.getPosition();
			}
		}

		return null;
	}
	
	/**
	 * Méthode qui retourne le chevalier allié le plus proche
	 * @param troupe
	 * @return Troupes
	 */
	
	public Troupes chevalierAllie(Troupes troupe) {
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		double distanceMin=-1;
		Troupes chevalierPlusProche=null;
		ArrayList<Troupes> allie;
		if(this.estBleu(troupe))
			allie = this.getPlateau().getUnite_bleue();
		else
			allie = this.getPlateau().getUnite_rouge();
		
		for(Troupes unite : allie) {
			if(unite.getType() == "Chevalier") {
				int ux = unite.getPosition().getX();
				int uy = unite.getPosition().getY();
				double terme1 = Math.pow((ux-x),2);
				double terme2 = Math.pow((uy-y),2);
				double distance = Math.sqrt(terme1+terme2);
				if(distanceMin==-1) {
					distanceMin=distance;
					chevalierPlusProche=unite;
				}
				else {
					if(distance<distanceMin) {
						distanceMin=distance;
						chevalierPlusProche=unite;
					}
				}
			}
		}
		return chevalierPlusProche;
	}
	
	/**
	 * Méthode qui retourne vrai si la position passée est à portée d'ennemis ou non
	 * @param pos
	 * @param couleur
	 * @return boolean
	 */
	
	public boolean peutEtreAttaque(Coordonnees pos, String couleur) {
		ArrayList<Troupes> ennemi;
		if(couleur == "bleu")
			ennemi = this.getPlateau().getUnite_rouge();
		else
			ennemi = this.getPlateau().getUnite_bleue();
		for(Troupes unite : ennemi) {
			if(this.estPresent(TroupesAction.ATTACK1,this.coupsPossibles(unite))) {
				if(unite.getType() == "Chevalier") {
					if(pos.getX() == unite.getPosition().getX()+1 && pos.getY() == unite.getPosition().getY()) return true;
					if(pos.getX() == unite.getPosition().getX()-1 && pos.getY() == unite.getPosition().getY()) return true;
					if(pos.getX() == unite.getPosition().getX() && pos.getY() == unite.getPosition().getY()+1) return true;
					if(pos.getX() == unite.getPosition().getX() && pos.getY() == unite.getPosition().getY()-1) return true;
				}
				else if(unite.getType() == "Archer") {
					if(pos.getX() == unite.getPosition().getX()+2 && pos.getY() == unite.getPosition().getY()) return true;
					if(pos.getX() == unite.getPosition().getX()+3 && pos.getY() == unite.getPosition().getY()) return true;
					if(pos.getX() == unite.getPosition().getX()-2 && pos.getY() == unite.getPosition().getY()) return true;
					if(pos.getX() == unite.getPosition().getX()-3 && pos.getY() == unite.getPosition().getY()) return true;
					if(pos.getX() == unite.getPosition().getX() && pos.getY() == unite.getPosition().getY()+2) return true;
					if(pos.getX() == unite.getPosition().getX() && pos.getY() == unite.getPosition().getY()+3) return true;
					if(pos.getX() == unite.getPosition().getX() && pos.getY() == unite.getPosition().getY()-2) return true;
					if(pos.getX() == unite.getPosition().getX() && pos.getY() == unite.getPosition().getY()-3) return true;
				}	
				else if(unite.getType() == "Mage") {
					if(pos.getX() == unite.getPosition().getX()+1 && pos.getY() == unite.getPosition().getY()) return true;
					if(pos.getX() == unite.getPosition().getX()-1 && pos.getY() == unite.getPosition().getY()) return true;
					if(pos.getX() == unite.getPosition().getX()+2 && pos.getY() == unite.getPosition().getY()) return true;
					if(pos.getX() == unite.getPosition().getX()+3 && pos.getY() == unite.getPosition().getY()) return true;
					if(pos.getX() == unite.getPosition().getX()-2 && pos.getY() == unite.getPosition().getY()) return true;
					if(pos.getX() == unite.getPosition().getX()-3 && pos.getY() == unite.getPosition().getY()) return true;
					
					if(pos.getX() == unite.getPosition().getX() && pos.getY() == unite.getPosition().getY()+1) return true;
					if(pos.getX() == unite.getPosition().getX() && pos.getY() == unite.getPosition().getY()-1) return true;
					if(pos.getX() == unite.getPosition().getX() && pos.getY() == unite.getPosition().getY()+2) return true;
					if(pos.getX() == unite.getPosition().getX() && pos.getY() == unite.getPosition().getY()+3) return true;
					if(pos.getX() == unite.getPosition().getX() && pos.getY() == unite.getPosition().getY()-2) return true;
					if(pos.getX() == unite.getPosition().getX() && pos.getY() == unite.getPosition().getY()-3) return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Méthode qui recherche l'ennemi le plus proche de la troupe donnée
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
			ennemi = this.getPlateau().getUnite_rouge();
		else
			ennemi = this.getPlateau().getUnite_bleue();
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