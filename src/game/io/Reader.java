package game.io;

import java.util.List;

import game.controller.PlayerController;
import game.model.card.Card;

public abstract class Reader {
	protected PlayerController pc;
	
	public final void setPC(PlayerController pc){
		this.pc = pc;
	}
	
	public abstract <T> T getChoice(String prompt, List<T> choices);
	public abstract boolean getChoice(String prompt);
	public abstract void buildDeck();
	public abstract List<Card> readDeck();
}
