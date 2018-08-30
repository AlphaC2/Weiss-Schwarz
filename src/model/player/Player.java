package model.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import command.*;
import controller.PlayerController;
import model.board.Board;
import model.board.DamageZone;
import model.board.Hand;
import model.board.SlotType;
import model.board.WaitingRoom;
import model.card.Card;
import model.card.Character;
import model.card.Trigger;
import model.card.ability.Abilities;

public class Player {
	List<Card> deck;
	Board board;
	Player opponent;
	PlayerPhase phase;
	String name;
	PlayerController controller;
	List<Command> commands = new ArrayList<>();

	public Player(String name, PlayerController controller) {
		this.name = name;
		opponent = null;
		this.controller = controller;
		phase = PlayerPhase.OPPONENTS_TURN;
		commands.add(new DisplayHand());
		commands.add(new Clock());
		commands.add(new PlayClimax());
		commands.add(new EndPhase());
	}

	public void setDeck(List<Card> deck) {
		this.deck = new ArrayList<Card>(deck);
		board = new Board(this.deck);
	}

	public Player getOpponent() {
		return opponent;
	}

	public void setOpponent(Player opponent) {
		if (this.opponent == null && opponent != null)
			this.opponent = opponent;
	}

	public void draw() {
		board.draw();
	}

	public void draw(int i) {
		for (int j = 0; j < i; j++) {
			draw();
		}
	}

	public void discardCard(Card c) {
		board.discard(c);
	}

	public void displayLibrarySize() {
		controller.log(board.cardsInLibrary() + " cards in library");
	}

	public void endPhase() {
		switch (phase) {
		case STAND:
			phase = PlayerPhase.DRAW;
			break;
		case DRAW:
			phase = PlayerPhase.CLOCK;
			break;
		case CLOCK:
			phase = PlayerPhase.MAIN;
			break;
		case MAIN:
			phase = PlayerPhase.CLIMAX;
			break;
		case CLIMAX:
			phase = PlayerPhase.ATTACK;
			board.endClimax();
			break;
		case ATTACK:
			phase = PlayerPhase.ENCORE;
			break;
		case ATTACK_DECLARATION:
			phase = PlayerPhase.ENCORE;
			break;
		case ENCORE:
			phase = PlayerPhase.END;
			break;
		case END:
			phase = PlayerPhase.OPPONENTS_TURN;
			break;
		case OPPONENTS_TURN:
			phase = PlayerPhase.STAND;
			break;
		default:
			break;
		}
	}

	public void nextStep() {
		switch (phase) {
		case ATTACK:
			phase = PlayerPhase.ATTACK_DECLARATION;
			break;
		case ATTACK_DECLARATION:
			phase = PlayerPhase.TRIGGER;
			break;
		case TRIGGER:
			phase = PlayerPhase.COUNTER;
			break;
		case COUNTER:
			phase = PlayerPhase.DAMAGE;
			break;
		case DAMAGE:
			phase = PlayerPhase.END_OF_ATTACK;
			break;
		case END_OF_ATTACK:
			phase = PlayerPhase.ATTACK_DECLARATION;
			break;
		default:
			break;
		}
	}

	public PlayerPhase getPhase() {
		return phase;
	}

	public String getName() {
		return name;
	}

	public int getLibrarySize() {
		return board.cardsInLibrary();
	}

	public int getWaitingRoomSize() {
		return board.cardsInWaitingRoom();
	}

	public void discard(Card c) {
		board.discard(c);
	}

	public void shuffleLibrary() {
		board.shuffleLibrary();
	}

	public int getHandSize() {
		return board.cardsInHand();
	}

	public DamageZone getDamageZone() {
		return board.getDamageZone();
	}

	public int memorySize() {
		return board.memorySize();
	}

	public void playCharacter(Card card, SlotType slot) {
		board.play(card, slot);
	}

	public void clock(Card c) {
		board.clock(c);
	}

