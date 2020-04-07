package IG;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.RescaleOp;
import java.awt.image.BufferedImage;

import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.ArrayList;
import Troupes.*;

public class Plateau extends JPanel{
	private static final long serialVersionUID = 1L;
	
	String path ="src/Image/";

	private ArrayList<Troupes> unité_rouge;
	private ArrayList<Troupes> unité_bleue;
	private ArrayList<Coordonnées> arbres;
	private ArrayList<Coordonnées> rochers;
	private int largeur;
	private int hauteur;
	
	public Plateau(String file) {
		
		try {
			InputStream flux =new FileInputStream(file); 
			InputStreamReader lecture =new InputStreamReader(flux);
			BufferedReader tampon =new BufferedReader(lecture);
			
			String ligne;

			int nbX=0;
			int nbY=0;
			while ((ligne = tampon.readLine())!=null)
			{
				ligne = ligne.trim();
				if (nbX==0) {nbX = ligne.length();}
				else if (nbX != ligne.length()) throw new Exception("Toutes les lignes doivent avoir la même longueur");
				
				nbY++;
			}			
			tampon.close();
			
			largeur = nbX;
			hauteur = nbY;
			
			flux = new FileInputStream(file); 
			lecture = new InputStreamReader(flux);
			tampon = new BufferedReader(lecture);
			
			unité_bleue = new ArrayList<Troupes>();
			unité_rouge = new ArrayList<Troupes>();
			arbres = new ArrayList<Coordonnées>();
			rochers = new ArrayList<Coordonnées>();
			
			int h=0;
			while ((ligne = tampon.readLine())!=null) {
				for(int i=0; i<ligne.length(); ++i) {
					if(ligne.charAt(i)=='T') {
						Coordonnées coor = new Coordonnées(i,h);
						arbres.add(coor);
					}
					if(ligne.charAt(i)=='R') {
						Coordonnées coor = new Coordonnées(i,h);
						rochers.add(coor);
					}
					if(ligne.charAt(i)=='A') {
						Coordonnées coor = new Coordonnées(i,h);
						Archer archer = new Archer(coor,Direction.SUD);
						unité_bleue.add(archer);
					}
					if(ligne.charAt(i)=='a') {
						Coordonnées coor = new Coordonnées(i,h);
						Archer archer = new Archer(coor,Direction.NORD);
						unité_rouge.add(archer);
					}
					if(ligne.charAt(i)=='C') {
						Coordonnées coor = new Coordonnées(i,h);
						Chevalier chevalier = new Chevalier(coor,Direction.SUD);
						unité_bleue.add(chevalier);
					}
					if(ligne.charAt(i)=='c') {
						Coordonnées coor = new Coordonnées(i,h);
						Chevalier chevalier = new Chevalier(coor,Direction.NORD);
						unité_rouge.add(chevalier);
					}
					if(ligne.charAt(i)=='M') {
						Coordonnées coor = new Coordonnées(i,h);
						Mage mage = new Mage(coor,Direction.SUD);
						unité_bleue.add(mage);
					}
					if(ligne.charAt(i)=='m') {
						Coordonnées coor = new Coordonnées(i,h);
						Mage mage = new Mage(coor,Direction.NORD);
						unité_rouge.add(mage);
					}
					if(ligne.charAt(i)=='F') {
						Coordonnées coor = new Coordonnées(i,h);
						Chateau chateau = new Chateau(coor);
						unité_bleue.add(chateau);
					}
					if(ligne.charAt(i)=='f') {
						Coordonnées coor = new Coordonnées(i,h);
						Chateau chateau = new Chateau(coor);
						unité_rouge.add(chateau);
					}
				}
				++h;
			}
			tampon.close();

		}catch (Exception e){
			System.out.println("Erreur : "+e.getMessage());
		}
	}
	
	public void paint(Graphics g) {
		
		int fen_x = getSize().width;
		int fen_y = getSize().height;
		
		g.fillRect(0,0,fen_x,fen_y);
		
		double stepx=fen_x/(double)largeur;
		double stepy=fen_y/(double)hauteur;
		
		double position_x=0;
		
		for(int x=0; x<largeur; ++x){
			double position_y=0;
			for(int y=0; y<hauteur; ++y) {
				try {
					Image img = ImageIO.read(new File(path+"herbe.png"));
					g.drawImage(img, (int)position_x, (int)position_y, (int)stepx, (int)stepy, this);
				}catch (IOException e) {
					e.printStackTrace();
				}
				position_y+=stepy;
			}
			position_x+=stepx;
		}
		if(unité_bleue.size()>0 && unité_rouge.size()>0) {
			dessine_troupes(g);
		}
		dessine_arbres(g);
		dessine_rochers(g);
		
	}
	
