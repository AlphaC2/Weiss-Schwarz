package model.ability.condition;

import model.board.Board;

public class HasStock extends Condition {
	private int amount;
	
	public HasStock(int amount) {
		super("Has Stock");
		this.amount = amount;
	}

	@Override
	public boolean check(Board p1, Board p2) {
		return p1.getStock().size() >= amount;
	}

}
