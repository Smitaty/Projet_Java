package Game;

import IG.Plateau;
import Strategie.StrategieRandom;

import java.util.ArrayList;
import Troupes.Troupes;

import java.util.Random;

public class JeuRandom extends Game{
	private Plateau plateau;
	private ArrayList<Troupes> TroupesBleues;
	private ArrayList<Troupes> TroupesRouges;
	private Troupes chateauBleu;
	private Troupes chateauRouge;
	private int bleu;
	
	public JeuRandom(Plateau plateau) {
		super();
		this.plateau = plateau;
		TroupesBleues = plateau.getUnite_bleue();
		TroupesRouges = plateau.getUnite_rouge();
		chateauBleu = plateau.getChateau(TroupesBleues);
		chateauRouge = plateau.getChateau(TroupesRouges);
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
		System.out.println(plateau.getLargeur()+" "+plateau.getHauteur());
		while(chateauBleu.getPV()>0 && chateauRouge.getPV()>0) {
			if(bleu==0) {
				for(Troupes troupe : TroupesBleues) {
					if(troupe.getType()!="Château") {
						System.out.println(troupe.getPosition().getX()+" "+troupe.getPosition().getY());
						StrategieRandom strat = new StrategieRandom(troupe,plateau);
						TroupesAction action = strat.coupRandom();
						System.out.println(troupe.getType()+" bleu  "+action);
						strat.jouer(action, troupe,true);
						
					}
				}
				System.out.println("Fin tour bleu");
				//notifyObserver();
				plateau.repaint();
				try {
					Thread.sleep(1000);
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				bleu=1;
			}
			else {
				for(Troupes troupe : TroupesRouges) {
					if(troupe.getType()!="Château") {
						StrategieRandom strat = new StrategieRandom(troupe,plateau);
						TroupesAction action = strat.coupRandom();
						System.out.println(troupe.getType()+" rouge "+action);
						strat.jouer(action, troupe,false);
					}
				}
				System.out.println("Fin tour rouge");
				//notifyObserver();
				plateau.repaint();
				try {
					Thread.sleep(1000);
				}catch(Exception e) {
					System.out.println(e.getMessage());
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
