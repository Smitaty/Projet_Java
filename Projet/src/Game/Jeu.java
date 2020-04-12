package Game;

import IG.Plateau;
import Strategie.*;

import java.util.ArrayList;
import Troupes.Troupes;

import java.util.Random;

public class Jeu extends Game{
	private Plateau plateau;
	private ArrayList<Troupes> TroupesBleues;
	private ArrayList<Troupes> TroupesRouges;
	private Troupes chateauBleu;
	private Troupes chateauRouge;
	private Strategie strategieBleu;
	private Strategie strategieRouge;
	private int bleu;
	
	public Jeu(Plateau plateau, Strategie stratBleu, Strategie stratRouge) {
		super();
		this.plateau = plateau;
		TroupesBleues = plateau.getUnite_bleue();
		TroupesRouges = plateau.getUnite_rouge();
		chateauBleu = plateau.getChateau(TroupesBleues);
		chateauRouge = plateau.getChateau(TroupesRouges);
		strategieBleu=stratBleu;
		strategieRouge=stratRouge;
		bleu = new Random().nextInt(2);
	}
	
	public void afficheCoordonneesTroupes() {
		for(Troupes troupe : TroupesBleues) {
			System.out.println(troupe.getType()+" Bleu "+troupe.getDirection()+" "+troupe.getPosition().getX()+" "+troupe.getPosition().getY());
		}
		for(Troupes troupe : TroupesRouges) {
			System.out.println(troupe.getType()+" Rouge "+troupe.getDirection()+" "+troupe.getPosition().getX()+" "+troupe.getPosition().getY());
		}
	}
	
	public void notifyObserver() {
		for (Observer ob : super.getObs()) {
            ob.update(plateau);
        }
	}
	
	public void partie() {
		System.out.println("Debut de partie");
		afficheCoordonneesTroupes();
		//notifyObserver();
		while(chateauBleu.getPV()>0 && chateauRouge.getPV()>0) {
			if(bleu==0) {
				if(TroupesBleues.size()>1) {
					for(Troupes troupe : TroupesBleues) {
						if(troupe.getType()!="Chateau" && chateauBleu.getPV()>0 && chateauRouge.getPV()>0) {
							TroupesAction action = strategieBleu.coup(troupe);
							System.out.println(troupe.toString()+", action="+action);
							strategieBleu.jouer(action, troupe,true);
							
						}
					}
					System.out.println("Fin tour bleu");
					//notifyObserver();
					plateau.repaint();
					try {
						Thread.sleep(3000);
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}
				bleu=1;
			}
			else {
				if(TroupesRouges.size()>1) {
					for(Troupes troupe : TroupesRouges) {
						if(troupe.getType()!="Chateau" && chateauBleu.getPV()>0 && chateauRouge.getPV()>0) {
							TroupesAction action = strategieRouge.coup(troupe);
							System.out.println(troupe.toString()+", action="+action);
							strategieRouge.jouer(action, troupe,false);
						}
					}
					System.out.println("Fin tour rouge");
					//notifyObserver();
					plateau.repaint();
					try {
						Thread.sleep(3000);
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}
				bleu=0;
			}
		}
		if(chateauBleu.getPV()<=0)
			System.out.println("Equipe Rouge gagne !!");
		else
			System.out.println("Equipe Bleue gagne !!");
	}
}
