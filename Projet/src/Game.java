package Game;

import IG.*;
import Troupes.*;
import java.util.*;
import java.lang.Thread.*;

public class Game {
	private String filename;
	private Plateau plateau;
	
	public Game(Plateau plateau) {
		this.plateau = plateau;
	}
	
	
	public void jeuAleatoire() {
		boolean tourRouge = true;
		
		int nbTours = 0;  // Pour debuguer et voir le déroulement

		while(plateau.getChateau(plateau.getUnitesBleues()).getPV() > 0 && plateau.getChateau(plateau.getUnitesRouges()).getPV() > 0) {
			System.out.println("Tour numero " + nbTours);

			if(tourRouge == true) {
				for(Troupes unite : plateau.getUnitesRouges()) {
					if(unite.getType() != "Chateau") {
						ArrayList<TroupesActions> Actions = unite.actionsPossibles("rouge", plateau);
						Random rand = new Random();
						int choixAleatoire = rand.nextInt(Actions.size());
						TroupesActions actionAleatoire = Actions.get(choixAleatoire);
						unite.executerAction(actionAleatoire, plateau, "rouge");
					}
				}
				System.out.println("Fin tour rouge.");
				try{
					Thread.sleep(4000);
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}
				tourRouge = false;
			}
			else {
				for(Troupes unite : plateau.getUnitesBleues()) {
					if(unite.getType() != "Chateau") {
						ArrayList<TroupesActions> Actions = unite.actionsPossibles("bleue", plateau);
						Random rand = new Random();
						int choixAleatoire = rand.nextInt(Actions.size());
						TroupesActions actionAleatoire = Actions.get(choixAleatoire);
						unite.executerAction(actionAleatoire, plateau, "bleue");
					}
				}
				System.out.println("Fin tour bleu.");
				try{
					Thread.sleep(4000);
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}				tourRouge = true;
			}
			++nbTours;
		}
	}
	
	
	
	
	
}