package game.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import game.io.ConsoleReader;
import game.io.Reader;
import game.io.Writer;
import game.model.ability.action.PlaceInDamageFromLibrary;
import game.model.ability.auto.AutoAbility;
import game.model.ability.auto.PhaseAutoAbility;
import game.model.ability.continuous.ContinuousAbility;
import game.model.board.Board;
import game.model.board.Slot;
import game.model.card.Card;
import game.model.card.Character;
import game.model.gameEvent.EventType;
import game.model.gameEvent.GameEvent;
import game.model.gameEvent.PhaseEvent;
import game.model.player.Player;

public class PlayerController {

	private Player player;
	private Board board;
	private Reader reader;
	private Writer writer;
	private List<GameEvent> events;
	private boolean isAlive = true;
	@JsonIgnore
	private GameManager gm;
	private int refreshPoint = 0;
	private List<AutoAbility> choices = new ArrayList<>();
	private boolean testing = false;

	public PlayerController(String name, Reader reader, Writer writer) {
		player = new Player(name);
		this.reader = reader;
		this.writer = writer;
		events = new ArrayList<>();
	}

	public void setGM(GameManager gm) {
		this.gm = gm;
		player.setGM(gm);
	}

	public final void setDeck(List<Card> deck) {
		board = new Board(deck);
	}

	public final <T> T getChoice(String prompt, List<T> choices) {
		return reader.getChoice(prompt, choices);
	}

	public final boolean getChoice(String prompt) {
		boolean choice = reader.getChoice(prompt);
		if (reader instanceof ConsoleReader) {
			gm.getGameState().resume(0);
		}
		return choice;
	}

	public Player getPlayer() {
		return player;
	}

	void checkTiming() {
		List<Slot> stage = board.getStage().getSlots();
		int stageChars = 0, chars = 0;
		for (Slot slot : stage) {
			if (slot.getCharacter() != null)
				stageChars++;
		}
		chars += stageChars;
		chars += board.getLevel().size();
		chars += board.getDamageZone().size();
		chars += board.getHand().size();
		chars += board.getStock().size();
		chars += board.getWaitingRoom().size();
		chars += board.getLibrary().size();
		chars += board.getMemoryZone().size();
		chars += board.getResolutionZone().size();
		if (board.climaxZone != null)
			chars++;

		if (testing && chars != 50) {
			log("Level " + board.getLevel().size());
			log("Damage " + board.getDamageZone().size());
			log("Hand " + board.getHand().size());
			log("Stage " + stageChars);
			log("Stock " + board.getStock().size());
			log("WaitingRoom " + board.getWaitingRoom().size());
			log("Library " + board.getLibrary().size());
			log("Memory " + board.getMemoryZone().size());
			log("Resolution " + board.getResolutionZone().size());
			System.exit(1);
		}

		while (activateAuto())
			;
	}

	public void addEvents(List<GameEvent> events, PlayerController opponent) {
		removeExpiredMods(events);
		checkContinuous(opponent);
		opponent.checkContinuous(this);
		prime(events);
		opponent.prime(events);
		checkTiming();
		opponent.checkTiming();
		this.events.addAll(events);
	}

	private List<Card> getAbilityCards() {
		List<Card> cards = new ArrayList<>();
		// check stage
		cards.addAll(board.getStage().getCharacters());
		// check hand
		cards.addAll(board.getHand().getCards());
		// check memory
		cards.addAll(board.getMemoryZone().getCards());
		// check level
		cards.addAll(board.getLevel().getCards());
		// check waiting room
		cards.addAll(board.getWaitingRoom().getCards());
		return cards;
	}

	private void removeExpiredMods(List<GameEvent> events) {
		for (GameEvent event : events) {
			if (event.getType() == EventType.PHASE) {
				for (Card card : getAbilityCards()) {
					card.removeExpiredMods(((PhaseEvent) event).getPt());
				}
			}
		}
	}

	void checkContinuous(PlayerController opponent) {
		for (Card card : getAbilityCards()) {
			for (ContinuousAbility a : card.getContinuousAbilities()) {
				a.setTargets(this, opponent);
				a.setEnabled(true);
			}
		}
	}

	void prime(List<GameEvent> events) {
		choices.clear();
		List<Character> chars = new ArrayList<>();
		// check stage
		chars.addAll(board.getStage().getCharacters());
		chars.addAll(board.getHand().getCardsOfType(Character.class));
		chars.addAll(board.getWaitingRoom().getCardsOfType(Character.class));

		for (GameEvent e : events) {
			log(e);
			for (Character character : chars) {
				for (AutoAbility autoAbility : character.getAutoAbilities()) {

					if ((autoAbility.isSelf() && e.getSourcePlayer().equals(player))
							|| (!autoAbility.isSelf() && !e.getSourcePlayer().equals(player))) {
						boolean primed = autoAbility.prime(e);
						if (primed) {
							choices.add(autoAbility);
						}
					}
				}
			}
		}
	}

	private boolean activateAuto() {
		// refresh point
		if (refreshPoint > 0) {
			new PlaceInDamageFromLibrary().execute(this, null);
			refreshPoint--;
			return true;
		}

		// TODO modifier update

		// trigger (auto) abilities

		Iterator<AutoAbility> ite = choices.iterator();
		while (ite.hasNext()) {
			AutoAbility auto = ite.next();
			if (auto.isResolved() || !auto.canActivate()) {
				ite.remove();
			}
		}

		if (choices.isEmpty()) {
			return false;
		}

		System.out.println(player.getName() + " has " + choices.size() + " auto abilities to activate");
		System.out.println(choices.get(0));
		AutoAbility choice = getChoice("Pick auto ability to activate", choices);
		gm.execute(choice, player);
		return true;
	}

	public Board getBoard() {
		return board;
	}

	// public abstract void handleException(NotEnoughStockException e);

	public final void setReader(Reader reader) {
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

	public void readDeck() {
		List<Card> deck = reader.readDeck();
		System.out.println(deck.size());
		setDeck(deck);
	}

	public void displayStage() {
		writer.displayStage();
	}

	public void displayHand() {
		writer.displayHand();
	}

	public void displayWaitingRoom() {
		writer.displayWaitingRoom();
	}

	public void displayDamageZone() {
		writer.displayDamageZone();
	}

	public void displayLevel() {
		writer.displayLevel();
	}

	public void displayStock() {
		writer.displayStock();
	}

	public void log(Object text) {
		writer.log(text);
	}

	public PlayerController toRestricted() {
		PlayerController newPC = new PlayerController(player.getName(), reader, writer);
		if (board != null) {
			newPC.board = board.toRestrictedBoard();
		}
		newPC.setGM(gm);
		return newPC;
	}

	public GameManager getGM() {
		return gm;
	}

}
