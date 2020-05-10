package Troupes;

import Strategie.*;

public abstract class Troupes {
	
	private int PV;
	private int Degats;
	private Coordonnees position;
	private String type;
	private Direction direction;
	private TroupesAction action;
	private double reward;

	public Troupes(int pv, int degats, Coordonnees pos, String t,Direction dir) {
		PV = pv;
		Degats = degats;
		position=pos;
		type=t;
		direction=dir;
		action = TroupesAction.STOP;
		this.reward=0;
	}
	
		

	@Override
	public String toString() {
		return "Troupes [PV=" + PV + ", position=(" + position.getX() + "," + position.getY() + "), type=" + type + ", direction=" + direction + "]";
	}

	// Getters
	public int getPV() {return PV;}
	public int getDegats() {return Degats;}
	public Coordonnees getPosition() {return position;}
	public String getType() {return type;}
	public Direction getDirection() {return direction;}
	public TroupesAction getAction() {return action;}
	public double getReward() {return reward;}
	
	// Setters
	public void setPV(int pv) {PV=pv;}
	public void setDegats(int degats) {Degats=degats;}
	public void setPosition(Coordonnees pos) {position=pos;}
	public void setType(String type) {this.type = type;}
	public void setDirection(Direction dir) {direction=dir;}
	public void setAction(TroupesAction action) {this.action = action;}
	public void setReward(double r) {this.reward=r;}
}
