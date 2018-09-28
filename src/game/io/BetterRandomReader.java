package game.io;

import java.util.List;
import game.model.card.Character;

public class BetterRandomReader extends RandomReader{

	@Override
	public <T> T getChoice(String prompt, List<T> choices) {
		if (prompt.contains("Pick card to clock")){
			for (int i = 0; i < choices.size(); i++) {
				if (! (choices.get(i) instanceof Character)){
					return choices.get(i);
				} 
			}
		}
		return super.getChoice(prompt, choices);
	}

	@Override
	public boolean getChoice(String prompt) {
		if (prompt.toLowerCase().contains("declare an attack?")){
			return true;
		} else if (prompt.toLowerCase().contains("clock?")){
			return true;
		}
		return super.getChoice(prompt);
	}

}
