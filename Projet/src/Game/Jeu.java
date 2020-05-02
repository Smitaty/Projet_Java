package Game;

import IG.Plateau;
import Strategie.*;
import Troupes.*;
import java.util.ArrayList;
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
	private boolean partieFini = false;
	private boolean gagneRouge;
	private boolean gagneBleu;
	private int nbTours;
	
	public Jeu(Plateau plateau, Strategie stratBleu, Strategie stratRouge, int nbTours) {
		super();
		this.plateau = plateau;
		TroupesBleues = plateau.getUnite_bleue();
		TroupesRouges = plateau.getUnite_rouge();
		chateauBleu = plateau.getChateau(TroupesBleues);
		chateauRouge = plateau.getChateau(TroupesRouges);
		strategieBleu=stratBleu;
		strategieRouge=stratRouge;
		bleu = new Random().nextInt(2);
		this.nbTours = nbTours;
	}
	
	public void afficheCoordonneesTroupes() {
		for(Troupes troupe : TroupesBleues) {
			System.out.println(troupe.getType()+" Bleu "+troupe.getDirection()+" "+troupe.getPosition().getX()+" "+troupe.getPosition().getY());
		}
		for(Troupes troupe : TroupesRouges) {
			System.out.println(troupe.getType()+" Rouge "+troupe.getDirection()+" "+troupe.getPosition().getX()+" "+troupe.getPosition().getY());
		}
	}
	
	public void partie() {
		System.out.println("Debut de partie");
		afficheCoordonneesTroupes();
		int tour = 0;
		while(chateauBleu.getPV()>0 && chateauRouge.getPV()>0 && tour < this.nbTours) {
			++tour;
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
		if(tour >= this.nbTours) {
			System.out.println("Match nul, nombre de tours max atteint.");
			partieFini=true;
			gagneBleu=false;
			gagneRouge=false;
		}
		else if(chateauBleu.getPV()<=0) {
			System.out.println("Equipe Rouge gagne !!");
			partieFini=true;
			gagneBleu=false;
			gagneRouge=true;
		}
		else {
			System.out.println("Equipe Bleue gagne !!");
			partieFini=true;
			gagneBleu=true;
			gagneRouge=false;
		}
	}

	public boolean isPartieFini() {
		return partieFini;
	}

	public void setPartieFini(boolean partieFini) {
		this.partieFini = partieFini;
	}

	public boolean isGagneRouge() {
		return gagneRouge;
	}

	public void setGagneRouge(boolean gagneRouge) {
		this.gagneRouge = gagneRouge;
	}

	public boolean isGagneBleu() {
		return gagneBleu;
	}

	public void setGagneBleu(boolean gagneBleu) {
		this.gagneBleu = gagneBleu;
	}
	
}