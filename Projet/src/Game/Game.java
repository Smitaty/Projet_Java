package Game;

public abstract class Game {
	static private long tpsParTour=90;
	private int tour;
	
	public Game() {
		tour=0;
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
