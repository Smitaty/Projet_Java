package IG;

import java.io.*;

public class Plateau{
	private String filename;
	private char objets [][];
	private int largeur;
	private int hauteur;
	
	public Plateau(String file) {
		filename=file;
		
		try {
			InputStream flux =new FileInputStream(filename); 
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
			objets = new char[nbX][nbY];
			
			flux = new FileInputStream(filename); 
			lecture = new InputStreamReader(flux);
			tampon = new BufferedReader(lecture);
			
			int h=0;
			while ((ligne = tampon.readLine())!=null) {
				for(int i=0; i<ligne.length(); ++i) {
					objets[i][h]=ligne.charAt(i);
				}
				++h;
			}
			tampon.close();

		}catch (Exception e){
			System.out.println("Erreur : "+e.getMessage());
		}		
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public char[][] getObjets() {
		return objets;
	}

	public void setObjets(char[][] objets) {
		this.objets = objets;
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
	
}
