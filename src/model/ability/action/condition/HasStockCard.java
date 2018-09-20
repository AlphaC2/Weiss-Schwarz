package model.ability.action.condition;

import model.board.Stock;
import model.card.Card;

public class HasStockCard extends Condition<Card>{

	private Stock stock;
	
	public HasStockCard() {
		super("Has Stock Card");
	}

	@Override
	public boolean check() {
		return stock.size() >= target.getCost();
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
}
