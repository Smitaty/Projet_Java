package Game;

public abstract class Game {
	static private long tpsParTour=60;
	private int tour=0;
	
	public Game() {
		this.init();
	}
	
	public void init() {
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
