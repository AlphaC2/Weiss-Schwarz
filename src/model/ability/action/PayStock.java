package model.ability.action;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.ability.condition.Condition;
import model.ability.condition.HasStock;
import model.board.Board;
import model.card.Card;

public class PayStock extends Action {
	private int amount;
	
	public PayStock(int amount) {
		super("Pay Stock");
		this.amount = amount;
		Condition c = new HasStock(amount);
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
	}

}
