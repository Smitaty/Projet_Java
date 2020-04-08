package Troupes;

import java.util.ArrayList;
import IG.*;


public abstract class Troupes {
	
	private int PV;
	private int Degats;
	private Coordonnees Coord;
	private String Type;
	
	public Troupes(int pv, int degats, Coordonnees coor, String type) {
		PV = pv;
		Degats = degats;
		Coord = coor;
		Type = type;
	}
	
	
	
	// Getters
	public int getPV() {return PV;}
	public int getDegats() {return Degats;}
	public int getX() {return Coord.getX();}
	public int getY() {return Coord.getY();}
	public Coordonnees getPosition() {return Coord;}
	public String getType() { return Type; }
	
	
	// Setters
	public void setPV(int pv) {PV=pv;}
	public void setDegats(int degats) {Degats=degats;}
	public void setX(int x) {Coord.setX(x);}
	public void setY(int y) {Coord.setY(y);}
	public void setPosition(Coordonnees new_coord) {Coord = new_coord ;}
	
	public ArrayList<TroupesActions> actionsPossibles(String Couleur, Plateau plateau){
		ArrayList<TroupesActions> Actions = new ArrayList<TroupesActions>();
		Coordonnees testTop = new Coordonnees(this.getX(), this.getY()+1);
		Coordonnees testBottom = new Coordonnees(this.getX(), this.getY()-1);
		Coordonnees testLeft = new Coordonnees(this.getX()-1, this.getY());
		Coordonnees testRight = new Coordonnees(this.getX()+1, this.getY());
		boolean topValide, bottomValide, leftValide, rightValide;
		
		if(testTop.getY() <= plateau.getHauteur()) topValide = true; else topValide = false;
		if(testBottom.getY() > 0) bottomValide = true; else bottomValide = false;
		if(testLeft.getX() > 0) leftValide = true; else leftValide = false;
		if(testRight.getX() <= plateau.getHauteur()) rightValide = true; else rightValide = false;

		Actions.add(TroupesActions.STOP);

// ----------- DEPLACEMENTS --------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------
		// Tests de la validité de la case au dessus de l'unité
		if(topValide == true) {
			// Tests si la case au dessus de l'unité est occupée ou non
			boolean caseOccupee = false;

			for(Troupes unite : plateau.getUnitesBleues()) {
				if(unite.getPosition().getX() == testTop.getX() && unite.getPosition().getY() == testTop.getY()) 
					caseOccupee = true;					
			}
				
			if(caseOccupee == false) {
				for(Troupes unite : plateau.getUnitesRouges()) {
					if(unite.getPosition().getX() == testTop.getX() && unite.getPosition().getY() == testTop.getY()) 
						caseOccupee = true;
				}
			}
					
			if(caseOccupee == false) {
				for(Coordonnees rocher : plateau.getRochers()) {
					if(rocher.getX() == testTop.getX() && rocher.getY() == testTop.getY()) 
						caseOccupee = true;
				}
			}
					
			if(caseOccupee == false) {
				for(Coordonnees arbre : plateau.getArbres()) {
					if(arbre.getX() == testTop.getX() && arbre.getY() == testTop.getY()) 
						caseOccupee = true;
				}
			}
				
			if(caseOccupee == false) Actions.add(TroupesActions.TOP);
		}
	
// ---------------------------------------------------------------------------------------------
		// Tests de la validité de la case en dessous de l'unité
		if(bottomValide == true) {
			// Tests si la case en dessous de l'unité est occupée ou non
			boolean caseOccupee = false;

			for(Troupes unite : plateau.getUnitesBleues()) {
				if(unite.getPosition().getX() == testBottom.getX() && unite.getPosition().getY() == testBottom.getY()) 
					caseOccupee = true;					
			}
						
			if(caseOccupee == false) {
				for(Troupes unite : plateau.getUnitesRouges()) {
					if(unite.getPosition().getX() == testBottom.getX() && unite.getPosition().getY() == testBottom.getY()) 
						caseOccupee = true;
				}
			}
							
			if(caseOccupee == false) {
				for(Coordonnees rocher : plateau.getRochers()) {
					if(rocher.getX() == testBottom.getX() && rocher.getY() == testBottom.getY()) 
						caseOccupee = true;
				}
			}
					
			if(caseOccupee == false) {
				for(Coordonnees arbre : plateau.getArbres()) {
					if(arbre == testBottom)
						caseOccupee = true;
				}
			}
					
			if(caseOccupee == false) Actions.add(TroupesActions.BOTTOM);
		}	
		
// ---------------------------------------------------------------------------------------------
		// Tests de la validité de la case à gauche de l'unité
		if(leftValide == true) {
			// Tests si la case à gauche de l'unité est occupée ou non
			boolean caseOccupee = false;

			for(Troupes unite : plateau.getUnitesBleues()) {
				if(unite.getPosition().getX() == testLeft.getX() && unite.getPosition().getY() == testLeft.getY()) 
					caseOccupee = true;					
			}
								
			if(caseOccupee == false) {
				for(Troupes unite : plateau.getUnitesRouges()) {
					if(unite.getPosition().getX() == testLeft.getX() && unite.getPosition().getY() == testLeft.getY()) 
						caseOccupee = true;
				}
			}
									
			if(caseOccupee == false) {
				for(Coordonnees rocher : plateau.getRochers()) {
					if(rocher.getX() == testLeft.getX() && rocher.getY() == testLeft.getY()) 
						caseOccupee = true;
				}
			}
					
			if(caseOccupee == false) {
				for(Coordonnees arbre : plateau.getArbres()) {
					if(arbre.getX() == testLeft.getX() && arbre.getY() == testLeft.getY()) 
						caseOccupee = true;
				}
			}
							
			if(caseOccupee == false) Actions.add(TroupesActions.LEFT);
		}	
			
// ---------------------------------------------------------------------------------------------
		// Tests de la validité de la case à droite de l'unité
		if(rightValide == true) {
			// Tests si la case à droite de l'unité est occupée ou non
			boolean caseOccupee = false;

			for(Troupes unite : plateau.getUnitesBleues()) {
				if(unite.getPosition().getX() == testRight.getX() && unite.getPosition().getY() == testRight.getY()) 
					caseOccupee = true;					
			}
										
			if(caseOccupee == false) {
				for(Troupes unite : plateau.getUnitesRouges()) {
					if(unite.getPosition().getX() == testRight.getX() && unite.getPosition().getY() == testRight.getY()) 
						caseOccupee = true;
				}
			}
											
			if(caseOccupee == false) {
				for(Coordonnees rocher : plateau.getRochers()) {
					if(rocher.getX() == testLeft.getX() && rocher.getY() == testLeft.getY()) 
						caseOccupee = true;
				}
			}
					
			if(caseOccupee == false) {
				for(Coordonnees arbre : plateau.getArbres()) {
					if(arbre.getX() == testLeft.getX() && arbre.getY() == testLeft.getY()) 
						caseOccupee = true;
				}
			}
									
			if(caseOccupee == false) Actions.add(TroupesActions.RIGHT);
		}	

		
// ----------- ATTAQUES ------------------------------------------------------------------------
// ------------ ROUGES -------------------------------------------------------------------------
		if(Couleur == "rouge") {
			// ----------- CHEVALIER -----------------------------------------------------------
			if(this.getType() == "Chevalier") {
				// Si la case au dessus est valide et que le perso ne peut pas se déplacer sur cette case, alors on test si la case est occupée par une unité ennemie ou non
				// Si oui, alors on ajoute l'action ATTACK_TOP, sinon on ne fait rien
				if(topValide == true && Actions.contains(TroupesActions.TOP) == false) {
					for(Troupes unite : plateau.getUnitesBleues()) {
						if(unite.getPosition().getX() == testTop.getX() && unite.getPosition().getY() == testTop.getY())
							Actions.add(TroupesActions.ATTACK_TOP); 
					}
				}
				if(bottomValide == true && Actions.contains(TroupesActions.BOTTOM) == false) {
					for(Troupes unite : plateau.getUnitesBleues()) {
						if(unite.getPosition().getX() == testBottom.getX() && unite.getPosition().getY() == testBottom.getY())
							Actions.add(TroupesActions.ATTACK_BOTTOM); 
					}
				}
				if(leftValide == true && Actions.contains(TroupesActions.LEFT) == false) {
					for(Troupes unite : plateau.getUnitesBleues()) {
						if(unite.getPosition().getX() == testLeft.getX() && unite.getPosition().getY() == testLeft.getY())
							Actions.add(TroupesActions.ATTACK_LEFT); 
					}
				}
				if(rightValide == true && Actions.contains(TroupesActions.RIGHT) == false) {
					for(Troupes unite : plateau.getUnitesBleues()) {
						if(unite.getPosition().getX() == testRight.getX() && unite.getPosition().getY() == testRight.getY())
							Actions.add(TroupesActions.ATTACK_RIGHT); 
					}
				}
			}
			// ----------- MAGE -----------------------------------------------------------
			else if(this.getType() == "Mage") {
				// On regarde si une unité ennemie se situe au dessus du personnage
				ArrayList<Coordonnees> testDessus = new ArrayList<Coordonnees>();
				testDessus.add(new Coordonnees(this.getX(),this.getY()+1));
				testDessus.add(new Coordonnees(this.getX(),this.getY()+2));
				testDessus.add(new Coordonnees(this.getX(),this.getY()+3));
				boolean obstacleArbre = false;
				boolean attaqueTrouvee = false;
				for(Coordonnees posTest : testDessus) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesBleues()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}	
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_TOP);
				
				
				// On regarde si une unité ennemie se situe en dessous du personnage
				ArrayList<Coordonnees> testDessous = new ArrayList<Coordonnees>();
				testDessous.add(new Coordonnees(this.getX(),this.getY()-1));
				testDessous.add(new Coordonnees(this.getX(),this.getY()-2));
				testDessous.add(new Coordonnees(this.getX(),this.getY()-3));
				obstacleArbre = false;
				attaqueTrouvee = false;
				
				for(Coordonnees posTest : testDessous) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesBleues()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}	
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_BOTTOM);

				
				// On regarde si une unité ennemie se situe à gauche du personnage
				ArrayList<Coordonnees> testGauche = new ArrayList<Coordonnees>();
				testGauche.add(new Coordonnees(this.getX()-1,this.getY()));
				testGauche.add(new Coordonnees(this.getX()-2,this.getY()));
				testGauche.add(new Coordonnees(this.getX()-3,this.getY()));
				obstacleArbre = false;
				attaqueTrouvee = false;
				
				for(Coordonnees posTest : testGauche) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesBleues()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}	
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_LEFT);				
				
				
				// On regarde si une unité ennemie se situe à droite du personnage
				ArrayList<Coordonnees> testDroite = new ArrayList<Coordonnees>();
				testDroite.add(new Coordonnees(this.getX()+1,this.getY()));
				testDroite.add(new Coordonnees(this.getX()+2,this.getY()));
				testDroite.add(new Coordonnees(this.getX()+3,this.getY()));
				obstacleArbre = false;
				attaqueTrouvee = false;
				
				for(Coordonnees posTest : testDroite) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesBleues()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}	
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_RIGHT);	
			}
			// ----------- ARCHER -----------------------------------------------------------
			else if(this.getType() == "Archer") {
				// On regarde si une unité ennemie se situe au dessus du personnage
				ArrayList<Coordonnees> testDessus = new ArrayList<Coordonnees>();
				testDessus.add(new Coordonnees(this.getX(),this.getY()+2));
				testDessus.add(new Coordonnees(this.getX(),this.getY()+3));
				
				boolean obstacleArbre = false;
				boolean attaqueTrouvee = false;
				
				for(Coordonnees posTest : testDessus) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesBleues()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_TOP);	

				
				// On regarde si une unité ennemie se situe en dessous du personnage
				ArrayList<Coordonnees> testDessous = new ArrayList<Coordonnees>();
				testDessous.add(new Coordonnees(this.getX(),this.getY()-2));
				testDessous.add(new Coordonnees(this.getX(),this.getY()-3));
				
				obstacleArbre = false;
				attaqueTrouvee = false;
				
				for(Coordonnees posTest : testDessous) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesBleues()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_BOTTOM);
				
				
				// On regarde si une unité ennemie se situe à gauche du personnage
				ArrayList<Coordonnees> testGauche = new ArrayList<Coordonnees>();
				testGauche.add(new Coordonnees(this.getX()-2,this.getY()));
				testGauche.add(new Coordonnees(this.getX()-3,this.getY()));
				
				obstacleArbre = false;
				attaqueTrouvee = false;
				
				for(Coordonnees posTest : testGauche) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesBleues()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_LEFT);
				
				
				// On regarde si une unité ennemie se situe à droite du personnage
				ArrayList<Coordonnees> testDroite = new ArrayList<Coordonnees>();
				testDroite.add(new Coordonnees(this.getX()+2,this.getY()));
				testDroite.add(new Coordonnees(this.getX()+3,this.getY()));
				
				obstacleArbre = false;
				attaqueTrouvee = false;
				
				for(Coordonnees posTest : testDroite) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesBleues()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_RIGHT);
			}
		}
		
