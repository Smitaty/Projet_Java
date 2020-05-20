package Troupes;

/**
 * Classe représentant des coordonnées avec une valeur en x et une en y
 * @author Simon et Rémi
 */

public class Coordonnees {
	private int x;
	private int y;

	public Coordonnees(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
