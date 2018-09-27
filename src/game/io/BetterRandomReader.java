package game.io;

import java.util.List;

public class BetterRandomReader extends RandomReader{

	@Override
	public <T> T getChoice(String prompt, List<T> choices) {
		if (prompt.toLowerCase().contains("declare an attack?")){
			//TODO
		}
		return super.getChoice(prompt, choices);
	}

	@Override
	public boolean getChoice(String prompt) {
		if (prompt.toLowerCase().contains("declare an attack?")){
			return true;
		}
		return super.getChoice(prompt);
	}

}
