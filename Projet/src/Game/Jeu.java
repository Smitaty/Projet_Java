package Game;

import IG.Plateau;
import Strategie.*;
import Troupes.*;
import Perceptron.*;
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
	private boolean batch;
	private int nbTours;
	private double reward;
	private ArrayList<Quadruplet> listQuad;
	
	public Jeu(Plateau plateau, Strategie stratBleu, Strategie stratRouge, int nbTours, boolean batch) {
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
		this.batch=batch;
		this.listQuad = new ArrayList<Quadruplet>();
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
		if(batch)
			partieBatch();
		else
			partieIG();
	}
	
	public void partieIG() {
		System.out.println("Debut de partie");
		afficheCoordonneesTroupes();
		int tour = 0;
		reward=0;
		while(chateauBleu.getPV()>0 && chateauRouge.getPV()>0 && tour < this.nbTours) {
			++tour;
			if(bleu==0) {
				if(TroupesBleues.size()>1) {
					for(Troupes troupe : TroupesBleues) {
						if(troupe.getType()!="Chateau" && chateauBleu.getPV()>0 && chateauRouge.getPV()>0) {
							TroupesAction action;
							if(strategieBleu.estPerceptron()) {
								SparseVector etatInit = strategieBleu.encodageEtat(plateau, troupe);
								action = strategieBleu.coup(troupe);
								strategieBleu.jouer(action, troupe);
								reward+=troupe.getReward();
								EnleveTroupeMorte(true);
							
								SparseVector etatAtteint = strategieBleu.encodageEtat(plateau, troupe);
								Quadruplet quad = new Quadruplet(etatInit,action,etatAtteint,troupe.getReward());
								listQuad.add(quad);
							}
							else {
								action = strategieBleu.coup(troupe);
								strategieBleu.jouer(action, troupe);
								reward+=troupe.getReward();
								EnleveTroupeMorte(true);
							}
						}
					}
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
							if(strategieRouge.estPerceptron()) {
								SparseVector etatInit = strategieRouge.encodageEtat(plateau, troupe);
								TroupesAction action = strategieRouge.coup(troupe);
								strategieRouge.jouer(action, troupe);
								SparseVector etatAtteint = strategieBleu.encodageEtat(plateau, troupe);
								Quadruplet quad = new Quadruplet(etatInit,action,etatAtteint,troupe.getReward());
								listQuad.add(quad);
							}
							else {
								TroupesAction action = strategieRouge.coup(troupe);
								strategieRouge.jouer(action, troupe);
							}
						}
					}
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
	
	public void partieBatch() {
		int tour = 0;
		while(chateauBleu.getPV()>0 && chateauRouge.getPV()>0 && tour < this.nbTours) {
			++tour;
			if(bleu==0) {
				if(TroupesBleues.size()>1) {
					for(Troupes troupe : TroupesBleues) {
						if(troupe.getType()!="Chateau" && chateauBleu.getPV()>0 && chateauRouge.getPV()>0) {
							TroupesAction action;
							if(strategieBleu.estPerceptron()) {
								SparseVector etatInit = strategieBleu.encodageEtat(plateau, troupe);
								action = strategieBleu.coup(troupe);
								strategieBleu.jouer(action, troupe);
								reward+=troupe.getReward();
								EnleveTroupeMorte(true);
							
								SparseVector etatAtteint = strategieBleu.encodageEtat(plateau, troupe);
								Quadruplet quad = new Quadruplet(etatInit,action,etatAtteint,troupe.getReward());
								listQuad.add(quad);
							}
							else {
								action = strategieBleu.coup(troupe);
								strategieBleu.jouer(action, troupe);
								reward+=troupe.getReward();
								EnleveTroupeMorte(true);
							}
						}
					}
				}
				bleu=1;
			}
			else {
				if(TroupesRouges.size()>1) {
					for(Troupes troupe : TroupesRouges) {
						if(troupe.getType()!="Chateau" && chateauBleu.getPV()>0 && chateauRouge.getPV()>0) {
							if(strategieRouge.estPerceptron()) {
								SparseVector etatInit = strategieRouge.encodageEtat(plateau, troupe);
								TroupesAction action = strategieRouge.coup(troupe);
								strategieRouge.jouer(action, troupe);
								SparseVector etatAtteint = strategieBleu.encodageEtat(plateau, troupe);
								Quadruplet quad = new Quadruplet(etatInit,action,etatAtteint,troupe.getReward());
								listQuad.add(quad);
							}
							else {
								TroupesAction action = strategieRouge.coup(troupe);
								strategieRouge.jouer(action, troupe);
								EnleveTroupeMorte(false);
							}
						}
					}
				}
				bleu=0;
			}
		}
		if(tour >= this.nbTours) {
			partieFini=true;
			gagneBleu=false;
			gagneRouge=false;
		}
		else if(chateauBleu.getPV()<=0) {
			partieFini=true;
			gagneBleu=false;
			gagneRouge=true;
		}
		else {
			partieFini=true;
			gagneBleu=true;
			gagneRouge=false;
		}
	}
	
	public void EnleveTroupeMorte(boolean estBleu) {
		if(!estBleu) {
			for(int i=0; i<TroupesBleues.size(); ++i) {
				if(TroupesBleues.get(i).getPV()<=0 && TroupesBleues.get(i).getType()!="Chateau")
					this.TroupesBleues.remove(i);
			}
		}
		else {
			for(int i=0; i<TroupesRouges.size(); ++i) {
				if(TroupesRouges.get(i).getPV()<=0 && TroupesRouges.get(i).getType()!="Chateau")
					this.TroupesRouges.remove(i);
			}
		}
	}
	
	public double getReward() {
		return this.reward;
	}

	public Plateau getPlateau() {
		return this.plateau;
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
	
	public ArrayList<Quadruplet> getListQuad(){
		return listQuad;
	}
}