package model.ability.condition;

import model.board.Board;
import model.board.DamageZone;
import model.board.LevelZone;
import model.card.Card;

public class HasColor extends Condition<Card>{

	private LevelZone level;
	private DamageZone damage;
	
	public HasColor() {
		super("Has Color");
	}
	
	public void init(Board board){
		this.level = board.getLevel();
		this.damage = board.getDamageZone();
	}

	@Override
	public boolean check() {
		if (target.getLevel() != 0){
			for (Card card : level.getCards()) {
				if (card.getColour() == target.getColour()){
					return true;
				}
			}
			for (Card card : damage.getCards()) {
				if (card.getColour() == target.getColour()){
					return true;
				}
			}
		}else {
			return true;
		}
		return false;
	}
	

}
