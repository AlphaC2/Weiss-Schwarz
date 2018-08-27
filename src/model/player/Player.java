package model.player;

import java.util.ArrayList;
import java.util.List;

import model.board.Board;
import model.board.PlayerPhase;
import model.card.Card;
import model.card.Climax;

public class Player {
	List<Card> deck;
	Board board;
	Player opponent;
	PlayerPhase phase;
	String name;
	
	public Player(List<Card> deck, String name) {
		super();
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
		this.opponent = opponent;
	}

	public Board getBoard() {
		return board;
	}

	public void setDeck(List<Card> deck) {
		this.deck = new ArrayList<Card>(deck);
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
		board.sendToWaitingRoom(board.chooseFromHand(i));
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
			case END_OF_ATTACK: phase = PlayerPhase.ATTACK; break;
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

}
