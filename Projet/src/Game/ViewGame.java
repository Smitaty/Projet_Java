package Game;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JFrame;

import IG.Plateau;



/**
 * Cette classe permet l'affichage de l'interface graphique
 * @author Simon et Rémi
 */


public class ViewGame{
	
	public ViewGame(Plateau plateau) {
		
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
