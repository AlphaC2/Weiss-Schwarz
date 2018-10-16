package game.io;

import java.util.List;

import game.model.board.SlotType;
import game.model.board.Stage;
import game.model.card.Character;
import game.model.card.Climax;

public class BetterRandomReader extends RandomReader {

	@Override
	public <T> T getChoice(String prompt, List<T> choices) {
		if (prompt.contains("deck")) {
			for (T t : choices) {
				if (t.equals("AggroLvl0"))
					return t;
			}
		}
		if (prompt.contains("Pick card to clock")) {
			for (int i = 0; i < choices.size(); i++) {
				if (!(choices.get(i) instanceof Climax)) {
					return choices.get(i);
				}
			}
		}
		if (prompt.contains("Enter command")) {
			Stage stage = pc.getBoard().getStage();
			if (pc.getBoard().getHand().getCardsOfType(Character.class).size() > 0) {
				if (stage.getSlot(SlotType.FRONT_LEFT).getCharacter() == null)
					return choices.get(6);
				if (stage.getSlot(SlotType.FRONT_CENTER).getCharacter() == null)
					return choices.get(6);
				if (stage.getSlot(SlotType.FRONT_RIGHT).getCharacter() == null)
					return choices.get(6);
			}
		}
		if (prompt.contains("Front or Side attack?")) {
			// TODO logic
		}
		if (prompt.contains("Choose Slot")) {
			Stage stage = pc.getBoard().getStage();
			if (stage.getSlot(SlotType.FRONT_LEFT).getCharacter() == null)
				return choices.get(0);
			if (stage.getSlot(SlotType.FRONT_CENTER).getCharacter() == null)
				return choices.get(1);
			if (stage.getSlot(SlotType.FRONT_RIGHT).getCharacter() == null)
				return choices.get(2);

		}
		return super.getChoice(prompt, choices);
	}

	@Override
	public boolean getChoice(String prompt) {
		if (prompt.toLowerCase().contains("declare an attack?")) {
			return true;
		} else if (prompt.toLowerCase().contains("Play a Climax")) {
			Stage stage = pc.getBoard().getStage();
			if (stage.getSlot(SlotType.FRONT_LEFT).getCharacter() != null
					&& stage.getSlot(SlotType.FRONT_CENTER).getCharacter() != null
					&& stage.getSlot(SlotType.FRONT_RIGHT).getCharacter() != null) {
				return true;
			}else{
				return false;
			}
		} else if (prompt.toLowerCase().contains("clock?")) {
			return true;
		}
		return super.getChoice(prompt);
	}

}
