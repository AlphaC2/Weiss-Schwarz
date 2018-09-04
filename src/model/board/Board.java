package model.board;

import java.util.List;

import model.card.Card;
import model.card.Climax;
import model.card.Colour;
import model.card.Character;

public class Board {
	private Library library;
	private Stock stock;
	private LevelZone level;
	private DamageZone damage;
	private MemoryZone memory;
	private WaitingRoom waitingRoom;
	private Hand hand;
	private Stage stage;
	private Climax climaxZone;
	
	public Board(List<Card> deck) {
		super();
		library = new Library(deck);
		stock = new Stock();
		level = new LevelZone();
		damage = new DamageZone();
		memory = new MemoryZone();
		waitingRoom = new WaitingRoom();
		hand = new Hand();
		stage = new Stage();
		climaxZone = null;
	}
	
	public boolean hasColour(Colour colour){
		return level.hasColour(colour) || damage.hasColour(colour);
	}
	
	/* Actions*/
	public void playClimax(Climax c){
		hand.remove(c);
		climaxZone = c;
	}
	
	public void endClimax(){
		if(climaxZone != null){
			waitingRoom.add(climaxZone);
			climaxZone = null;
		}
	}
	
	public boolean payCost(int i){
		List<Card> cards = stock.pay(i);
		if (cards == null)
			return false;
		else{
			waitingRoom.add(cards);
			return true;
		}
	}

	public void play(Character current, SlotType s) {
		if (stage.hasCharacter(s))
			waitingRoom.add(stage.removeCharacter(s));
		hand.remove(current);
		stage.place(current, s);
	}

	public boolean declareAttack(Slot slot) {
		SlotType s = slot.getSlotType();
		if (s == SlotType.REAR_LEFT || s== SlotType.REAR_RIGHT){
			System.out.println("Cannot attack from back row");
			return false;
		}
		return stage.rest(s);
	}
	
	public Hand getHand(){
		return hand;
	}
	
	public Stage getStage() {
		return stage;
	}

	public WaitingRoom getWaitingRoom() {
		return waitingRoom;
	}

	public DamageZone getDamageZone() {
		return damage;
	}

	public LevelZone getLevel() {
		return level;
	}

	public Stock getStock() {
		return stock;
	}

	public Library getLibrary() {
		return library;
	}

	public MemoryZone getMemoryZone() {
		return memory;
	}

}
