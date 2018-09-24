package game.model.ability.action.condition;

import game.model.board.LevelZone;
import game.model.card.Card;

public class HasLevel extends Condition<Card>{

	private LevelZone level;
	
	public HasLevel() {
		super("Has Level");
	}
	
	public void setLevel(LevelZone level){
		this.level = level;
	}

	@Override
	public boolean check() {
		return target.getLevel() <= level.size();
	}

}
