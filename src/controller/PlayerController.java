package controller;

import java.util.ArrayList;
import java.util.List;

import model.ability.AbilityInterface;
import model.board.Board;
import model.card.Card;
import model.player.Player;

public abstract class PlayerController {

	private Player player;
	private Board board;
	private ReadUserInput reader;
	private List<AbilityInterface> unresolvedActions;
	private boolean isAlive = true;

	public PlayerController(String name, ReadUserInput reader) {
		player = new Player(name);
		this.reader = reader;
		unresolvedActions = new ArrayList<>();
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
	
	public void checkTiming(PlayerController p2){
		for (AbilityInterface action : unresolvedActions) {
			action.execute(this, p2);
		}
		unresolvedActions.clear();
	}
	
	
	public void addToUnresolved(AbilityInterface action){
		unresolvedActions.add(action);
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

	public void deckOut() {
		log("deck out");
		gameOver();
	}

	public void level4() {
		log("level 4");
		gameOver();
	}
	
	private void gameOver(){
		isAlive = false;
		unresolvedActions.clear();
		GameManager.getInstance().gameOver(this);
	}

	public boolean isAlive() {
		return isAlive;
	}
}
