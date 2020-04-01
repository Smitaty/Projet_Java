package Troupes;

public abstract class Troupes {
	
	private int PV;
	private int Degats;
	private int ID;
	private int pos_x;
	private int pos_y;
	private int Portee_Attaque;
	
	public Troupes(int pv, int degats, int iD, int portee) {
		PV = pv;
		Degats = degats;
		ID = iD;
		Portee_Attaque=portee;
	}
	
	// Getters
	int getPV() {return PV;}
	int getDegats() {return Degats;}
	int getId() {return ID;}
	int getX() {return pos_x;}
	int getY() {return pos_y;}
	int getPortee() {return Portee_Attaque;}
	
	// Setters
	void setPV(int pv) {PV=pv;}
	void setDegats(int degats) {Degats=degats;}
	void setId(int id) {ID=id;}
	void setX(int x) {pos_x=x;}
	void setY(int y) {pos_y=y;}
	void setPortee(int portee) {Portee_Attaque=portee;}
}
