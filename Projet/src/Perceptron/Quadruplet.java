package Perceptron;

import Troupes.TroupesAction;

public class Quadruplet {

	private SparseVector etat, atteint;
	private TroupesAction action;
	private double reward;
	
	public Quadruplet(SparseVector etat,TroupesAction action,SparseVector atteint,double reward){
		this.etat=etat;
		this.action=action;
		this.atteint=atteint;
		this.reward=reward;
	}

	public SparseVector getEtat() {
		return etat;
	}

	public SparseVector getAtteint() {
		return atteint;
	}

	public TroupesAction getAction() {
		return action;
	}

	public double getReward() {
		return reward;
	}
	
	
	
}
