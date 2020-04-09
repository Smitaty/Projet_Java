package Game;

import java.util.ArrayList;
import java.lang.Thread;

public abstract class Game implements Runnable,Observable{
	static private long tpsParTour=90;
	private int tour;
	private ArrayList<Observer> obs = new ArrayList<Observer>();
	private Thread thread;
	
	public Game() {
		tour=0;
		launch();
	}
	
	public abstract void partie();
	
	public void launch() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		partie();
	}
	
	public void addObserver(Observer observer) {
		obs.add(observer);
	}

	public void rmObserver(Observer observer) {
		obs.remove(observer);
	}
	
	public abstract void notifyObserver();
	
	public static long getTpsParTour() {
		return tpsParTour;
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}
	
	public ArrayList<Observer> getObs() { return obs;}
	
	
}
