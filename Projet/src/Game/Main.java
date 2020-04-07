package Game;
import IG.*;
import Game.JeuRandom;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import javax.swing.JFrame;
import java.lang.Thread;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Plateau plateau = new Plateau("src/Layout/Plateau.lay");
		
		JFrame frame = new JFrame();
		frame.setTitle("Plateau");
		frame.setSize(new Dimension(700, 700));
		Dimension windowSize = frame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 ;
		int dy = centerPoint.y - windowSize.height / 2 - 350;
		frame.setLocation(dx, dy);
		frame.add(plateau);
		frame.setVisible(true);
		try {
			Thread.sleep(30000);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JeuRandom jeu = new JeuRandom(plateau);
		Thread thread = new Thread(jeu);
		thread.start();
		//plateau.repaint();
		//frame.setVisible(false);
		//frame.dispose();
	}

}
