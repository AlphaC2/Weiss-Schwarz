package controller;

import java.util.List;

import model.card.Card;
import model.player.Player;

public abstract class PlayerController {
	
	private Player player;
	private ReadUserInput reader;
	
	public PlayerController(String name, ReadUserInput reader) {
		player = new Player(name, this);
		this.reader = reader;
		readDeck();
	}

	public final void setDeck(List<Card> deck){
		player.setDeck(deck);
	}
    
    public abstract void buildDeck();
    public abstract void readDeck();
    
    public final <T> T getChoice(String prompt, List<T> choices){
    	return reader.getChoice(prompt, choices);
    }
    
    public final boolean getChoice(String prompt){
    	return reader.getChoice(prompt);
    }
    
    public abstract void log(Object text);

	public Player getPlayer() {
		return player;
	}
}
