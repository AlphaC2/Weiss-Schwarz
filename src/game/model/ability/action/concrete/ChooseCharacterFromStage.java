package game.model.ability.action.concrete;

import game.controller.PlayerController;
import game.model.ability.action.TargetedAction;
import game.model.ability.action.condition.CharacterOnStage;
import game.model.board.Slot;

public class ChooseCharacterFromStage extends TargetedAction<Slot> {

	protected ChooseCharacterFromStage() {
		super("Choose Character");
		addCondition(new CharacterOnStage());
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets = p1.getBoard().getStage().getSlots();
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		Slot chosen = p1.getChoice("Choose a character", targets);
		p1.getBoard().getResolutionZone().add(chosen.getCharacter());
	}

	@Override
	public String failureMessage() {
		return "Failed to choose character";
	}

}
