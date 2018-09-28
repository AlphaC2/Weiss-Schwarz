package game.model.ability.action.concrete;

import java.util.List;

import game.controller.PlayerController;
import game.model.ability.action.TargetedAction;
import game.model.board.Slot;
import game.model.card.Card;
import game.model.card.Trigger;

public class TriggerAction extends TargetedAction<Card>{
	
	private Slot attacking;

	public TriggerAction(Slot attacking) {
		super("Trigger");
		this.attacking = attacking;
	}

	@Override
	public String failureMessage() {
		return "Error";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.add(p1.getBoard().getLibrary().peek());
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		p1.log(p1.getPlayer().getPhase());
		new DrawToResolution().execute(p1, p2);
		Card trigger = p1.getBoard().getResolutionZone().getCards().get(0);
		p1.log("Triggerd:" + trigger);
		List<Trigger> triggers = trigger.getTrigger();
		for (Trigger t : triggers) {
			//TODO
			
		}
		new ResolutionToStock().execute(p1, p2);
		p1.getPlayer().nextStep();
	}

}
