package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.ability.action.PlaceInDamageFromLibrary;
import model.ability.auto.AutoAbility;
import model.board.Board;
import model.card.Card;
import model.card.Character;
import model.gameEvent.GameEvent;
import model.player.Player;

public abstract class PlayerController {

	private Player player;
	private Board board;
	private ReadUserInput reader;
	private List<GameEvent> events;
	private boolean isAlive = true;
	private GameManager gm;
	private int refreshPoint = 0;

	public PlayerController(String name, ReadUserInput reader) {
		player = new Player(name);
		this.reader = reader;
		events = new ArrayList<>();
	}

	public void setGM(GameManager gm) {
		this.gm = gm;
		player.setGM(gm);
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

	public void addEvent(GameEvent e) {
		checkTiming(e);
		gm.checkTiming(e);
		events.add(e);
	}

	public void checkTiming(GameEvent e) {

		// refresh point
		if (refreshPoint > 0) {
			new PlaceInDamageFromLibrary().execute(this, null);
			refreshPoint--;
			return;
		}

		// TODO modifier update

		// trigger (auto) abilities

		List<AutoAbility> choices = new ArrayList<>();
		List<Character> chars = new ArrayList<>();
		// check stage
		chars.addAll(board.getStage().getCharacters());

		/*
		 * // check hand for (Character character :
		 * board.getHand().getCardsOfType(Character.class)) {
		 * choices.addAll(character.getAutoAbilities()); }
		 * 
		 * // check memory for (Character character :
		 * board.getMemoryZone().getCardsOfType(Character.class)) {
		 * choices.addAll(character.getAutoAbilities()); }
		 * 
		 * // check level for (Character character :
		 * board.getLevel().getCardsOfType(Character.class)) {
		 * choices.addAll(character.getAutoAbilities()); }
		 * 
		 * // check waiting room for (Character character :
		 * board.getWaitingRoom().getCardsOfType(Character.class)) {
		 * choices.addAll(character.getAutoAbilities()); }
		 */

		for (Character character : chars) {
			for (AutoAbility autoAbility : character.getAutoAbilities()) {
				if ((autoAbility.isSelf() && e.getSourcePlayer().equals(player))
						|| (!autoAbility.isSelf() && !e.getSourcePlayer().equals(player))) {
					choices.add(autoAbility);
				}
			}
		}

		if (!choices.isEmpty()) {
			AutoAbility choice = getChoice("Pick auto ability to activate", choices);
			gm.execute(choice, player);
		}

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

	// public abstract void handleException(NotEnoughStockException e);

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

	private void gameOver() {
		isAlive = false;
		gm.gameOver(this);
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void refresh() {
		refreshPoint++;
	}
}
