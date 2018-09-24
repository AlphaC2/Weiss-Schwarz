package game.model.ability.action.condition;

import game.model.board.Stock;
import game.model.card.Card;

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
