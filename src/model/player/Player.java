package model.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import command.*;
import controller.GameManager;
import controller.PlayerController;
import model.board.Board;
import model.board.DamageZone;
import model.board.Hand;
import model.board.LevelZone;
import model.board.SlotType;
import model.board.Stock;
import model.board.WaitingRoom;
import model.card.Card;
import model.card.Character;
import model.card.Trigger;
import model.card.ability.Abilities;

public class Player {
	List<Card> deck;
	Board board;
	GameManager gm;
	PlayerPhase phase;
	String name;
	List<Command> commands = new ArrayList<>();

	public Player(String name) {
		this.gm = GameManager.getInstance();
		this.name = name;
		phase = PlayerPhase.OPPONENTS_TURN;
	}

	public void setDeck(List<Card> deck) {
		this.deck = new ArrayList<Card>(deck);
		board = new Board(this.deck);
	}

	public void discardCard(Card c) {
		board.discard(c);
	}
	
	public void endPhase() {
		commands.clear();
		switch (phase) {
		case STAND:
			phase = PlayerPhase.DRAW;
			commands.add(new Draw());
			break;
		case DRAW:
			phase = PlayerPhase.CLOCK;
			commands.add(new Clock());
			break;
		case CLOCK:
			phase = PlayerPhase.MAIN;
			commands.add(new DisplayHand());
			commands.add(new DisplayDamage());
			commands.add(new EndPhase());
			break;
		case MAIN:
			commands.add(new PlayClimax());
			phase = PlayerPhase.CLIMAX;
			break;
		case CLIMAX:
			commands.add(new Attack());
			commands.add(new EndPhase());
			phase = PlayerPhase.ATTACK;
			break;
		case ATTACK:
			phase = PlayerPhase.ENCORE;
			break;
		case ATTACK_DECLARATION:
			phase = PlayerPhase.ENCORE;
			break;
		case ENCORE:
			phase = PlayerPhase.END;
			board.endClimax();
			break;
		case END:
			phase = PlayerPhase.OPPONENTS_TURN;
			gm.endTurn(this);
			break;
		case OPPONENTS_TURN:
			phase = PlayerPhase.STAND;
			break;
		default:
			break;
		}
		gm.log(this, System.lineSeparator());
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
			gm.log(this,name + " Leveled Up");
			Card chosen = gm.getChoice(this,name + " choose a card to level up", cards);
			board.levelUp(cards, chosen);
		}

	}

	public SlotType chooseSlot() {
		return gm.getChoice(this,"Choose Slot", Arrays.asList(SlotType.values()));
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
			boolean choice = gm.getChoice(this,name + " Encore:" + current.toShortString() + "?(y/n)");
			if (choice) {
				if (board.payCost(3)) {
					board.salvage(current);
					board.play(current, s);
					gm.log(this,current.toShortString() + " encored");

				} else {
					gm.log(this,"Not enough stock to encore");
				}
			}

		}
	}

	public Character getCharacter(SlotType s) {
		return board.getCharacter(s);
	}

	public void executeCommand() {
		if (commands.size() == 0){
			endPhase();
			return;
		}
		Command cmd = null;
		if (commands.size() == 1){
			cmd = commands.get(0);
		}else{
			cmd = gm.getChoice(this,"Enter command", commands);
		}
		gm.execute(cmd,this);
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

	public LevelZone getLevel() {
		return board.getLevel();
	}

	public Stock getStock() {
		return board.getStock();
	}

}