	public void dessine_troupes(Graphics g) {
		int fen_x = getSize().width;
		int fen_y = getSize().height;

		double stepx = fen_x/(double)largeur;
		double stepy = fen_y/(double)hauteur;
		BufferedImage img = null;
		float[] rouge = {3,0.75f,0.75f,1.0f};
		float[] bleu = {0.75f,0.75f,3,1.0f};
		float[] contraste = {0,0,0,1.0f};
		
		for(int i=0; i<unité_bleue.size(); ++i) {
			int x = unité_bleue.get(i).getPosition().getX();
			int y = unité_bleue.get(i).getPosition().getY();
			Troupes unité = unité_bleue.get(i);
			double pos_x=x*stepx;
			double pos_y=y*stepy;
			if(unité.getType()=="Chevalier") {
				try {
					if(unité.getDirection()==Direction.SUD) {
						img = ImageIO.read(new File(path+"chevalier_avant.png"));
					}
					if(unité.getDirection()==Direction.NORD) {
						img = ImageIO.read(new File(path+"chevalier_arriere.png"));
					}
					if(unité.getDirection()==Direction.OUEST) {
						img = ImageIO.read(new File(path+"chevalier_gauche.png"));
					}
					if(unité.getDirection()==Direction.EST) {
						img = ImageIO.read(new File(path+"chevalier_droite.png"));
					}
					RescaleOp op = new RescaleOp(bleu, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(unité.getType()=="Archer") {
				try {
					if(unité.getDirection()==Direction.SUD) {
						img = ImageIO.read(new File(path+"Bas_Repos.png"));
					}
					if(unité.getDirection()==Direction.NORD) {
						img = ImageIO.read(new File(path+"Haut_Repos.png"));
					}
					if(unité.getDirection()==Direction.OUEST) {
						img = ImageIO.read(new File(path+"Gauche_Repos.png"));
					}
					if(unité.getDirection()==Direction.EST) {
						img = ImageIO.read(new File(path+"Droite_Repos.png"));
					}
					RescaleOp op = new RescaleOp(bleu, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(unité.getType()=="Mage") {
				try {
					if(unité.getDirection()==Direction.SUD) {
						img = ImageIO.read(new File(path+"mage_avant.png"));
					}
					if(unité.getDirection()==Direction.NORD) {
						img = ImageIO.read(new File(path+"mage_arriere.png"));
					}
					if(unité.getDirection()==Direction.OUEST) {
						img = ImageIO.read(new File(path+"mage_gauche.png"));
					}
					if(unité.getDirection()==Direction.EST) {
						img = ImageIO.read(new File(path+"mage_droite.png"));
					}
					RescaleOp op = new RescaleOp(bleu, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(unité.getType()=="Château") {
				try {
					img = ImageIO.read(new File(path+"chateau.png"));
					RescaleOp op = new RescaleOp(bleu, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		for(int i=0; i<unité_rouge.size(); ++i) {
			int x = unité_rouge.get(i).getPosition().getX();
			int y = unité_rouge.get(i).getPosition().getY();
			Troupes unité = unité_rouge.get(i);
			double pos_x=x*stepx;
			double pos_y=y*stepy;
			
			if(unité_rouge.get(i).getType()=="Chevalier") {
				try {
					if(unité.getDirection()==Direction.SUD) {
						img = ImageIO.read(new File(path+"warrior_avant.png"));
					}
					if(unité.getDirection()==Direction.NORD) {
						img = ImageIO.read(new File(path+"warrior_arriere.png"));
					}
					if(unité.getDirection()==Direction.OUEST) {
						img = ImageIO.read(new File(path+"warrior_gauche.png"));
					}
					if(unité.getDirection()==Direction.EST) {
						img = ImageIO.read(new File(path+"warrior_droite.png"));
					}
					RescaleOp op = new RescaleOp(rouge,contraste,null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(unité_rouge.get(i).getType()=="Archer") {
				try {
					if(unité.getDirection()==Direction.SUD) {
						img = ImageIO.read(new File(path+"Bas_Repos.png"));
					}
					if(unité.getDirection()==Direction.NORD) {
						img = ImageIO.read(new File(path+"Haut_Repos.png"));
					}
					if(unité.getDirection()==Direction.OUEST) {
						img = ImageIO.read(new File(path+"Gauche_Repos.png"));
					}
					if(unité.getDirection()==Direction.EST) {
						img = ImageIO.read(new File(path+"Droite_Repos.png"));
					}
					RescaleOp op = new RescaleOp(rouge, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(unité_rouge.get(i).getType()=="Mage") {
				try {
					if(unité.getDirection()==Direction.SUD) {
						img = ImageIO.read(new File(path+"witch_avant.png"));
					}
					if(unité.getDirection()==Direction.NORD) {
						img = ImageIO.read(new File(path+"witch_arriere.png"));
					}
					if(unité.getDirection()==Direction.OUEST) {
						img = ImageIO.read(new File(path+"witch_gauche.png"));
					}
					if(unité.getDirection()==Direction.EST) {
						img = ImageIO.read(new File(path+"witch_droite.png"));
					}
					RescaleOp op = new RescaleOp(rouge, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(unité_rouge.get(i).getType()=="Château") {
				try {
					img = ImageIO.read(new File(path+"chateau.png"));
					RescaleOp op = new RescaleOp(rouge, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void dessine_arbres(Graphics g) {
		int fen_x = getSize().width;
		int fen_y = getSize().height;

		double stepx = fen_x/(double)largeur;
		double stepy = fen_y/(double)hauteur;
		
		for(int i=0; i<arbres.size(); ++i) {
			int x = arbres.get(i).getX();
			int y = arbres.get(i).getY();
			double pos_x=x*stepx;
			double pos_y=y*stepy;
			
			try {
				Image img = ImageIO.read(new File(path+"arbre.png"));
				g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void dessine_rochers(Graphics g) {
		int fen_x = getSize().width;
		int fen_y = getSize().height;

		double stepx = fen_x/(double)largeur;
		double stepy = fen_y/(double)hauteur;
		
		for(int i=0; i<rochers.size(); ++i) {
			int x = rochers.get(i).getX();
			int y = rochers.get(i).getY();
			double pos_x=x*stepx;
			double pos_y=y*stepy;
			
			try {
				Image img = ImageIO.read(new File(path+"rocher.png"));
				g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	public ArrayList<Troupes> getUnité_rouge() {
		return unité_rouge;
	}

	public void setUnité_rouge(ArrayList<Troupes> unité_rouge) {
		this.unité_rouge = unité_rouge;
	}

	public ArrayList<Troupes> getUnité_bleue() {
		return unité_bleue;
	}

	public void setUnité_bleue(ArrayList<Troupes> unité_bleue) {
		this.unité_bleue = unité_bleue;
	}

	public ArrayList<Coordonnées> getArbres() {
		return arbres;
	}

	public void setArbres(ArrayList<Coordonnées> arbres) {
		this.arbres = arbres;
	}

	public ArrayList<Coordonnées> getRochers() {
		return rochers;
	}

	public void setRochers(ArrayList<Coordonnées> rochers) {
		this.rochers = rochers;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}
	
	public Troupes getChateau(ArrayList<Troupes> unites) {
		for(Troupes unite : unites) {
			if(unite.getType() == "Château") return unite;
		}
		return null;
	}
	
	public Troupes getTroupeBleu(Troupes troupe) {
		boolean trouver=false;
		int i=0;
		int tx = troupe.getPosition().getX();
		int ty = troupe.getPosition().getY();
		while(trouver==false && i<unité_bleue.size()) {
			int x = unité_bleue.get(i).getPosition().getX();
			int y = unité_bleue.get(i).getPosition().getY();
			if(tx==x && ty==y) {
				trouver = true;
				return unité_bleue.get(i);
			}
			else {
				++i;
			}
		}
		return null;
	}
	
	public Troupes getTroupeBleu(Coordonnées coor) {
		boolean trouver=false;
		int i=0;
		int tx = coor.getX();
		int ty = coor.getY();
		while(trouver==false && i<unité_bleue.size()) {
			int x = unité_bleue.get(i).getPosition().getX();
			int y = unité_bleue.get(i).getPosition().getY();
			if(tx==x && ty==y) {
				trouver = true;
				return unité_bleue.get(i);
			}
			else {
				++i;
			}
		}
		return null;
	}
	
	public Troupes getTroupeRouge(Troupes troupe) {
		boolean trouver=false;
		int i=0;
		int tx = troupe.getPosition().getX();
		int ty = troupe.getPosition().getY();
		while(trouver==false && i<unité_rouge.size()) {
			int x = unité_rouge.get(i).getPosition().getX();
			int y = unité_rouge.get(i).getPosition().getY();
			if(tx==x && ty==y) {
				trouver = true;
			}
			else {
				++i;
			}
		}
		return unité_rouge.get(i);
	}
	
	public Troupes getTroupeRouge(Coordonnées coor) {
		boolean trouver=false;
		int i=0;
		int tx = coor.getX();
		int ty = coor.getY();
		while(trouver==false && i<unité_rouge.size()) {
			int x = unité_rouge.get(i).getPosition().getX();
			int y = unité_rouge.get(i).getPosition().getY();
			if(tx==x && ty==y) {
				trouver = true;
				return unité_rouge.get(i);
			}
			else {
				++i;
			}
		}
		return null;
	}
	
}
