package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;
import model.card.Climax;
import model.card.Colour;
import model.card.Position;
import model.card.Trigger;
import model.card.ability.Abilities;
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
	
	/* Get */
	public int cardsInHand(){
		return hand.size();
	} 
	
	public int cardsInWaitingRoom(){
		return waitingRoom.size();
	}
	
	public int cardsInLibrary() {
		return library.size();
	}
	
	public boolean hasColour(Colour colour){
		return level.hasColour(colour) || damage.hasColour(colour);
	}
	
	public int memorySize(){
		return memory.size();
	}
	
	public int damageSize(){
		return damage.size();
	}
	
	/* Actions*/
	public void playClimax(Climax c){
		//TODO abilities activation
		hand.remove(c);
		climaxZone = c;
	}
	
	public void endClimax(){
		if(climaxZone != null){
			waitingRoom.add(climaxZone);
			climaxZone = null;
		}
	}
	
	public void refreshWaitingRoom(){
		library.addCards(waitingRoom.refresh());
		damage.add(library.draw());
	}
	
	public void draw(int count){
		for (int j = 0; j < count; j++) {
			draw();
		}
	}
	
	public Card draw(){
		Card c = library.draw();
		hand.add(c);
		return c;
	}
	
	public void addToLibrary(List<Card> passedCards){
		library.addCards(passedCards);
	}
	
	public void shuffleLibrary(){
		library.shuffle();
	}
	
	public void discard(Card c){
		hand.remove(c);
		waitingRoom.add(c);
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
	
	public void bounce(SlotType s){
		hand.add(stage.removeCharacter(s));
	}
	
	public void topDeck(SlotType s){
		library.placeTop(stage.removeCharacter(s));
	}
	
	public void kickToClock(SlotType s){
		damage.add(stage.removeCharacter(s));
	}
	
	public void stockBomb(SlotType s){
		stock.add(stage.removeCharacter(s));
	}
	
	public void kickToMemory(SlotType s){
		memory.add(stage.removeCharacter(s));
	}
	
	public void encore(SlotType s){
		Character c = stage.removeCharacter(s);
		waitingRoom.add(c);
		waitingRoom.remove(c);
		stage.place(c, s);
	}
	
	public void play(Card c, SlotType s){
		if (c instanceof Character){
			if (stage.hasCharacter(s))
				waitingRoom.add(stage.removeCharacter(s));
			stage.place((Character) c, s);
		}else{
			hand.add(c);
			System.out.println("Card is not a Character");
		}
	}

	public void play(Character current, SlotType s) {
		if (stage.hasCharacter(s))
			waitingRoom.add(stage.removeCharacter(s));
		hand.remove(current);
		stage.place(current, s);
	}
	
	public void clock(Card c) {
		hand.remove(c);
		damage.add(c);
	}

	public void trigger() {
		Card trigger = library.draw();
		System.out.println("Triggerd:" + trigger);
		List<Trigger> triggers = trigger.getTrigger();
		stock.add(trigger);
		for (Trigger t : triggers) {
			Abilities.trigger(t);
		}
	}

	public boolean declareAttack(Slot slot) {
		SlotType s = slot.getSlotType();
		if (s == SlotType.REAR_LEFT || s== SlotType.REAR_RIGHT){
			System.out.println("Cannot attack from back row");
			return false;
		}
		return stage.rest(s);
	}

	public List<Card> takeDamage(int amount) {
		List<Card> cards = new ArrayList<Card>();
		Card c;
		for (int i = 0; i < amount; i++) {
			c = library.draw();
			cards.add(c);
			if (c instanceof Climax){
				waitingRoom.add(cards);
				return null;
			}
		}
		return damage.takeDamage(cards);
		
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

}
