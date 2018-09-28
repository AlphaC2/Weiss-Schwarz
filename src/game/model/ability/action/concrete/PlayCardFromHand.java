package game.model.ability.action.concrete;


import java.util.List;

import game.controller.PlayerController;
import game.model.ability.action.TargetedAction;
import game.model.ability.action.condition.HasColor;
import game.model.ability.action.condition.HasLevel;
import game.model.ability.action.condition.HasStockCard;
import game.model.ability.action.condition.MatchesCardType;
import game.model.board.Board;
import game.model.board.Slot;
import game.model.card.Card;
import game.model.card.CardType;
import game.model.card.Character;
import game.model.card.Event;

public class PlayCardFromHand extends TargetedAction<Card>{
	
	private HasLevel haslevel;
	private HasStockCard hasStock;
	private HasColor hasColor;
	private MatchesCardType type;
	
	public PlayCardFromHand() {
		super("Play Card");
		haslevel = new HasLevel();
		hasStock = new HasStockCard();
		hasColor = new HasColor();
		type = new MatchesCardType(CardType.CLIMAX, false);
		addCondition(haslevel);
		addCondition(hasStock);
		addCondition(hasColor);
		addCondition(type);
	}

	@Override
	public String failureMessage() {
		return "No cards in hand to play";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		haslevel.setLevel(p1.getBoard().getLevel());
		hasStock.setStock(p1.getBoard().getStock());
		hasColor.init(p1.getBoard());
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
