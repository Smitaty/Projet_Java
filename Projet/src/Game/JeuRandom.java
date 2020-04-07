package Game;

import IG.Plateau;
import java.util.ArrayList;
import Troupes.Troupes;
import Stratégie.StratégieRandom;
import java.util.Random;
import java.lang.Thread;

public class JeuRandom extends Game implements Runnable{
	private Plateau plateau;
	private ArrayList<Troupes> TroupesBleues;
	private ArrayList<Troupes> TroupesRouges;
	private Troupes chateauBleu;
	private Troupes chateauRouge;
	private int bleu;
	
	public JeuRandom(Plateau plateau) {
		super();
		this.plateau = plateau;
		TroupesBleues = plateau.getUnité_bleue();
		TroupesRouges = plateau.getUnité_rouge();
		chateauBleu = plateau.getChateau(TroupesBleues);
		chateauRouge = plateau.getChateau(TroupesRouges);
		bleu = new Random().nextInt(2);
	}
	
	public void afficheCoordonnéesTroupes() {
		for(Troupes troupe : TroupesBleues) {
			System.out.println(troupe.getType()+" Bleu "+troupe.getDirection()+" "+troupe.getPosition().getX()+" "+troupe.getPosition().getY());
		}
		for(Troupes troupe : TroupesRouges) {
			System.out.println(troupe.getType()+" Rouge "+troupe.getDirection()+" "+troupe.getPosition().getX()+" "+troupe.getPosition().getY());
		}
	}
	
	public void run() {
		System.out.println("Début de partie");
		afficheCoordonnéesTroupes();
		while(chateauBleu.getPV()>0 || chateauRouge.getPV()>0) {
			if(bleu==0) {
				for(Troupes troupe : TroupesBleues) {
					if(troupe.getType()!="Château") {
						StratégieRandom strat = new StratégieRandom(troupe,plateau);
						TroupesAction action = strat.coupRandom();
						System.out.println(troupe.getType()+" bleu  "+action);
						strat.jouer(action, troupe,true);
					}
				}
				System.out.println("Fin tour bleu");
				plateau.repaint();
				try {
					Thread.sleep(18000);
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				bleu=1;
			}
			else {
				for(Troupes troupe : TroupesRouges) {
					if(troupe.getType()!="Château") {
						StratégieRandom strat = new StratégieRandom(troupe,plateau);
						TroupesAction action = strat.coupRandom();
						System.out.println(troupe.getType()+" rouge "+action);
						strat.jouer(action, troupe,false);
					}
				}
				System.out.println("Fin tour rouge");
				plateau.repaint();
				try {
					Thread.sleep(18000);
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
