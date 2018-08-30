package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;
import model.card.Climax;
import model.card.Colour;
import model.card.Position;
import model.card.Trigger;
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
	
	public void displayDamage(){
		damage.display();
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
		hand.remove(c);
		climaxZone = c;
	}
	
	public void endClimax(){
		if(climaxZone != null){
			waitingRoom.sendToWaitingRoom(climaxZone);
			climaxZone = null;
		}
	}
	
	public void standAll(){
		stage.standAll();
	}
	
	public void sendToWaitingRoom(Card c){
		waitingRoom.sendToWaitingRoom(c);
	}
	
	public void refreshWaitingRoom(){
		library.addCards(waitingRoom.refresh());
		damage.takeNoCancelDamage(library.draw());
	}
	
	public void draw(int count){
		for (int j = 0; j < count; j++) {
			draw();
		}
	}
	
	public void draw(){
		hand.add(library.draw());
	}
	
	public void addToLibrary(List<Card> passedCards){
		library.addCards(passedCards);
	}
	
	public void shuffleLibrary(){
		library.shuffle();
	}

	public Card chooseFromHand(Card c){
		return hand.get(c);
	}
	
	public void discard(Card c){
		waitingRoom.sendToWaitingRoom(hand.get(c));
	}
	
	public boolean payCost(int i){
		List<Card> cards = stock.pay(i);
		if (cards == null)
			return false;
		else{
			waitingRoom.sendToWaitingRoom(cards);
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
		damage.takeNoCancelDamage(stage.removeCharacter(s));
	}
	
	public void stockBomb(SlotType s){
		stock.addStock(stage.removeCharacter(s));
	}
	
	public void kickToMemory(SlotType s){
		memory.sendToMemory(stage.removeCharacter(s));
	}
	
	public void encore(SlotType s){
		Character c = stage.removeCharacter(s);
		waitingRoom.sendToWaitingRoom(c);
		waitingRoom.salvage(waitingRoom.size() - 1);
		stage.place(c, s);
	}
	
	public void play(Card c, SlotType s){
		if (c instanceof Character){
			if (stage.hasCharacter(s))
				waitingRoom.sendToWaitingRoom(stage.removeCharacter(s));
			stage.place((Character) c, s);
		}else{
			hand.add(c);
			System.out.println("Card is not a Character");
		}
	}
	
	public void remove(SlotType s){
		stage.removeCharacter(s);
	}

	public void play(Character current, SlotType s) {
		if (stage.hasCharacter(s))
			waitingRoom.sendToWaitingRoom(stage.removeCharacter(s));
		stage.place(current, s);
	}
	
	public void clock(Card c) {
		damage.takeNoCancelDamage(hand.get(c));
	}

	public List<Trigger> trigger() {
		Card trigger = library.draw();
		System.out.println("Triggerd:" + trigger);
		List<Trigger> triggers = trigger.getTrigger();
		stock.addStock(trigger);
		return triggers;
	}

	public boolean declareAttack(SlotType s) {
		if (s == SlotType.REAR_LEFT || s== SlotType.REAR_RIGHT){
			System.out.println("Cannot attack from back row");
			return false;
		}
		return stage.rest(s);
	}

	public int getSoul(SlotType s){
		Character c = stage.getCharacter(s);
		if (c == null)
			return 0;
		else
			return c.getSoul();
	}

	public List<Card> takeDamage(int amount) {
		List<Card> cards = new ArrayList<Card>();
		Card c;
		for (int i = 0; i < amount; i++) {
			c = library.draw();
			cards.add(c);
			if (c instanceof Climax){
				waitingRoom.sendToWaitingRoom(cards);
				return null;
			}
		}
		return damage.takeDamage(cards);
		
	}

	public void levelUp(List<Card> cards, Card card) {
		level.levelUp(card);
		cards.remove(card);
		waitingRoom.sendToWaitingRoom(cards);
	}

	public Character getCharacter(SlotType s) {
		return stage.getCharacter(s);
	}

	public void displayLevel() {
		level.display();
	}

	public List<Character> getReversed() {
		return stage.getCharacterByPosition(Position.REVERSED);
	}

	public void salvage(Character current) {
		waitingRoom.salvage(current);
	}
	
	public Slot getSlot(Character c){
		return stage.getSlot(c);
	}

	public void displayStock() {
		stock.display();
	}
	
	public Hand getHand(){
		return hand;
	}
	
	public List<Character> getStanding(){
		return stage.getCharacterByPosition(Position.STANDING);
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

}
