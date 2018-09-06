package controller;

import java.util.List;

import model.board.Board;
import model.card.Card;
import model.player.Player;

public abstract class PlayerController {

	private Player player;
	private Board board;
	private ReadUserInput reader;

	public PlayerController(String name, ReadUserInput reader) {
		player = new Player(name);
		this.reader = reader;
	}

	public final void setDeck(List<Card> deck) {
		board = new Board(deck);
	}

	public abstract void buildDeck();

	public abstract void readDeck();

	public final <T> T getChoice(String prompt, List<T> choices) {
		return reader.getChoice(prompt, choices);
	}

	public final boolean getChoice(String prompt) {
		return reader.getChoice(prompt);
	}

	public abstract void log(Object text);

	public Player getPlayer() {
		return player;
	}

	public abstract void displayStage();

	public abstract void displayHand();

	public abstract void displayWaitingRoom();

	public abstract void displayDamageZone();

	public abstract void displayLevel();

	public abstract void displayStock();

	public Board getBoard() {
		return board;
	}
	
	//public abstract void handleException(NotEnoughStockException e);

	public final void setReader(ReadUserInput reader) {
		this.reader = reader;
	}
}
