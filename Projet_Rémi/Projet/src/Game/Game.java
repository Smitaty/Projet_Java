package Game;

import IG.*;
import Troupes.*;
import java.util.*;
import java.lang.Thread.*;


import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import javax.swing.JFrame;


public class Game {
	private String filename;
	private Plateau plateau;
	
	public Game(Plateau plateau) {
		this.plateau = plateau;
	}
	
	
	public void jeuAleatoire(JFrame frame) {
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
						
						
						
						
						System.out.println("actions possibles pour " + unite.getType() + " rouge ");
						for(TroupesActions act : Actions) {
							if(act == TroupesActions.ATTACK_BOTTOM) System.out.println("atq_bottom");
							if(act == TroupesActions.ATTACK_TOP) System.out.println("atq_haut");
							if(act == TroupesActions.ATTACK_LEFT) System.out.println("atq_gauche");
							if(act == TroupesActions.ATTACK_RIGHT) System.out.println("atq_droite");
							if(act == TroupesActions.TOP) System.out.println("haut");
							if(act == TroupesActions.BOTTOM) System.out.println("bas");
							if(act == TroupesActions.LEFT) System.out.println("gauche");
							if(act == TroupesActions.RIGHT) System.out.println("droite");

						
						}

						
						
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
						
						
						System.out.println("actions possibles pour " + unite.getType() + " bleu");
						for(TroupesActions act : Actions) {
							if(act == TroupesActions.ATTACK_BOTTOM) System.out.println("atq_bottom");
							if(act == TroupesActions.ATTACK_TOP) System.out.println("atq_haut");
							if(act == TroupesActions.ATTACK_LEFT) System.out.println("atq_gauche");
							if(act == TroupesActions.ATTACK_RIGHT) System.out.println("atq_droite");
							if(act == TroupesActions.TOP) System.out.println("haut");
							if(act == TroupesActions.BOTTOM) System.out.println("bas");
							if(act == TroupesActions.LEFT) System.out.println("gauche");
							if(act == TroupesActions.RIGHT) System.out.println("droite");

						
						}
						
						
					}
				}
				System.out.println("Fin tour bleu.");
				try{
					Thread.sleep(10000);
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}	
				tourRouge = true;
			}
			++nbTours;
			frame.repaint();
		}
	}
	
	
	
	
	
}