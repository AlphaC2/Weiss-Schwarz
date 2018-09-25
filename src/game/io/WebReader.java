package game.io;

import java.util.ArrayList;
import java.util.List;

import game.controller.GameStatus;

public class WebReader extends Reader {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getChoice(String prompt, List<T> choices) {
		System.out.println("BEFORE List CHOICE");
		pc.getGM().getGameState().pause(pc, prompt, choices);
		if (choices.size() == 1){
			return choices.get(0);
		}
		while (pc.getGM().getGameState().getStatus() == GameStatus.INTERUPTED) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("WebReader:");
		System.out.println(pc.getGM().getGameState().getChoice());
		
		return (T) pc.getGM().getGameState().getChoice();
	}

	@Override
	public void buildDeck() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getChoice(String prompt) {
		/*System.out.println("BEFORE BOOLEAN CHOICE");
		pc.getGM().getGameState().pause(pc, prompt);
		while (pc.getGM().getGameState().getStatus() == GameStatus.INTERUPTED) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("AFTER BOOLEAN CHOICE:");
		System.out.println(pc.getGM().getGameState().getChoice());
		return (Boolean) pc.getGM().getGameState().getChoice();*/
		List<Boolean> list = new ArrayList<>();
		list.add(new Boolean(true));
		list.add(new Boolean(false));
		return getChoice(prompt,list);
		
	}

}
