package game.model.board;

import java.util.List;

import game.model.card.Card;
import game.model.card.Climax;
import game.model.card.Colour;

public class Board {
	private Library library;
	private Stock stock;
	private LevelZone level;
	private DamageZone damage;
	private MemoryZone memory;
	private WaitingRoom waitingRoom;
	private Hand hand;
	private Stage stage;
	private ResolutionZone resolution;
	public Climax climaxZone;
	
	public Board(){
		stock = new Stock();
		level = new LevelZone();
		damage = new DamageZone();
		memory = new MemoryZone();
		waitingRoom = new WaitingRoom();
		hand = new Hand();
		stage = new Stage();
		climaxZone = null;
		resolution = new ResolutionZone();
		library = new Library();
	}

	public Board(List<Card> deck) {
		this();
		library = new Library(deck);
	}
	
	public boolean hasColour(Colour colour){
		return level.hasColour(colour) || damage.hasColour(colour);
	}
	
	/* Actions*/
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
	
	public ResolutionZone getResolutionZone(){
		return resolution;
	}
	
	public Board toRestrictedBoard(){
		Board newBoard = new Board();
		newBoard.library = (Library) library.toRestricted();
		newBoard.stock = (Stock) stock.toRestricted();
		
		return newBoard;
	}

}
