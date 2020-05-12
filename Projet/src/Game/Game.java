package Game;

import java.util.ArrayList;
import java.lang.Thread;

public abstract class Game implements Runnable{
	private int tour;
	
	public Game() {
		tour=0;
	}
	
	public abstract void partie();
	
	public void run() {
		partie();
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}	
	
}
