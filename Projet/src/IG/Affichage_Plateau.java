package IG;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Affichage_Plateau extends JPanel{
	private static final long serialVersionUID = 1L;
	
	String path="src/Image/";
	private Plateau plateau;
	private char objets[][];
	private int largeur;
	private int hauteur;
	
	public Affichage_Plateau(Plateau plateau) {
		this.plateau = plateau;
		this.objets = plateau.getObjets();
		this.largeur = plateau.getLargeur();
		this.hauteur = plateau.getHauteur();
	}
	
	public void paint(Graphics g) {
		int fen_x = getSize().width;
		int fen_y = getSize().height;
		
		g.fillRect(0,0,fen_x,fen_y);
		//g.setColor(Color.GREEN);
		
		double stepx=fen_x/(double)largeur;
		double stepy=fen_y/(double)hauteur;
	
		double position_x=0;
		
		for(int x=0; x<largeur; ++x){
			double position_y=0;
			String str="";
			for(int y=0; y<hauteur; ++y) {
				try {
					str+="herbe ";
					Image img = ImageIO.read(new File(path+"herbe.png"));
					g.drawImage(img, (int)position_x, (int)position_y, (int)stepx, (int)stepy, this);
				}catch (IOException e) {
					e.printStackTrace();
				}
				if(objets[x][y]=='T') {
					try {
						Image img = ImageIO.read(new File(path+"arbre.png"));
						g.drawImage(img, (int)position_x, (int)position_y, (int)stepx, (int)stepy, this);
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(objets[x][y]=='R') {
					try {
						Image img = ImageIO.read(new File(path+"rocher.png"));
						g.drawImage(img, (int)position_x, (int)position_y, (int)stepx, (int)stepy, this);
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(objets[x][y]=='A') {
					try {
						Image img = ImageIO.read(new File(path+"archer1.png"));
						g.drawImage(img, (int)position_x, (int)position_y, (int)stepx, (int)stepy, this);
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(objets[x][y]=='C') {
					try {
						Image img = ImageIO.read(new File(path+"chevalier_avant.png"));
						g.drawImage(img, (int)position_x, (int)position_y, (int)stepx, (int)stepy, this);
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(objets[x][y]=='F') {
					try {
						Image img = ImageIO.read(new File(path+"chateau.png"));
						g.drawImage(img, (int)position_x, (int)position_y, (int)stepx, (int)stepy, this);
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
				position_y=position_y+stepy;
			}
			position_x=position_x+stepx;
		}
	}
	
	public void update() {
		this.repaint();
	}
}
