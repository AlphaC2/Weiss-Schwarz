package game.model.ability.action.concrete;

import game.controller.PlayerController;
import game.model.ability.action.DelayedTargetAction;
import game.model.ability.action.condition.MatchesCardType;
import game.model.ability.action.condition.Self;
import game.model.board.Slot;
import game.model.card.Card;
import game.model.card.CardType;

public class PlayCharacterOnStage extends DelayedTargetAction<Card>{
	private Slot s;
	private Card c;
	
	public PlayCharacterOnStage(String name, Card c) {
		super(name);
		addCondition(new MatchesCardType(CardType.CHARACTER));
		addCondition(new Self(c));
		this.c = c;
	}

	public void setSlot(Slot s) {
		this.s = s;
	}

	@Override
	public String failureMessage() {
		return "CRITICAL ERROR";
	}

	@Override
	public boolean canActivate() {
		return s != null;
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		// TODO Auto-generated method stub
		
	}

}
