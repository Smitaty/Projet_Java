package Troupes;

public abstract class Troupes {
	
	private int PV;
	private int Degats;
	private Coordonnées position;
	private String type;
	
	public Troupes(int pv, int degats, Coordonnées pos, String t) {
		PV = pv;
		Degats = degats;
		position=pos;
		type=t;
	}
	

	// Getters
	public int getPV() {return PV;}
	public int getDegats() {return Degats;}
	public Coordonnées getPosition() {return position;}
	public String getType() {
		return type;
	}
	// Setters
	public void setPV(int pv) {PV=pv;}
	public void setDegats(int degats) {Degats=degats;}
	public void setPosition(Coordonnées pos) {position=pos;}
	public void setType(String type) {
		this.type = type;
	}
}
