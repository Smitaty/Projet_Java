package Strategie;

import IG.Plateau;
import Game.TroupesAction;
import Troupes.*;
import java.util.ArrayList;
import java.util.Random;

public class StrategieRandom extends Strategie{
	public StrategieRandom(Plateau plateau) {
		super(plateau);
	}

	public TroupesAction coup(Troupes troupe) {
		ArrayList<TroupesAction> coups = coupsPossibles(troupe);
		for(int i=0; i<coups.size(); ++i) {
			if(coups.get(i)==TroupesAction.ATTACK1)
				return TroupesAction.ATTACK1;
		}
		int nbcoups = coups.size();
		Random r = new Random();
		int coup = r.nextInt(nbcoups);
		return coups.get(coup);
	}
}
