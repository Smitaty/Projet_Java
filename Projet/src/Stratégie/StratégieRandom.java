package Stratégie;

import IG.Plateau;
import Game.TroupesAction;
import Troupes.*;
import java.util.ArrayList;
import java.util.Random;

public class StratégieRandom extends Stratégie{
	private Plateau plateau;
	public StratégieRandom(Troupes troupes, Plateau plateau) {
		super(troupes,plateau);
		this.plateau=plateau;
	}

	public TroupesAction coupRandom() {
		ArrayList<TroupesAction> coups = coupsPossibles();
		for(int i=0; i<coups.size(); ++i) {
			if(coups.get(i)==TroupesAction.ATTACK1)
				return TroupesAction.ATTACK1;
		}
		int nbcoups = coups.size();
		Random r = new Random();
		int coup = r.nextInt(nbcoups);
		return coups.get(coup);
	}
	
	public void jouer(TroupesAction action,Troupes troupe, boolean estBleu) {
		if(estBleu) {
			Troupes unité = plateau.getTroupeBleu(troupe);
			switch (action) {
				case TOP:
					unité.setPosition(new Coordonnées(unité.getPosition().getX(),unité.getPosition().getY()-1));
					unité.setDirection(Direction.NORD);
					break;
				case BOTTOM:
					unité.setPosition(new Coordonnées(unité.getPosition().getX(),unité.getPosition().getY()+1));
					unité.setDirection(Direction.SUD);
					break;
				case LEFT:
					unité.setPosition(new Coordonnées(unité.getPosition().getX()-1,unité.getPosition().getY()));
					unité.setDirection(Direction.OUEST);
					break;
				case RIGHT:
					unité.setPosition(new Coordonnées(unité.getPosition().getX()+1,unité.getPosition().getY()));
					unité.setDirection(Direction.EST);
					break;
				case ATTACK1:
					Troupes cible = chercheCible(troupe,troupe.getDirection(),true);
					cible.setPV(cible.getPV()-troupe.getDegats());
					if(cible.getPV()<=0)
						if(cible.getType()!="Château")
							plateau.getUnité_rouge().remove(cible);
						else
							System.out.println("Château rouge détruit !");
					break;
				case ATTACK2:
					Troupes cible2 = chercheCible(troupe,troupe.getDirection(),true);
					cible2.setPV(cible2.getPV()-troupe.getDegats()+20);
					if(cible2.getPV()<=0) {
						if(cible2.getType()!="Château")
							plateau.getUnité_rouge().remove(cible2);
						else
							System.out.println("Château rouge détruit !");
					}
					break;
				case STOP:
					break;
			}
		}
		else {
			Troupes unité = plateau.getTroupeRouge(troupe);
			switch (action) {
				case TOP:
					unité.setPosition(new Coordonnées(unité.getPosition().getX(),unité.getPosition().getY()-1));
					unité.setDirection(Direction.NORD);
					break;
				case BOTTOM:
					unité.setPosition(new Coordonnées(unité.getPosition().getX(),unité.getPosition().getY()+1));
					unité.setDirection(Direction.SUD);
					break;
				case LEFT:
					unité.setPosition(new Coordonnées(unité.getPosition().getX()-1,unité.getPosition().getY()));
					unité.setDirection(Direction.OUEST);
					break;
				case RIGHT:
					unité.setPosition(new Coordonnées(unité.getPosition().getX()+1,unité.getPosition().getY()));
					unité.setDirection(Direction.EST);
					break;
				case ATTACK1:
					Troupes cible = chercheCible(troupe,troupe.getDirection(),false);
					cible.setPV(cible.getPV()-troupe.getDegats());
					if(cible.getPV()<=0) {
						if(cible.getType()!="Château")
							plateau.getUnité_bleue().remove(cible);
						else
							System.out.println("Château rouge détruit !");
					}
					break;
				case ATTACK2:
					Troupes cible2 = chercheCible(troupe,troupe.getDirection(),false);
					cible2.setPV(cible2.getPV()-troupe.getDegats());
					if(cible2.getPV()<=0) {
						if(cible2.getType()!="Château")
							plateau.getUnité_bleue().remove(cible2);
						else
							System.out.println("Château rouge détruit !");
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
					cible = plateau.getTroupeRouge(new Coordonnées(x, y-1));
					if(cible==null && (type=="Archer" || type=="Mage")) {
						cible = plateau.getTroupeRouge(new Coordonnées(x,y-2));
						if(cible==null)
							cible = plateau.getTroupeRouge(new Coordonnées(x,y-3));
					}
					break;
				case SUD:
					cible = plateau.getTroupeRouge(new Coordonnées(x, y+1));
					if(cible==null && (type=="Archer" || type=="Mage")) {
						cible = plateau.getTroupeRouge(new Coordonnées(x,y+2));
						if(cible==null)
							cible = plateau.getTroupeRouge(new Coordonnées(x,y+3));
					}
					break;
				case OUEST:
					cible = plateau.getTroupeRouge(new Coordonnées(x-1, y));
					if(cible==null && (type=="Archer" || type=="Mage")) {
						cible = plateau.getTroupeRouge(new Coordonnées(x-2,y));
						if(cible==null)
							cible = plateau.getTroupeRouge(new Coordonnées(x-3,y));
					}
					break;
				case EST:
					cible = plateau.getTroupeRouge(new Coordonnées(x+1, y));
					if(cible==null && (type=="Archer" || type=="Mage")) {
						cible = plateau.getTroupeRouge(new Coordonnées(x+2,y));
						if(cible==null)
							cible = plateau.getTroupeRouge(new Coordonnées(x+3,y));
					}
					break;
			}
		}
		else {
			switch (dir) {
			case NORD:
				cible = plateau.getTroupeBleu(new Coordonnées(x, y-1));
				if(cible==null && (type=="Archer" || type=="Mage")) {
					cible = plateau.getTroupeBleu(new Coordonnées(x,y-2));
					if(cible==null)
						cible = plateau.getTroupeBleu(new Coordonnées(x,y-3));
				}
				break;
			case SUD:
				cible = plateau.getTroupeBleu(new Coordonnées(x, y+1));
				if(cible==null && (type=="Archer" || type=="Mage")) {
					cible = plateau.getTroupeBleu(new Coordonnées(x,y+2));
					if(cible==null)
						cible = plateau.getTroupeBleu(new Coordonnées(x,y+3));
				}
				break;
			case OUEST:
				cible = plateau.getTroupeBleu(new Coordonnées(x-1, y));
				if(cible==null && (type=="Archer" || type=="Mage")) {
					cible = plateau.getTroupeBleu(new Coordonnées(x-2,y));
					if(cible==null)
						cible = plateau.getTroupeBleu(new Coordonnées(x-3,y));
				}
				break;
			case EST:
				cible = plateau.getTroupeBleu(new Coordonnées(x+1, y));
				if(cible==null && (type=="Archer" || type=="Mage")) {
					cible = plateau.getTroupeBleu(new Coordonnées(x+2,y));
					if(cible==null)
						cible = plateau.getTroupeBleu(new Coordonnées(x+3,y));
				}
				break;
			}
		}
		return cible;
	}
}
