package Execution;
import IG.*;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Plateau plateau = new Plateau("src/Layout/Test.lay");
		Affichage_Plateau affichage = new Affichage_Plateau(plateau);
		JFrame frame = new JFrame();
		frame.setTitle("Plateau");
		
		frame.setSize(new Dimension(700, 700));
		Dimension windowSize = frame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 ;
		int dy = centerPoint.y - windowSize.height / 2 - 350;
		frame.setLocation(dx, dy);

		frame.add(affichage);
		
		frame.setVisible(true);
	}

}
