package Execution;
import IG.*;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import javax.swing.JFrame;
import java.util.ArrayList;
import Troupes.Archer;
import Troupes.Coordonnées;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Plateau plateau = new Plateau("src/Layout/Test.lay");
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
	}

}
