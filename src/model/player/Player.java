package model.player;

import java.util.ArrayList;
import java.util.List;

import io.ReadUserInput;
import model.board.Board;
import model.board.Slot;
import model.card.Card;
import model.card.Climax;
import model.card.Trigger;
import model.card.ability.Abilities;

public class Player {
	ReadUserInput reader;
	List<Card> deck;
	Board board;
	Player opponent;
	PlayerPhase phase;
	String name;
	
	public Player(List<Card> deck, String name) {
		super();
		reader = new ReadUserInput();
		this.deck = new ArrayList<Card>(deck);
		board = new Board(this.deck);
		opponent = null;
		phase = PlayerPhase.OPPONENTS_TURN;
		this.name = name;
	}

	public Player getOpponent() {
		return opponent;
	}

	public void setOpponent(Player opponent) {
		if (this.opponent == null && opponent != null)
			this.opponent = opponent;
	}

	public void draw(){
		board.draw();
	}
	
	public void draw(int i){
		for (int j = 0; j < i; j++) {
			draw();
		}
	}
	
	public void displayHand(){
		board.displayHand();
	}
	
	public void discardCard(int i){
		board.discard(i);
	}
	
	public void displayLibrarySize(){
		System.out.println(board.cardsInLibrary() + " cards in library");
	}
	
	public void endPhase(){
		switch(phase){
		case STAND: phase = PlayerPhase.DRAW; break;
		case DRAW: phase = PlayerPhase.CLOCK; break;
		case CLOCK: phase = PlayerPhase.MAIN; break;
		case MAIN: phase = PlayerPhase.CLIMAX; break;
		case CLIMAX: phase = PlayerPhase.ATTACK; board.endClimax();	break;
		case ATTACK: phase = PlayerPhase.ENCORE; break;
		case ATTACK_DECLARATION: phase = PlayerPhase.ENCORE; break;
		case ENCORE: phase = PlayerPhase.END; break;
		case END: phase = PlayerPhase.OPPONENTS_TURN; break;
		case OPPONENTS_TURN: phase = PlayerPhase.DRAW; break;
		default: break;
		}
	}
	
	public void nextStep(){
		switch(phase){
			case ATTACK: phase = PlayerPhase.ATTACK_DECLARATION; break;
			case ATTACK_DECLARATION: phase = PlayerPhase.TRIGGER; break;
			case TRIGGER: phase = PlayerPhase.COUNTER; break;
			case COUNTER: phase = PlayerPhase.DAMAGE; break;
			case DAMAGE: phase = PlayerPhase.END_OF_ATTACK; break;
			case END_OF_ATTACK: phase = PlayerPhase.ATTACK_DECLARATION; break;
			default: break;
		}
	}
	
	public PlayerPhase getPhase(){
		return phase;
	}

	public String getName() {
		return name;
	}
	
	public int getLibrarySize(){
		return board.cardsInLibrary();
	}
	
	public int getWaitingRoomSize(){
		return board.cardsInWaitingRoom();
	}
	
	public void discard(int i){
		board.discard(i);
	}
	
	public void shuffleLibrary(){
		board.shuffleLibrary();
	}
	
	public void displayWaitingRoom(){
		board.displayWaitingRoom();
	}

	public int getHandSize() {
		return board.cardsInHand();
	}

	public void displayDamage() {
		board.displayDamage();
	}

	public void playClimax(int index) {
		Card c = board.chooseFromHand(index);
		if (c instanceof Climax)
			board.playClimax((Climax) c);
		else
			System.out.println("Not a climax");
	}

	public int memorySize() {
		return board.memorySize();
	}

	public void displayStage() {
		board.displayStage();
	}

	public void playCharacter(int cardIndex, Slot slot) {
		board.play(cardIndex, slot);
	}

	public void clock(int index) {
		board.clock(index);
	}

	public void trigger() {
		List<Trigger> triggers = board.trigger();
		//Activate triggers
		for (Trigger trigger : triggers) {
			Abilities.trigger(trigger);
		}
	}

	public boolean declareAttack(Slot s) {
		return board.declareAttack(s);
	}

	public void standAll() {
		board.standAll();
	}

	public int getSoul(Slot s){
		return board.getSoul(s);
	}

	public void takeDamage(int damage) {
		List<Card> cards = board.takeDamage(damage);
		if(cards != null){
			System.out.println("Level Up");
			for (Card card : cards) {
				System.out.println(cards.indexOf(card) + " - " + card.toShortString());
			}
			int index = chooseCard(cards.size());
			board.levelUp(cards, index);
		}
		
		
	}

	public Slot chooseSlot(){
		displayStage();
		return Slot.getName(chooseCard(5));
	}
	
	public int chooseCard(int max) {
		int i;
		System.out.println("Which card?");
		while(true){
			i = reader.getInt();
			if (i >= 0 && i < max) 
				return i;
		}
	}
	
	public int chooseCardFromHand() {
		displayHand();
		return chooseCard(getHandSize());
	}
	
}
