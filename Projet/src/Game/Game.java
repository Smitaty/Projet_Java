package Game;

import java.util.ArrayList;
import java.lang.Thread;

public abstract class Game implements Runnable{
	static private long tpsParTour=90;
	private int tour;
	
	public Game() {
		tour=0;
	}
	
	public abstract void partie();
	
	public void run() {
		partie();
	}
		
	public static long getTpsParTour() {
		return tpsParTour;
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}	
	
}
