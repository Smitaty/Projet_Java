package IG;

import java.awt.Graphics;
import java.awt.Graphics2D;
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

	private ArrayList<Troupes> unitesRouges = new ArrayList<Troupes>();
	private ArrayList<Troupes> unitesBleues = new ArrayList<Troupes>();
	private ArrayList<Coordonnees> arbres = new ArrayList<Coordonnees>();
	private ArrayList<Coordonnees> rochers = new ArrayList<Coordonnees>();

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
			
			unitesBleues = new ArrayList<Troupes>();
			unitesRouges = new ArrayList<Troupes>();
			arbres = new ArrayList<Coordonnees>();
			rochers = new ArrayList<Coordonnees>();
			
			int h=0;
			while ((ligne = tampon.readLine())!=null) {
				for(int i=0; i<ligne.length(); ++i) {
					if(ligne.charAt(i)=='T') {
						Coordonnees coor = new Coordonnees(i,h);
						arbres.add(coor);
					}
					if(ligne.charAt(i)=='R') {
						Coordonnees coor = new Coordonnees(i,h);
						rochers.add(coor);
					}
					if(ligne.charAt(i)=='A') {
						Coordonnees coor = new Coordonnees(i,h);
						Archer archer = new Archer(coor);
						unitesBleues.add(archer);
					}
					if(ligne.charAt(i)=='a') {
						Coordonnees coor = new Coordonnees(i,h);
						Archer archer = new Archer(coor);
						unitesRouges.add(archer);
					}
					if(ligne.charAt(i)=='C') {
						Coordonnees coor = new Coordonnees(i,h);
						Chevalier chevalier = new Chevalier(coor);
						unitesBleues.add(chevalier);
					}
					if(ligne.charAt(i)=='c') {
						Coordonnees coor = new Coordonnees(i,h);
						Chevalier chevalier = new Chevalier(coor);
						unitesRouges.add(chevalier);
					}
					if(ligne.charAt(i)=='M') {
						Coordonnees coor = new Coordonnees(i,h);
						Mage mage = new Mage(coor);
						unitesBleues.add(mage);
					}
					if(ligne.charAt(i)=='m') {
						Coordonnees coor = new Coordonnees(i,h);
						Mage mage = new Mage(coor);
						unitesRouges.add(mage);
					}
					if(ligne.charAt(i)=='F') {
						Coordonnees coor = new Coordonnees(i,h);
						Chateau chateau = new Chateau(coor);
						unitesBleues.add(chateau);
					}
					if(ligne.charAt(i)=='f') {
						Coordonnees coor = new Coordonnees(i,h);
						Chateau chateau = new Chateau(coor);
						unitesRouges.add(chateau);
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
		
		dessine_troupes(g);
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
		
		for(int i=0; i<unitesBleues.size(); ++i) {
			int x = unitesBleues.get(i).getPosition().getX();
			int y = unitesBleues.get(i).getPosition().getY();
			
			double pos_x=x*stepx;
			double pos_y=y*stepy;
			if(unitesBleues.get(i).getType()=="Chevalier") {
				try {
					img = ImageIO.read(new File(path+"chevalier_avant.png"));
					RescaleOp op = new RescaleOp(bleu, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(unitesBleues.get(i).getType()=="Archer") {
				try {
					img = ImageIO.read(new File(path+"Bas_Repos.png"));
					RescaleOp op = new RescaleOp(bleu, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(unitesBleues.get(i).getType()=="Mage") {
				try {
					img = ImageIO.read(new File(path+"mage_avant.png"));
					RescaleOp op = new RescaleOp(bleu, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(unitesBleues.get(i).getType()=="Chateau") {
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
		
		for(int i=0; i<unitesRouges.size(); ++i) {
			int x = unitesRouges.get(i).getPosition().getX();
			int y = unitesRouges.get(i).getPosition().getY();
			double pos_x=x*stepx;
			double pos_y=y*stepy;
			
			if(unitesRouges.get(i).getType()=="Chevalier") {
				try {
					img = ImageIO.read(new File(path+"chevalier_avant.png"));
					RescaleOp op = new RescaleOp(rouge,contraste,null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(unitesRouges.get(i).getType()=="Archer") {
				try {
					img = ImageIO.read(new File(path+"Bas_Repos.png"));
					RescaleOp op = new RescaleOp(rouge, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(unitesRouges.get(i).getType()=="Mage") {
				try {
					img = ImageIO.read(new File(path+"mage_avant.png"));
					RescaleOp op = new RescaleOp(rouge, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(unitesRouges.get(i).getType()=="Chateau") {
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
	


	
	public ArrayList<Troupes> getUnitesRouges() {
		return unitesRouges;
	}

	public void setUnitesRouges(ArrayList<Troupes> unitesRouges) {
		this.unitesRouges = unitesRouges;
	}

	public ArrayList<Troupes> getUnitesBleues() {
		return unitesBleues;
	}

	public void setUnitesBleues(ArrayList<Troupes> unité_bleue) {
		this.unitesBleues = unité_bleue;
	}

	public ArrayList<Coordonnees> getArbres() {
		return arbres;
	}

	public void setArbres(ArrayList<Coordonnees> arbres) {
		this.arbres = arbres;
	}

	public ArrayList<Coordonnees> getRochers() {
		return rochers;
	}

	public void setRochers(ArrayList<Coordonnees> rochers) {
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
			if(unite.getType() == "Chateau") return unite;
		}
		return null;
	}
	
	
	
	
}
