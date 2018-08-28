package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;
import model.card.Climax;
import model.card.Colour;
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
	
	/* Display */
	public void displayHand(){
		hand.display();
	}
	
	public void displayStage(){
		stage.displayStage();
		if (climaxZone != null)
			System.out.println("Active Climax: " + climaxZone);
	}
	
	public void displayWaitingRoom(){
		waitingRoom.displayWaitingRoom();
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
	
	public void draw(){
		hand.add(library.draw());
	}
	
	public void addToLibrary(List<Card> passedCards){
		library.addCards(passedCards);
	}
	
	public void shuffleLibrary(){
		library.shuffle();
	}

	public Card chooseFromHand(int i){
		return hand.get(i);
	}
	
	public void discard(int i){
		waitingRoom.sendToWaitingRoom(hand.get(i));
	}
	
	public void payCost(){
		waitingRoom.sendToWaitingRoom(stock.pay());
	}
	
	public void payCost(int i){
		for (int j = 0; j < i; j++) {
			payCost();
		}
	}
	
	public void bounce(Slot s){
		hand.add(stage.remove(s));
	}
	
	public void topDeck(Slot s){
		library.placeTop(stage.remove(s));
	}
	
	public void kickToClock(Slot s){
		damage.takeNoCancelDamage(stage.remove(s));
	}
	
	public void stockBomb(Slot s){
		stock.addStock(stage.remove(s));
	}
	
	public void kickToMemory(Slot s){
		memory.sendToMemory(stage.remove(s));
	}
	
	public void encore(Slot s){
		Character c = stage.remove(s);
		waitingRoom.sendToWaitingRoom(c);
		waitingRoom.salvage(waitingRoom.size() - 1);
		stage.place(c, s);
	}
	
	public void play(int i, Slot s){
		Card c = hand.get(i);
		if (c instanceof Character){
			if (stage.hasChar(s))
				waitingRoom.sendToWaitingRoom(stage.remove(s));
			stage.place((Character) c, s);
		}else{
			hand.add(c);
			System.out.println("Card is not a Character");
		}
	}

	public void clock(int index) {
		damage.takeNoCancelDamage(hand.get(index));
	}

	public List<Trigger> trigger() {
		Card trigger = library.draw();
		System.out.println("Triggerd:" + trigger);
		List<Trigger> triggers = trigger.getTrigger();
		stock.addStock(trigger);
		return triggers;
	}

	public boolean declareAttack(Slot s) {
		if (s == Slot.REAR_LEFT || s== Slot.REAR_RIGHT){
			System.out.println("Cannot attack from back row");
			return false;
		}
		return stage.rest(s);
	}

	public int getSoul(Slot s){
		Character c = stage.getCard(s);
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

	public void levelUp(List<Card> cards, int index) {
		level.levelUp(cards.remove(index));
		waitingRoom.sendToWaitingRoom(cards);
	}
	
}
