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
	
}
