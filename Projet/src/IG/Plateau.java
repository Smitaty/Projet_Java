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
	String file;
	
	private ArrayList<Troupes> unite_rouge;
	private ArrayList<Troupes> unite_bleue;
	private ArrayList<Coordonnees> arbres;
	private ArrayList<Coordonnees> rochers;
	private int largeur;
	private int hauteur;
	
	private ArrayList<BufferedImage> imagesArcher = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> imagesChevalier = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> imagesWarrior = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> imagesMage = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> imagesWitch = new ArrayList<BufferedImage>();
	
	boolean premierefois = true;
	int cpt=1;
	int cptProjectile=1;
	
	public Plateau(String file) {
		this.file=file;
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
			
			unite_bleue = new ArrayList<Troupes>();
			unite_rouge = new ArrayList<Troupes>();
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
						Archer archer = new Archer(coor,Direction.SUD);
						if(imagesArcher.size()==0) {
							LoadImageArcher();
						}
						unite_bleue.add(archer);
					}
					if(ligne.charAt(i)=='a') {
						Coordonnees coor = new Coordonnees(i,h);
						Archer archer = new Archer(coor,Direction.NORD);
						if(imagesArcher.size()==0)
							LoadImageArcher();
						unite_rouge.add(archer);
					}
					if(ligne.charAt(i)=='C') {
						Coordonnees coor = new Coordonnees(i,h);
						Chevalier chevalier = new Chevalier(coor,Direction.SUD);
						if(imagesChevalier.size()==0)
							LoadImageChevalier();
						unite_bleue.add(chevalier);
					}
					if(ligne.charAt(i)=='c') {
						Coordonnees coor = new Coordonnees(i,h);
						Chevalier chevalier = new Chevalier(coor,Direction.NORD);
						if(imagesWarrior.size()==0)
							LoadImageWarrior();
						unite_rouge.add(chevalier);
					}
					if(ligne.charAt(i)=='M') {
						Coordonnees coor = new Coordonnees(i,h);
						Mage mage = new Mage(coor,Direction.SUD);
						if(imagesMage.size()==0)
							LoadImageMage();
						unite_bleue.add(mage);
					}
					if(ligne.charAt(i)=='m') {
						Coordonnees coor = new Coordonnees(i,h);
						Mage mage = new Mage(coor,Direction.NORD);
						if(imagesWitch.size()==0)
							LoadImageWitch();
						unite_rouge.add(mage);
					}
					if(ligne.charAt(i)=='F') {
						Coordonnees coor = new Coordonnees(i,h);
						Chateau chateau = new Chateau(coor);
						unite_bleue.add(chateau);
					}
					if(ligne.charAt(i)=='f') {
						Coordonnees coor = new Coordonnees(i,h);
						Chateau chateau = new Chateau(coor);
						unite_rouge.add(chateau);
					}
				}
				++h;
			}
			tampon.close();

		}catch (Exception e){
			System.out.println("Erreur : "+e.getMessage());
		}
	}
	
	public void LoadImageArcher() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path+"Haut_Repos.png"));
		}catch(Exception e) {
			
		}
		imagesArcher.add(img);
		try {
			img = ImageIO.read(new File(path+"Bas_Repos.png"));
		}catch(Exception e) {
			
		}
		imagesArcher.add(img);
		try {
			img = ImageIO.read(new File(path+"Gauche_Repos.png"));
		}catch(Exception e) {
			
		}
		imagesArcher.add(img);
		try {
			img = ImageIO.read(new File(path+"Droite_Repos.png"));
		}catch(Exception e) {
			
		}
		imagesArcher.add(img);
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"Haut_Attaque"+i+".png"));
			}catch(Exception e) {
				
			}
			imagesArcher.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"Bas_Attaque"+i+".png"));
			}catch(Exception e) {
				
			}
			imagesArcher.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"Gauche_Attaque"+i+".png"));
			}catch(Exception e) {
				
			}
			imagesArcher.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"Droite_Attaque"+i+".png"));
			}catch(Exception e) {
				
			}
			imagesArcher.add(img);
		}
	}
		
	public void LoadImageChevalier() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path+"chevalier_arriere.png"));
		}catch (Exception e) {
			
		}
		imagesChevalier.add(img);
		try {
			img = ImageIO.read(new File(path+"chevalier_avant.png"));
		}catch (Exception e) {
			
		}
		imagesChevalier.add(img);
		try {
			img = ImageIO.read(new File(path+"chevalier_gauche.png"));
		}catch (Exception e) {
			
		}
		imagesChevalier.add(img);
		try {
			img = ImageIO.read(new File(path+"chevalier_droite.png"));
		}catch (Exception e) {
			
		}
		imagesChevalier.add(img);
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"chevalier_attaque2_arriere_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesChevalier.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"chevalier_attaque2_avant_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesChevalier.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"chevalier_attaque2_gauche_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesChevalier.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"chevalier_attaque2_droite_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesChevalier.add(img);
		}
	}

	public void LoadImageWarrior() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path+"warrior_arriere.png"));
		}catch (Exception e) {
			
		}
		imagesWarrior.add(img);
		try {
			img = ImageIO.read(new File(path+"warrior_avant.png"));
		}catch (Exception e) {
			
		}
		imagesWarrior.add(img);
		try {
			img = ImageIO.read(new File(path+"warrior_gauche.png"));
		}catch (Exception e) {
			
		}
		imagesWarrior.add(img);
		try {
			img = ImageIO.read(new File(path+"warrior_droite.png"));
		}catch (Exception e) {
			
		}
		imagesWarrior.add(img);
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"warrior_attaque2_arriere_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesWarrior.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"warrior_attaque2_avant_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesWarrior.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"warrior_attaque2_gauche_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesWarrior.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"warrior_attaque2_droite_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesWarrior.add(img);
		}
	}
	
	public void LoadImageMage() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path+"mage_arriere.png"));
		}catch (Exception e) {
			
		}
		imagesMage.add(img);
		try {
			img = ImageIO.read(new File(path+"mage_avant.png"));
		}catch (Exception e) {
			
		}
		imagesMage.add(img);
		try {
			img = ImageIO.read(new File(path+"mage_gauche.png"));
		}catch (Exception e) {
			
		}
		imagesMage.add(img);
		try {
			img = ImageIO.read(new File(path+"mage_droite.png"));
		}catch (Exception e) {
			
		}
		imagesMage.add(img);
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"mage_attaque2_arriere_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesMage.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"mage_attaque2_avant_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesMage.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"mage_attaque2_gauche_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesMage.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"mage_attaque2_droite_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesMage.add(img);
		}
	}
	
	public void LoadImageWitch() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path+"witch_arriere.png"));
		}catch (Exception e) {
			
		}
		imagesWitch.add(img);
		try {
			img = ImageIO.read(new File(path+"witch_avant.png"));
		}catch (Exception e) {
			
		}
		imagesWitch.add(img);
		try {
			img = ImageIO.read(new File(path+"witch_gauche.png"));
		}catch (Exception e) {
			
		}
		imagesWitch.add(img);
		try {
			img = ImageIO.read(new File(path+"witch_droite.png"));
		}catch (Exception e) {
			
		}
		imagesWitch.add(img);
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"witch_attaque2_arriere_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesWitch.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"witch_attaque2_avant_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesWitch.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"witch_attaque2_gauche_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesWitch.add(img);
		}
		for(int i=1; i<5; ++i) {
			try {
				img = ImageIO.read(new File(path+"witch_attaque2_droite_"+i+".png"));
			}catch (Exception e) {
				
			}
			imagesWitch.add(img);
		}
	}
	
	public void paint(Graphics g) {
		int fen_x = getSize().width;
		int fen_y = getSize().height;
		
		g.fillRect(0,0,fen_x,fen_y);
		
		double stepx=fen_x/(double)largeur;
		double stepy=fen_y/(double)hauteur;
		
		double position_x=0;
		Image img = null;
		try {
	        img = ImageIO.read(new File(path+"herbe.png"));
	        }catch (IOException e) {
	            e.printStackTrace();
	        }
		
		for(int x=0; x<largeur; ++x){
			double position_y=0;
			for(int y=0; y<hauteur; ++y) {
					g.drawImage(img, (int)position_x, (int)position_y, (int)stepx, (int)stepy, this);
				position_y+=stepy;
			}
			position_x+=stepx;
		}
		if(unite_bleue.size()>0 && unite_rouge.size()>0) {
			dessine_troupes(g);
		}
		dessine_arbres(g);
		dessine_rochers(g);
		EnleveTroupeMorte();
		if(premierefois) {
			cpt=1;
			cptProjectile=1;
			premierefois=false;
		}
		else {
			if(cpt>=5) {
				cpt=1;
				cptProjectile=1;
			}
			else {
				++cpt;
				if(cpt>3)
					++cptProjectile;
				this.repaint();
			}
		}
	}
	
	public void EnleveTroupeMorte() {
		for(int i=0; i<unite_bleue.size(); ++i) {
			if(unite_bleue.get(i).getPV()<=0 && unite_bleue.get(i).getType()!="Chateau")
				this.unite_bleue.remove(i);
		}
		for(int i=0; i<unite_rouge.size(); ++i) {
			if(unite_rouge.get(i).getPV()<=0 && unite_rouge.get(i).getType()!="Chateau")
				this.unite_rouge.remove(i);
		}
	}
	
	public void dessine_troupes(Graphics g) {
		int fen_x = getSize().width;
		int fen_y = getSize().height;
		double stepx = fen_x/(double)largeur;
		double stepy = fen_y/(double)hauteur;
		BufferedImage img = null;
		
		// Couleur pour les filtres
		float[] rouge = {3,0.75f,0.75f,1.0f};
		float[] bleu = {0.75f,0.75f,3,1.0f};
		float[] contraste = {0,0,0,1.0f};
		
		// Affichage des troupes bleues
		
		for(int i=0; i<unite_bleue.size(); ++i) {
			int x = unite_bleue.get(i).getPosition().getX();
			int y = unite_bleue.get(i).getPosition().getY();
			Troupes unite = unite_bleue.get(i);
			double pos_x=x*stepx;
			double pos_y=y*stepy;
			if(unite.getAction()==TroupesAction.ATTACK1) {
				dessine_attaque(g,unite,true);
				if(cpt>=5)
					unite.setAction(TroupesAction.STOP); // Pour stopper l'animation d'attaque
			}
			else {
				if(unite.getType()=="Chevalier") {
					if(unite.getDirection()==Direction.SUD) {
						img = imagesChevalier.get(1);
					}
					if(unite.getDirection()==Direction.NORD) {
						img = imagesChevalier.get(0);
					}
					if(unite.getDirection()==Direction.OUEST) {
						img = imagesChevalier.get(2);
					}
					if(unite.getDirection()==Direction.EST) {
						img = imagesChevalier.get(3);
					}
					RescaleOp op = new RescaleOp(bleu, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}
				if(unite.getType()=="Archer") {
						if(unite.getDirection()==Direction.SUD) {
							img = imagesArcher.get(1);
						}
						if(unite.getDirection()==Direction.NORD) {
							img = imagesArcher.get(0);
						}
						if(unite.getDirection()==Direction.OUEST) {
							img = imagesArcher.get(2);
						}
						if(unite.getDirection()==Direction.EST) {
							img = imagesArcher.get(3);
						}
						RescaleOp op = new RescaleOp(bleu, contraste, null);
						img = op.filter( img, null);
						g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}
				if(unite.getType()=="Mage") {
					if(unite.getDirection()==Direction.SUD) {
						img = imagesMage.get(1);
					}
					if(unite.getDirection()==Direction.NORD) {
						img = imagesMage.get(0);
					}
					if(unite.getDirection()==Direction.OUEST) {
						img = imagesMage.get(2);
					}
					if(unite.getDirection()==Direction.EST) {
						img = imagesMage.get(3);
					}
					RescaleOp op = new RescaleOp(bleu, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}
				if(unite.getType()=="Chateau") {
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
		}
		
		for(int i=0; i<unite_rouge.size(); ++i) {
			int x = unite_rouge.get(i).getPosition().getX();
			int y = unite_rouge.get(i).getPosition().getY();
			Troupes unite = unite_rouge.get(i);
			double pos_x=x*stepx;
			double pos_y=y*stepy;
			if(unite.getAction()==TroupesAction.ATTACK1) {
				dessine_attaque(g,unite,false);
				if(cpt>=5)
					unite.setAction(TroupesAction.STOP);
			}
			else {
				if(unite.getType()=="Chevalier") {
					if(unite.getDirection()==Direction.SUD) {
						img = imagesWarrior.get(1);
					}
					if(unite.getDirection()==Direction.NORD) {
						img = imagesWarrior.get(0);
					}
					if(unite.getDirection()==Direction.OUEST) {
						img = imagesWarrior.get(2);
					}
					if(unite.getDirection()==Direction.EST) {
						img = imagesWarrior.get(3);
					}
					RescaleOp op = new RescaleOp(rouge,contraste,null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}
				if(unite.getType()=="Archer") {
					if(unite.getDirection()==Direction.SUD) {
						img = imagesArcher.get(1);
					}
					if(unite.getDirection()==Direction.NORD) {
						img = imagesArcher.get(0);
					}
					if(unite.getDirection()==Direction.OUEST) {
						img = imagesArcher.get(2);
					}
					if(unite.getDirection()==Direction.EST) {
						img = imagesArcher.get(3);
					}
					RescaleOp op = new RescaleOp(rouge, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}
				if(unite.getType()=="Mage") {
					if(unite.getDirection()==Direction.SUD) {
						img = imagesWitch.get(1);
					}
					if(unite.getDirection()==Direction.NORD) {
						img = imagesWitch.get(0);
					}
					if(unite.getDirection()==Direction.OUEST) {
						img = imagesWitch.get(2);
					}
					if(unite.getDirection()==Direction.EST) {
						img = imagesWitch.get(3);
					}
					RescaleOp op = new RescaleOp(rouge, contraste, null);
					img = op.filter( img, null);
					g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				}
				if(unite.getType()=="Chateau") {
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
	}
	
	public void dessine_attaque(Graphics g, Troupes troupe, boolean estBleu) {
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		
		int fen_x = getSize().width;
		int fen_y = getSize().height;
		
		double stepx = fen_x/(double)largeur;
		double stepy = fen_y/(double)hauteur;
		
		double pos_x = x*stepx;
		double pos_y = y*stepy;
		
		float[] rouge = {3,0.75f,0.75f,1.0f};
		float[] bleu = {0.75f,0.75f,3,1.0f};
		float[] contraste = {0,0,0,1.0f};
		
		BufferedImage img = null;
		if(troupe.getType()=="Chevalier") {
			if(estBleu) {
				switch(troupe.getDirection()) {
					case NORD:
						if(cpt<5) {
							img = imagesChevalier.get(3+cpt);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						else {
							img = imagesChevalier.get(0);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					break;
					case SUD:
						if(cpt<5) {
							img = imagesChevalier.get(7+cpt);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						else {
							img = imagesChevalier.get(1);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						break;
					case OUEST:
						if(cpt<5) {
							img = imagesChevalier.get(11+cpt);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						else {
							img = imagesChevalier.get(2);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						break;
					case EST:
						if(cpt<5) {
							img = imagesChevalier.get(15+cpt);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						else {
							img = imagesChevalier.get(3);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						break;
				}
			}
			else {
				switch(troupe.getDirection()) {
					case NORD:
						if(cpt<5) {
							img = imagesWarrior.get(3+cpt);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						else {
							img = imagesWarrior.get(0);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						break;
					case SUD:
						if(cpt<5) {
							img = imagesWarrior.get(7+cpt);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						else {
							img = imagesWarrior.get(1);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						break;
					case OUEST:
						if(cpt<5) {
							img = imagesWarrior.get(11+cpt);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						else {
							img = imagesWarrior.get(2);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						break;
					case EST:
						if(cpt<5) {
							img = imagesWarrior.get(15+cpt);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						else {
							img = imagesWarrior.get(3);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
						break;
				}
			}
		}
		if(troupe.getType()=="Archer") {
			switch(troupe.getDirection()) {
				case NORD:
					try {
						if(cpt<5) {
							img = imagesArcher.get(3+cpt);
							RescaleOp op;
							if(estBleu)
								op = new RescaleOp(bleu, contraste, null);
							else
								op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
							if(cpt>=3) {
								Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu);
								if(cible!=null) {
									int range = Math.abs(cible.getPosition().getY()-y);
									if(cptProjectile<=range) {
										img = ImageIO.read(new File(path+"Haut.png"));
										g.drawImage(img, (int)pos_x, (int)(pos_y - (stepy * cptProjectile)), (int)stepx, (int)stepy, this);
									}
								}
							}
						}
						else {
							img = imagesArcher.get(0);
							RescaleOp op;
							if(estBleu)
								op = new RescaleOp(bleu, contraste, null);
							else
								op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case SUD:
					try {
						if(cpt<5) {
							img = imagesArcher.get(7+cpt);
							RescaleOp op;
							if(estBleu)
								op = new RescaleOp(bleu, contraste, null);
							else
								op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
							if(cpt>=3) {
								Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu);
								if(cible!=null) {
									int range = Math.abs(cible.getPosition().getY()-y);
									if(cptProjectile<=range) {
										img = ImageIO.read(new File(path+"Bas.png"));
										g.drawImage(img, (int)pos_x, (int)(pos_y + (stepy * cptProjectile)), (int)stepx, (int)stepy, this);
									}
								}
							}
						}
						else {
							img = imagesArcher.get(1);
							RescaleOp op;
							if(estBleu)
								op = new RescaleOp(bleu, contraste, null);
							else
								op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case OUEST:
					try {
						if(cpt<5) {
							img = imagesArcher.get(11+cpt);
							RescaleOp op;
							if(estBleu)
								op = new RescaleOp(bleu, contraste, null);
							else
								op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
							if(cpt>=3) {
								Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu);
								if(cible!=null) {
									int range = Math.abs(cible.getPosition().getX()-x);
									if(cptProjectile<=range) {
										img = ImageIO.read(new File(path+"Gauche.png"));
										g.drawImage(img, (int)(pos_x - (stepx * cptProjectile)), (int)pos_y , (int)stepx, (int)stepy, this);
									}
								}
							}
						}
						else {
							img = imagesArcher.get(2);
							RescaleOp op;
							if(estBleu)
								op = new RescaleOp(bleu, contraste, null);
							else
								op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case EST:
					try {
						if(cpt<5) {
							img = imagesArcher.get(15+cpt);
							RescaleOp op;
							if(estBleu)
								op = new RescaleOp(bleu, contraste, null);
							else
								op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
							if(cpt>=3) {
								Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu);
								if(cible!=null) {
									int range = Math.abs(cible.getPosition().getX()-x);
									if(cptProjectile<=range) {
										img = ImageIO.read(new File(path+"Droite.png"));
										g.drawImage(img, (int)(pos_x + (stepx * cptProjectile)), (int)pos_y, (int)stepx, (int)stepy, this);
									}
								}
							}
						}
						else {
							img = imagesArcher.get(3);
							RescaleOp op;
							if(estBleu)
								op = new RescaleOp(bleu, contraste, null);
							else
								op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					break;
			}
		}
		if(troupe.getType()=="Mage") {
			if(estBleu) {
				switch(troupe.getDirection()) {
				case NORD:
					try {
						if(cpt<5) {
							img = imagesMage.get(3+cpt);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
							if(cpt>=3) {
								Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu);
								if(cible!=null) {
									int range = Math.abs(cible.getPosition().getY()-y);
									if(cptProjectile<=range) {
										img = ImageIO.read(new File(path+"FeuHaut.png"));
										g.drawImage(img, (int)pos_x, (int)(pos_y - (stepy * cptProjectile)), (int)stepx, (int)stepy, this);
									}
								}
							}
						}
						else {
							img = imagesMage.get(0);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case SUD:
					try {
						if(cpt<5) {
							img = imagesMage.get(7+cpt);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
							if(cpt>=3) {
								Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu);
								if(cible!=null) {
									int range = Math.abs(cible.getPosition().getY()-y);
									if(cptProjectile<=range) {
										img = ImageIO.read(new File(path+"FeuBas.png"));
										g.drawImage(img, (int)pos_x, (int)(pos_y + (stepy * cptProjectile)), (int)stepx, (int)stepy, this);
									}
								}
							}
						}
						else {
							img = imagesMage.get(1);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case OUEST:
					try {
						if(cpt<5) {
							img = imagesMage.get(11+cpt);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
							if(cpt>=3) {
								Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu);
								if(cible!=null) {
									int range = Math.abs(cible.getPosition().getX()-x);
									if(cptProjectile<=range) {
										img = ImageIO.read(new File(path+"FeuGauche.png"));
										g.drawImage(img, (int)(pos_x - (stepx * cptProjectile)), (int)pos_y, (int)stepx, (int)stepy, this);
									}
								}
							}
						}
						else {
							img = imagesMage.get(2);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case EST:
					try {
						if(cpt<5) {
							img = imagesMage.get(15+cpt);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
							if(cpt>=3) {
								Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu);
								if(cible!=null) {
									int range = Math.abs(cible.getPosition().getX()-x);
									if(cptProjectile<=range) {
										img = ImageIO.read(new File(path+"FeuDroite.png"));
										g.drawImage(img, (int)(pos_x + (stepx * cptProjectile)), (int)pos_y, (int)stepx, (int)stepy, this);
									}
								}
							}
						}
						else {
							img = imagesMage.get(3);
							RescaleOp op = new RescaleOp(bleu, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
			}
			else {
				switch(troupe.getDirection()) {
				case NORD:
					try {
						if(cpt<5) {
							img = imagesWitch.get(3+cpt);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
							if(cpt>=3) {
								Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu);
								if(cible!=null) {
									int range = Math.abs(cible.getPosition().getY()-y);
									if(cptProjectile<=range) {
										img = ImageIO.read(new File(path+"FeuHaut.png"));
										g.drawImage(img, (int)pos_x, (int)(pos_y - (stepy * cptProjectile)), (int)stepx, (int)stepy, this);
									}
								}
							}
						}
						else {
							img = imagesWitch.get(0);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case SUD:
					try {
						if(cpt<5) {
							img = imagesWitch.get(7+cpt);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
							if(cpt>=3) {
								Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu);
								if(cible!=null) {
									int range = Math.abs(cible.getPosition().getY()-y);
									if(cptProjectile<=range) {
										img = ImageIO.read(new File(path+"FeuBas.png"));
										g.drawImage(img, (int)pos_x, (int)(pos_y + (stepy * cptProjectile)), (int)stepx, (int)stepy, this);
									}
								}
							}
						}
						else {
							img = imagesWitch.get(1);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case OUEST:
					try {
						if(cpt<5) {
							img = imagesWitch.get(11+cpt);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
							if(cpt>=3) {
								Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu);
								if(cible!=null) {
									int range = Math.abs(cible.getPosition().getX()-x);
									if(cptProjectile<=range) {
										img = ImageIO.read(new File(path+"FeuGauche.png"));
										g.drawImage(img, (int)(pos_x - (stepx * cptProjectile)), (int)pos_y, (int)stepx, (int)stepy, this);
									}
								}
							}
						}
						else {
							img = imagesWitch.get(2);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case EST:
					try {
						if(cpt<5) {
							img = imagesWitch.get(15+cpt);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
							if(cpt>=3) {
								Troupes cible = chercheCible(troupe,troupe.getDirection(),estBleu);
								if(cible!=null) {
									int range = Math.abs(cible.getPosition().getX()-x);
									if(cptProjectile<=range) {
										img = ImageIO.read(new File(path+"FeuDroite.png"));
										g.drawImage(img, (int)(pos_x + (stepx * cptProjectile)), (int)pos_y, (int)stepx, (int)stepy, this);
									}
								}
							}
						}
						else {
							img = imagesWitch.get(3);
							RescaleOp op = new RescaleOp(rouge, contraste, null);
							img = op.filter( img, null);
							g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}
					break;
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
	
	public Troupes chercheCible(Troupes troupe,Direction dir, boolean estBleu) {
		int x = troupe.getPosition().getX();
		int y = troupe.getPosition().getY();
		String type = troupe.getType();
		Troupes cible = null;
		if(estBleu) {
			switch (dir) {
				case NORD:
					cible = getTroupeRouge(new Coordonnees(x, y-1));
					if(cible==null && (type=="Archer" || type=="Mage")) {
						cible = getTroupeRouge(new Coordonnees(x,y-2));
						if(cible==null)
							cible = getTroupeRouge(new Coordonnees(x,y-3));
					}
					break;
				case SUD:
					cible = getTroupeRouge(new Coordonnees(x, y+1));
					if(cible==null && (type=="Archer" || type=="Mage")) {
						cible = getTroupeRouge(new Coordonnees(x,y+2));
						if(cible==null)
							cible = getTroupeRouge(new Coordonnees(x,y+3));
					}
					break;
				case OUEST:
					cible = getTroupeRouge(new Coordonnees(x-1, y));
					if(cible==null && (type=="Archer" || type=="Mage")) {
						cible = getTroupeRouge(new Coordonnees(x-2,y));
						if(cible==null)
							cible = getTroupeRouge(new Coordonnees(x-3,y));
					}
					break;
				case EST:
					cible = getTroupeRouge(new Coordonnees(x+1, y));
					if(cible==null && (type=="Archer" || type=="Mage")) {
						cible = getTroupeRouge(new Coordonnees(x+2,y));
						if(cible==null)
							cible = getTroupeRouge(new Coordonnees(x+3,y));
					}
					break;
			}
		}
		else {
			switch (dir) {
			case NORD:
				cible = getTroupeBleu(new Coordonnees(x, y-1));
				if(cible==null && (type=="Archer" || type=="Mage")) {
					cible = getTroupeBleu(new Coordonnees(x,y-2));
					if(cible==null)
						cible = getTroupeBleu(new Coordonnees(x,y-3));
				}
				break;
			case SUD:
				cible = getTroupeBleu(new Coordonnees(x, y+1));
				if(cible==null && (type=="Archer" || type=="Mage")) {
					cible = getTroupeBleu(new Coordonnees(x,y+2));
					if(cible==null)
						cible = getTroupeBleu(new Coordonnees(x,y+3));
				}
				break;
			case OUEST:
				cible = getTroupeBleu(new Coordonnees(x-1, y));
				if(cible==null && (type=="Archer" || type=="Mage")) {
					cible = getTroupeBleu(new Coordonnees(x-2,y));
					if(cible==null)
						cible = getTroupeBleu(new Coordonnees(x-3,y));
				}
				break;
			case EST:
				cible = getTroupeBleu(new Coordonnees(x+1, y));
				if(cible==null && (type=="Archer" || type=="Mage")) {
					cible = getTroupeBleu(new Coordonnees(x+2,y));
					if(cible==null)
						cible = getTroupeBleu(new Coordonnees(x+3,y));
				}
				break;
			}
		}
		return cible;
	}
	
	public void metActionStop() {
		for(Troupes unite : this.unite_bleue) {
			unite.setAction(TroupesAction.STOP);
		}
		for(Troupes unite : this.unite_rouge) {
			unite.setAction(TroupesAction.STOP);
		}
	}
	
	public ArrayList<Troupes> getUnite_rouge() {
		return unite_rouge;
	}

	public void setUnite_rouge(ArrayList<Troupes> unite_rouge) {
		this.unite_rouge = unite_rouge;
	}

	public ArrayList<Troupes> getUnite_bleue() {
		return unite_bleue;
	}

	public void setUnite_bleue(ArrayList<Troupes> unite_bleue) {
		this.unite_bleue = unite_bleue;
	}

	public ArrayList<Coordonnees> getArbres() {
		return arbres;
	}
	
	public boolean ArbreEn(Coordonnees coor) {
		int x = coor.getX();
		int y = coor.getY();
		for(int i=0; i<arbres.size(); ++i) {
			int ax = arbres.get(i).getX();
			int ay = arbres.get(i).getY();
			if(x==ax && y==ay)
				return true;
		}
		return false;
	}
	
	public boolean rocherEn(Coordonnees coor) {
		int x = coor.getX();
		int y = coor.getY();
		for(int i=0; i<rochers.size(); ++i) {
			int ax = rochers.get(i).getX();
			int ay = rochers.get(i).getY();
			if(x==ax && y==ay)
				return true;
		}
		return false;
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
	
	public Troupes getTroupeBleu(Coordonnees coor) {
		boolean trouver=false;
		int i=0;
		int tx = coor.getX();
		int ty = coor.getY();
		while(trouver==false && i<unite_bleue.size()) {
			int x = unite_bleue.get(i).getPosition().getX();
			int y = unite_bleue.get(i).getPosition().getY();
			if(tx==x && ty==y) {
				trouver = true;
				return unite_bleue.get(i);
			}
			else {
				++i;
			}
		}
		return null;
	}
	
	public Troupes getTroupeRouge(Coordonnees coor) {
		boolean trouver=false;
		int i=0;
		int tx = coor.getX();
		int ty = coor.getY();
		while(trouver==false && i<unite_rouge.size()) {
			int x = unite_rouge.get(i).getPosition().getX();
			int y = unite_rouge.get(i).getPosition().getY();
			if(tx==x && ty==y) {
				trouver = true;
				return unite_rouge.get(i);
			}
			else {
				++i;
			}
		}
		return null;
	}
	
	public String getFile() {
		return this.file;
	}
	
}
