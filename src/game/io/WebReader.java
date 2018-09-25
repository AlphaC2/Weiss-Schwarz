package game.io;

import java.util.List;

import game.controller.GameStatus;

public class WebReader extends Reader {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getChoice(String prompt, List<T> choices) {
		pc.getGM().getGameState().pause(pc, prompt, choices);
		while (pc.getGM().getGameState().getStatus() == GameStatus.INTERUPTED) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return (T) pc.getGM().getGameState().getChoice();
	}

	@Override
	public void buildDeck() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getChoice(String prompt) {
		pc.getGM().getGameState().pause(pc, prompt);
		while (pc.getGM().getGameState().getStatus() == GameStatus.INTERUPTED) {

		}
		return (Boolean) pc.getGM().getGameState().getChoice();
	}

}