// ------------ BLEUS -------------------------------------------------------------------------
		else if(Couleur == "bleue") {
			// ----------- CHEVALIER -----------------------------------------------------------
			if(this.getType() == "Chevalier") {
				// Si la case au dessus est valide et que le perso ne peut pas se déplacer sur cette case, alors on test si la case est occupée par une unité ennemie ou non
				// Si oui, alors on ajoute l'action ATTACK_TOP, sinon on ne fait rien
				if(topValide == true && Actions.contains(TroupesActions.TOP) == false) {
					for(Troupes unite : plateau.getUnitesRouges()) {
						if(unite.getPosition().getX() == testTop.getX() && unite.getPosition().getY() == testTop.getY())
							Actions.add(TroupesActions.ATTACK_TOP); 
					}
				}
				if(bottomValide == true && Actions.contains(TroupesActions.BOTTOM) == false) {
					for(Troupes unite : plateau.getUnitesRouges()) {
						if(unite.getPosition().getX() == testBottom.getX() && unite.getPosition().getY() == testBottom.getY())
							Actions.add(TroupesActions.ATTACK_BOTTOM); 
					}
				}
				if(leftValide == true && Actions.contains(TroupesActions.LEFT) == false) {
					for(Troupes unite : plateau.getUnitesRouges()) {
						if(unite.getPosition().getX() == testLeft.getX() && unite.getPosition().getY() == testLeft.getY())
							Actions.add(TroupesActions.ATTACK_LEFT); 
					}
				}
				if(rightValide == true && Actions.contains(TroupesActions.RIGHT) == false) {
					for(Troupes unite : plateau.getUnitesRouges()) {
						if(unite.getPosition().getX() == testRight.getX() && unite.getPosition().getY() == testRight.getY())
							Actions.add(TroupesActions.ATTACK_RIGHT); 
					}
				}
			}
			// ----------- MAGE -----------------------------------------------------------
			else if(this.getType() == "Mage") {
				// On regarde si une unité ennemie se situe au dessus du personnage
				ArrayList<Coordonnees> testDessus = new ArrayList<Coordonnees>();
				testDessus.add(new Coordonnees(this.getX(),this.getY()+1));
				testDessus.add(new Coordonnees(this.getX(),this.getY()+2));
				testDessus.add(new Coordonnees(this.getX(),this.getY()+3));
				boolean obstacleArbre = false;
				boolean attaqueTrouvee = false;
				for(Coordonnees posTest : testDessus) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesRouges()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}	
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_TOP);
				
				
				// On regarde si une unité ennemie se situe en dessous du personnage
				ArrayList<Coordonnees> testDessous = new ArrayList<Coordonnees>();
				testDessous.add(new Coordonnees(this.getX(),this.getY()-1));
				testDessous.add(new Coordonnees(this.getX(),this.getY()-2));
				testDessous.add(new Coordonnees(this.getX(),this.getY()-3));
				obstacleArbre = false;
				attaqueTrouvee = false;
				
				for(Coordonnees posTest : testDessous) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesRouges()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}	
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_BOTTOM);

				
				// On regarde si une unité ennemie se situe à gauche du personnage
				ArrayList<Coordonnees> testGauche = new ArrayList<Coordonnees>();
				testGauche.add(new Coordonnees(this.getX()-1,this.getY()));
				testGauche.add(new Coordonnees(this.getX()-2,this.getY()));
				testGauche.add(new Coordonnees(this.getX()-3,this.getY()));
				obstacleArbre = false;
				attaqueTrouvee = false;
				
				for(Coordonnees posTest : testGauche) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesRouges()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}	
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_LEFT);				
				
				
				// On regarde si une unité ennemie se situe à droite du personnage
				ArrayList<Coordonnees> testDroite = new ArrayList<Coordonnees>();
				testDroite.add(new Coordonnees(this.getX()+1,this.getY()));
				testDroite.add(new Coordonnees(this.getX()+2,this.getY()));
				testDroite.add(new Coordonnees(this.getX()+3,this.getY()));
				obstacleArbre = false;
				attaqueTrouvee = false;
				
				for(Coordonnees posTest : testDroite) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesRouges()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}	
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_RIGHT);	
			}
			// ----------- ARCHER -----------------------------------------------------------
			else if(this.getType() == "Archer") {
				// On regarde si une unité ennemie se situe au dessus du personnage
				ArrayList<Coordonnees> testDessus = new ArrayList<Coordonnees>();
				testDessus.add(new Coordonnees(this.getX(),this.getY()+2));
				testDessus.add(new Coordonnees(this.getX(),this.getY()+3));
				
				boolean obstacleArbre = false;
				boolean attaqueTrouvee = false;
				
				for(Coordonnees posTest : testDessus) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesRouges()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_TOP);	

				
				// On regarde si une unité ennemie se situe en dessous du personnage
				ArrayList<Coordonnees> testDessous = new ArrayList<Coordonnees>();
				testDessous.add(new Coordonnees(this.getX(),this.getY()-2));
				testDessous.add(new Coordonnees(this.getX(),this.getY()-3));
				
				obstacleArbre = false;
				attaqueTrouvee = false;
				
				for(Coordonnees posTest : testDessous) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesRouges()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_BOTTOM);
				
				
				// On regarde si une unité ennemie se situe à gauche du personnage
				ArrayList<Coordonnees> testGauche = new ArrayList<Coordonnees>();
				testGauche.add(new Coordonnees(this.getX()-2,this.getY()));
				testGauche.add(new Coordonnees(this.getX()-3,this.getY()));
				
				obstacleArbre = false;
				attaqueTrouvee = false;
				
				for(Coordonnees posTest : testGauche) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesRouges()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_LEFT);
				
				
				// On regarde si une unité ennemie se situe à droite du personnage
				ArrayList<Coordonnees> testDroite = new ArrayList<Coordonnees>();
				testDroite.add(new Coordonnees(this.getX()+2,this.getY()));
				testDroite.add(new Coordonnees(this.getX()+3,this.getY()));
				
				obstacleArbre = false;
				attaqueTrouvee = false;
				
				for(Coordonnees posTest : testDroite) {
					for(Coordonnees arbre : plateau.getArbres()) {
						if(arbre.getX() == posTest.getX() && arbre.getY() == posTest.getY())
							obstacleArbre = true;
					}
					
					if(obstacleArbre == false && attaqueTrouvee == false) {
						for(Troupes unite : plateau.getUnitesRouges()) {
							if(unite.getPosition().getX() == posTest.getX() && unite.getPosition().getY() == posTest.getY())
								attaqueTrouvee = true;						
						}
					}
				}
				if(attaqueTrouvee == true) Actions.add(TroupesActions.ATTACK_RIGHT);
			}
		}
		return Actions;
	}
	
	
	public void executerAction(TroupesActions action, Plateau plateau, String Couleur) {
		switch (action) {
			case STOP:
				System.out.println("Action effectuee : STOP");
				break;
				
			case TOP:
				this.setY(this.getY()+1);
				System.out.println("Action effectuee : TOP");
				break;
				
			case BOTTOM:
				this.setY(this.getY()-1);
				System.out.println("Action effectuee : BOTTOM");
				break;
				
			case LEFT:
				this.setX(this.getX()-1);
				System.out.println("Action effectuee : LEFT");
				break;
				
			case RIGHT:
				this.setX(this.getX()+1);
				System.out.println("Action effectuee : RIGHT");
				break;
				
			case ATTACK_TOP:
				// Si l'unité est rouge
				if(Couleur == "rouge") {
					if(this.getType() == "Chevalier") {
						Coordonnees posAttaque = new Coordonnees(this.getX(),this.getY()+1);
						for(Troupes victime : plateau.getUnitesBleues()) {
							if(victime.getPosition().getX() == posAttaque.getX() && victime.getPosition().getY() == posAttaque.getY()) {
									victime.setPV(victime.getPV() - this.getDegats());
									if(victime.getPV() <= 0) plateau.getUnitesBleues().remove(victime);
							}
						}
					}
					else if (this.getType() == "Mage") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX(),this.getY()+1);
						Coordonnees posAttaque2 = new Coordonnees(this.getX(),this.getY()+2);
						Coordonnees posAttaque3 = new Coordonnees(this.getX(),this.getY()+3);
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);
						atqPossibles.add(posAttaque3);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesBleues()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesBleues().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
					}
					else if(this.getType() == "Archer") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX(),this.getY()+2);
						Coordonnees posAttaque2 = new Coordonnees(this.getX(),this.getY()+3);
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesBleues()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesBleues().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
						
					}
				}
				// Si l'unité est bleue
				else {
					if(this.getType() == "Chevalier") {
						Coordonnees posAttaque = new Coordonnees(this.getX(),this.getY()+1);
						for(Troupes victime : plateau.getUnitesRouges()) {
							if(victime.getPosition().getX() == posAttaque.getX() && victime.getPosition().getY() == posAttaque.getY()) {
									victime.setPV(victime.getPV() - this.getDegats());
									if(victime.getPV() <= 0) plateau.getUnitesRouges().remove(victime);
							}
						}
					}
					else if (this.getType() == "Mage") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX(),this.getY()+1);
						Coordonnees posAttaque2 = new Coordonnees(this.getX(),this.getY()+2);
						Coordonnees posAttaque3 = new Coordonnees(this.getX(),this.getY()+3);
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);
						atqPossibles.add(posAttaque3);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesRouges()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesRouges().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
					}
					else if(this.getType() == "Archer") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX(),this.getY()+2);
						Coordonnees posAttaque2 = new Coordonnees(this.getX(),this.getY()+3);
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesRouges()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesRouges().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
						
					}
				}			
				System.out.println("Action effectuee : ATTACK_TOP");
				break;
				
			case ATTACK_BOTTOM:
				// Si l'unité est rouge
				if(Couleur == "rouge") {
					if(this.getType() == "Chevalier") {
						Coordonnees posAttaque = new Coordonnees(this.getX(),this.getY()-1);
						for(Troupes victime : plateau.getUnitesBleues()) {
							if(victime.getPosition().getX() == posAttaque.getX() && victime.getPosition().getY() == posAttaque.getY()) {
									victime.setPV(victime.getPV() - this.getDegats());
									if(victime.getPV() <= 0) plateau.getUnitesBleues().remove(victime);
							}
						}
					}
					else if (this.getType() == "Mage") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX(),this.getY()-1);
						Coordonnees posAttaque2 = new Coordonnees(this.getX(),this.getY()-2);
						Coordonnees posAttaque3 = new Coordonnees(this.getX(),this.getY()-3);
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);
						atqPossibles.add(posAttaque3);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesBleues()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesBleues().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
					}
					else if(this.getType() == "Archer") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX(),this.getY()-2);
						Coordonnees posAttaque2 = new Coordonnees(this.getX(),this.getY()-3);
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesBleues()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesBleues().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
						
					}
				}
				// Si l'unité est bleue
				else {
					if(this.getType() == "Chevalier") {
						Coordonnees posAttaque = new Coordonnees(this.getX(),this.getY()-1);
						for(Troupes victime : plateau.getUnitesRouges()) {
							if(victime.getPosition().getX() == posAttaque.getX() && victime.getPosition().getY() == posAttaque.getY()) {
									victime.setPV(victime.getPV() - this.getDegats());
									if(victime.getPV() <= 0) plateau.getUnitesRouges().remove(victime);
							}
						}
					}
					else if (this.getType() == "Mage") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX(),this.getY()-1);
						Coordonnees posAttaque2 = new Coordonnees(this.getX(),this.getY()-2);
						Coordonnees posAttaque3 = new Coordonnees(this.getX(),this.getY()-3);
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);
						atqPossibles.add(posAttaque3);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesRouges()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesRouges().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
					}
					else if(this.getType() == "Archer") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX(),this.getY()-2);
						Coordonnees posAttaque2 = new Coordonnees(this.getX(),this.getY()-3);
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesRouges()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesRouges().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
						
					}
				}	
				System.out.println("Action effectuee : ATTACK_BOTTOM");
				break;
				
			case ATTACK_LEFT:
				// Si l'unité est rouge
				if(Couleur == "rouge") {
					if(this.getType() == "Chevalier") {
						Coordonnees posAttaque = new Coordonnees(this.getX()+1,this.getY());
						for(Troupes victime : plateau.getUnitesBleues()) {
							if(victime.getPosition().getX() == posAttaque.getX() && victime.getPosition().getY() == posAttaque.getY()) {
									victime.setPV(victime.getPV() - this.getDegats());
									if(victime.getPV() <= 0) plateau.getUnitesBleues().remove(victime);
							}
						}
					}
					else if (this.getType() == "Mage") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX()+1,this.getY());
						Coordonnees posAttaque2 = new Coordonnees(this.getX()+2,this.getY());
						Coordonnees posAttaque3 = new Coordonnees(this.getX()+3,this.getY());
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);
						atqPossibles.add(posAttaque3);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesBleues()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesBleues().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
					}
					else if(this.getType() == "Archer") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX()+2,this.getY());
						Coordonnees posAttaque2 = new Coordonnees(this.getX()+3,this.getY());
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesBleues()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesBleues().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
						
					}
				}
				// Si l'unité est bleue
				else {
					if(this.getType() == "Chevalier") {
						Coordonnees posAttaque = new Coordonnees(this.getX()-1,this.getY());
						for(Troupes victime : plateau.getUnitesRouges()) {
							if(victime.getPosition().getX() == posAttaque.getX() && victime.getPosition().getY() == posAttaque.getY()) {
									victime.setPV(victime.getPV() - this.getDegats());
									if(victime.getPV() <= 0) plateau.getUnitesRouges().remove(victime);
							}
						}
					}
					else if (this.getType() == "Mage") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX()-1,this.getY());
						Coordonnees posAttaque2 = new Coordonnees(this.getX()-2,this.getY());
						Coordonnees posAttaque3 = new Coordonnees(this.getX()-3,this.getY());
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);
						atqPossibles.add(posAttaque3);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesRouges()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesRouges().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
					}
					else if(this.getType() == "Archer") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX()-2,this.getY());
						Coordonnees posAttaque2 = new Coordonnees(this.getX()-3,this.getY());
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesRouges()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesRouges().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
						
					}
				}	
				System.out.println("Action effectuee : ATTACK_LEFT");
				break;
				
				
			case ATTACK_RIGHT:
				// Si l'unité est rouge
				if(Couleur == "rouge") {
					if(this.getType() == "Chevalier") {
						Coordonnees posAttaque = new Coordonnees(this.getX()+1,this.getY());
						for(Troupes victime : plateau.getUnitesBleues()) {
							if(victime.getPosition().getX() == posAttaque.getX() && victime.getPosition().getY() == posAttaque.getY()) {
									victime.setPV(victime.getPV() - this.getDegats());
									if(victime.getPV() <= 0) plateau.getUnitesBleues().remove(victime);
							}
						}
					}
					else if (this.getType() == "Mage") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX()+1,this.getY());
						Coordonnees posAttaque2 = new Coordonnees(this.getX()+2,this.getY());
						Coordonnees posAttaque3 = new Coordonnees(this.getX()+3,this.getY());
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);
						atqPossibles.add(posAttaque3);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesBleues()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesBleues().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
					}
					else if(this.getType() == "Archer") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX()+2,this.getY());
						Coordonnees posAttaque2 = new Coordonnees(this.getX()+3,this.getY());
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								if(plateau.getArbres().contains(posTest)) break;
								for(Troupes victime : plateau.getUnitesBleues()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesBleues().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
						
					}
				}
				// Si l'unité est bleue
				else {
					if(this.getType() == "Chevalier") {
						Coordonnees posAttaque = new Coordonnees(this.getX()+1,this.getY());
						for(Troupes victime : plateau.getUnitesRouges()) {
							if(victime.getPosition().getX() == posAttaque.getX() && victime.getPosition().getY() == posAttaque.getY()) {
									victime.setPV(victime.getPV() - this.getDegats());
									if(victime.getPV() <= 0) plateau.getUnitesRouges().remove(victime);
							}
						}
					}
					else if (this.getType() == "Mage") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX()+1,this.getY());
						Coordonnees posAttaque2 = new Coordonnees(this.getX()+2,this.getY());
						Coordonnees posAttaque3 = new Coordonnees(this.getX()+3,this.getY());
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);
						atqPossibles.add(posAttaque3);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesRouges()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesRouges().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
					}
					else if(this.getType() == "Archer") {
						Coordonnees posAttaque1 = new Coordonnees(this.getX()+2,this.getY());
						Coordonnees posAttaque2 = new Coordonnees(this.getX()+3,this.getY());
						boolean attaqueEffectuee = false;
						
						ArrayList<Coordonnees> atqPossibles = new ArrayList<Coordonnees>();
						atqPossibles.add(posAttaque1);
						atqPossibles.add(posAttaque2);

						for(Coordonnees posTest : atqPossibles) {
							if(attaqueEffectuee == false) {
								for(Troupes victime : plateau.getUnitesRouges()) {
									if(victime.getPosition().getX() == posTest.getX() && victime.getPosition().getY() == posTest.getY()) {
											victime.setPV(victime.getPV() - this.getDegats());
											if(victime.getPV() <= 0) plateau.getUnitesRouges().remove(victime);
											attaqueEffectuee = true;
									}
								}
							}
						}
						
					}
				}
				System.out.println("Action effectuee : ATTACK_RIGHT");
				break;
		}
	}

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

	

