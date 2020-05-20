package Strategie;

import IG.Plateau;
import Perceptron.SparseVector;
import Troupes.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * Cette stratégie retourne une action aléatoire parmi les actions possibles pour la troupe
 * @author Simon
 */


public class StrategieRandom extends Strategie{
	public StrategieRandom(Plateau plateau) {
		super(plateau);
	}
	
	/**
	 * Méthode qui retourne une action pour la troupe donnée
	 * @param troupe
	 * @return TroupesAction
	 */

	public TroupesAction coup(Troupes troupe) {
		ArrayList<TroupesAction> coups = coupsPossibles(troupe);
		if(coups.size()>0) {
			for(int i=0; i<coups.size(); ++i) {
				if(coups.get(i)==TroupesAction.ATTACK1)
					return TroupesAction.ATTACK1;
			}
			int nbcoups = coups.size();
			Random r = new Random();
			int coup = r.nextInt(nbcoups);
			return coups.get(coup);
		}
		else {
			return TroupesAction.STOP;
		}
	}
	
	public boolean estPerceptron() {return false;}
	
	public SparseVector encodageEtat(Plateau plateau, Troupes troupe) {return null;}
	
}
