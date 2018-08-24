package model.board;

import java.util.List;

import model.card.Card;
import model.card.Climax;

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
	private PlayerPhase phase;
	
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
	
	public Library getLibrary() {
		return library;
	}
	public Stock getStock() {
		return stock;
	}
	public LevelZone getLevel() {
		return level;
	}
	public DamageZone getDamage() {
		return damage;
	}
	public MemoryZone getMemory() {
		return memory;
	}
	public WaitingRoom getWaitingRoom() {
		return waitingRoom;
	}
	public Hand getHand() {
		return hand;
	}
	public Stage getStage() {
		return stage;
	}
	public Climax getClimaxZone() {
		return climaxZone;
	}
	public PlayerPhase getPhase(){
		return phase;
	}
	public void playClimax(Climax c){
		climaxZone = c;
	}
	public void endClimax(){
		if(climaxZone != null){
			waitingRoom.sendToWaitingRoom(climaxZone);
			climaxZone = null;
		}
	}
	public void displayStage(){
		stage.displayStage();
		if (climaxZone != null)
			System.out.println("Active Climax: " + climaxZone);
	}
	
	public void standAll(){
		stage.standAll();
	}
	
	public void sendToWaitingRoom(Card c){
		waitingRoom.sendToWaitingRoom(c);
	}
	
	public int cardsInWaitingRoom(){
		return waitingRoom.size();
	}
	
	public void displayWaitingRoom(){
		waitingRoom.displayWaitingRoom();
	}
	
	public void refreshWaitingRoom(){
		library.addCards(waitingRoom.refresh());
		damage.takeRefreshDamage(library.draw());
	}
	
	public int memorySize(){
		return memory.size();
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
	
	public void displayDamage(){
		damage.display();
	}
	
	public Card chooseFromHand(int i){
		return hand.get(i);
	}
	
	public void discard(int i){
		waitingRoom.sendToWaitingRoom(hand.get(i));
	}
	
	public int cardsInHand(){
		return hand.size();
	} 
	
	public void displayHand(){
		hand.display();
	}
}
