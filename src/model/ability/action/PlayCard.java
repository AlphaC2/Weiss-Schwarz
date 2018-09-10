package model.ability.action;


import java.util.List;

import controller.PlayerController;
import model.ability.condition.HasLevel;
import model.ability.condition.HasStockCard;
import model.board.Board;
import model.board.Slot;
import model.card.Card;
import model.card.Character;
import model.card.Event;

public class PlayCard extends Action<Card>{
	
	private HasLevel haslevel;
	private HasStockCard hasStock;
	
	PlayCard() {
		super("Play Card");
		haslevel = new HasLevel();
		hasStock = new HasStockCard();
		addCondition(haslevel);
		addCondition(hasStock);
	}

	@Override
	public String failureMessage() {
		return "No cards in hand to play";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		haslevel.setLevel(p1.getBoard().getLevel());
		hasStock.setStock(p1.getBoard().getStock());
		List<Card> hand = p1.getBoard().getHand().getCards();
		targets.addAll(hand);
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		Card c = p1.getChoice("Choose a card to play", targets);
		Board board = p1.getBoard();
		if (c instanceof Character){
			
			Character charCard = (Character) c;
			Slot slot = p1.getChoice("Choose Slot", board.getStage().getSlots());
			
			if (slot.getCharacter() != null){
				board.getWaitingRoom().add( board.getStage().removeCharacter(slot.getSlotType()) );
			}
			board.getHand().remove(charCard);
			slot.setCharacter(charCard);

		} else if (c instanceof Event){
			//TODO
		}
		new PayStock(c.getCost()).execute(p1, p2);
	}

}
