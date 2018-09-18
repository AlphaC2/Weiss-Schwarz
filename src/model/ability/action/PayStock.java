package model.ability.action;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.ability.condition.Condition;
import model.ability.condition.HasStock;
import model.board.Board;
import model.board.Stock;
import model.card.Card;

public class PayStock extends Action<Stock> {
	private int amount;
	
	public PayStock(int amount) {
		super("Pay Stock");
		this.amount = amount;
		Condition<Stock> c = new HasStock(amount);
		addCondition(c);
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		Board board = p1.getBoard();
		List<Card> cards = new ArrayList<>();
		for (int i = 0; i < amount; i++) {
			cards.add( board.getStock().pay() ); 
		}
		board.getWaitingRoom().add(cards);
		p1.log("Paid " + amount + " cost");
	}

	@Override
	public String failureMessage() {
		return "Could not pay stock";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.add(p1.getBoard().getStock());
	}

}
