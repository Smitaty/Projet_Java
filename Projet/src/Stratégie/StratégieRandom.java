package Stratégie;

import IG.Plateau;
import Game.TroupesAction;
import Troupes.*;
import java.util.ArrayList;
import java.util.Random;

public class StratégieRandom extends Stratégie{
	public StratégieRandom(Troupes troupes, Plateau plateau) {
		super(troupes,plateau);
	}

	public TroupesAction coupRandom() {
		ArrayList<TroupesAction> coups = coupsPossibles();
		int nbcoups = coups.size();
		Random r = new Random();
		int coup = r.nextInt(nbcoups);
		return coups.get(coup);
	}
}