	public void trigger() {
		List<Trigger> triggers = board.trigger();
		// Activate triggers
		for (Trigger trigger : triggers) {
			Abilities.trigger(trigger);
		}
	}

	public boolean declareAttack(SlotType s) {
		return board.declareAttack(s);
	}

	public void standAll() {
		board.standAll();
	}

	public int getSoul(SlotType s) {
		return board.getSoul(s);
	}

	public void takeDamage(int damage) {
		List<Card> cards = board.takeDamage(damage);
		if (cards != null) {
			controller.log(name + " Leveled Up");
			Card chosen = controller.getChoice(name + " choose a card to level up", cards);
			board.levelUp(cards, chosen);
		}

	}

	public SlotType chooseSlot() {
		return controller.getChoice("Choose Slot", Arrays.asList(SlotType.values()));
	}

	public Card chooseCardFromHand() {
		return controller.getChoice("Which card?", board.getHand().getCards());
	}

	public void encore() {
		List<Character> dead = board.getReversed();
		Iterator<Character> iterator = dead.iterator();
		Character current;
		SlotType s;
		while (iterator.hasNext()) {
			current = iterator.next();
			s = board.getSlot(current).getSlotType();
			board.sendToWaitingRoom(current);
			board.remove(s);
			boolean choice = controller.getChoice(name + " Encore:" + current.toShortString() + "?(y/n)");
			if (choice) {
				if (board.payCost(3)) {
					board.salvage(current);
					board.play(current, s);
					controller.log(current.toShortString() + " encored");

				} else {
					controller.log("Not enough stock to encore");
				}
			}

		}
	}

	public Character getCharacter(SlotType s) {
		return board.getCharacter(s);
	}

	public void displayLevel() {
		board.displayLevel();
	}

	public void displayStock() {
		board.displayStock();
	}

	public void attack() {
		SlotType s = null;
		SlotType across = null;
		Character attacking = null;
		Character defending = null;
		Boolean declared;
		// Beginning of Attack Phase
		nextStep();
		while (true) {
			declared = false;
			while (!declared) {
				// Attack Declaration
				controller.displayStage();
				opponent.controller.displayStage();
				boolean attack = controller.getChoice("Declare an attack");
				if (!attack) {
					return;
				}

				Character c = controller.getChoice("Choose a character to attack with", board.getStanding());
				s = board.getSlot(c).getSlotType();
				declared = declareAttack(s);
			}
			controller.log(getPhase());
			across = SlotType.getAcross(s);
			nextStep();
			attacking = getCharacter(s);
			defending = opponent.getCharacter(across);
			nextStep();

			// Trigger
			trigger();
			controller.log(getPhase());
			nextStep();

			// Counter
			controller.log(getPhase());
			nextStep();

			// Damage
			controller.log(getPhase());
			int amount = getSoul(s);
			if (defending == null)
				amount++;
			controller.log("Deal " + amount + " damage to opponent");
			opponent.takeDamage(amount);
			nextStep();

			// End of Attack
			controller.log(getPhase());
			if (defending != null) {
				if (attacking.getCurrentPower() > defending.getCurrentPower()) {
					defending.reverse();
				} else if (attacking.getCurrentPower() < defending.getCurrentPower()) {
					attacking.reverse();
				} else if (attacking.getCurrentPower() == defending.getCurrentPower()) {
					defending.reverse();
					attacking.reverse();
				} else {
					throw new IllegalStateException("End of Attack step, should be unreachable code");
				}
			}

			// Attack
		}
	}

	public List<Command> getAvailableCommands() {
		// TODO be implemented properly
		return commands;
	}

	public void executeCommand() {
		Command cmd = controller.getChoice("Enter command", getAvailableCommands());
		cmd.execute(controller, opponent.controller);
	}

	public Board getBoard() {
		return board;
	}

	public Hand getHand() {
		return board.getHand();
	}

	public WaitingRoom getWaitingRoom() {
		return board.getWaitingRoom();
	}

}
