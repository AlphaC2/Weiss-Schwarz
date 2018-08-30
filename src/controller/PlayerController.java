package controller;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;
import model.player.Player;

public abstract class PlayerController {

	private Player player;
	private ReadUserInput reader;

	public PlayerController(String name, ReadUserInput reader) {
		player = new Player(name);
		this.reader = reader;
		readDeck();
	}

	public final void setDeck(List<Card> deck) {
		player.setDeck(deck);
	}

	public abstract void buildDeck();

	public abstract void readDeck();

	public Card chooseCardFromHand() {
		return getChoice("Which card?", getPlayer().getBoard().getHand().getCards());
	}

	public Card chooseCardFromHand(Class<? extends Card> c) {
		List<Card> list = new ArrayList<>();
		for (Card card : getPlayer().getBoard().getHand().getCards()) {
			if (card.getClass().equals(c)) {
				list.add(card);
			}
		}
		return getChoice("Which card?", list);
	}

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
}
