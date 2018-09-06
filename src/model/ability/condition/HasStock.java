package model.ability.condition;

import model.board.Stock;

public class HasStock extends Condition<Stock> {
	private int amount;
	
	public HasStock(int amount) {
		super("Has Stock");
		this.amount = amount;
	}

	@Override
	public boolean check() {
		return target.size() >= amount;
	}

}
