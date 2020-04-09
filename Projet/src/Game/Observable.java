package Game;

public interface Observable {
	void addObserver(Observer observer);
	void rmObserver(Observer observer);
	void notifyObserver();
}
