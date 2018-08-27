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
		damage.takeDamage(library.draw());
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
	
	public void bounce(int i){
		hand.add(stage.remove(i));
	}
	
	public void topDeck(int i){
		library.placeTop(stage.remove(i));
	}
	
	public void kickToClock(int i){
		damage.takeDamage(stage.remove(i));
	}
	
	public void stockBomb(int i){
		stock.addStock(stage.remove(i));
	}
	
	public void kickToMemory(int i){
		memory.sendToMemory(stage.remove(i));
	}
	
	public void encore(int i){
		Character c = stage.remove(i);
		waitingRoom.sendToWaitingRoom(c);
		waitingRoom.salvage(waitingRoom.size() - 1);
		stage.place(c, Slot.getName(i));
	}
	
}
