package game.model.ability.auto;

import game.model.ability.AbilityInterface;
import game.model.ability.action.PayStock;
import game.model.card.Card;
import game.model.gameEvent.EventType;
import game.model.gameEvent.GameEvent;
import game.model.gameEvent.StageToWaitingRoomEvent;

public class Encore extends AutoAbility{
	
	Encore(Card source, AbilityInterface cost) {
		super(source, EventType.STAGE_TO_WAITINGROOM, true, true);
		addCost(cost);
	}
	
	Encore(Card source) {
		this(source, new PayStock(3));
	}

	@Override
	protected boolean checkPrime(GameEvent e) {
		if (e.getType() == EventType.STAGE_TO_WAITINGROOM){
			StageToWaitingRoomEvent stageToWaitingRoomEvent = (StageToWaitingRoomEvent) e;
			if (stageToWaitingRoomEvent.getCharacter() == getSource()){
				return true;
			}
		}
		return false;
	}

}
