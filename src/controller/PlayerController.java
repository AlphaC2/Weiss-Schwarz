package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.ability.AbilityInterface;
import model.ability.action.TimedAction;
import model.board.Board;
import model.card.Card;
import model.player.PhaseTiming;
import model.player.Player;
import model.player.PlayerPhase;
@SuppressWarnings("rawtypes")
public abstract class PlayerController {

	private Player player;
	private Board board;
	private ReadUserInput reader;
	private List<TimedAction> unresolvedActions;
	private Map<PlayerPhase,List<TimedAction>> map;
	private boolean isAlive = true;
	private GameManager gm;
	
	public PlayerController(String name, ReadUserInput reader) {
		player = new Player(name);
		this.reader = reader;
		unresolvedActions = new ArrayList<>();
		map = new HashMap<>();
	}

	public void setGM(GameManager gm){
		this.gm = gm;
		player.setGM(gm);
	}
	public final void setDeck(List<Card> deck) {
		board = new Board(deck);
	}
	
	public abstract void buildDeck();

	public abstract void readDeck();

	public final <T> T getChoice(String prompt, List<T> choices) {
		return reader.getChoice(prompt, choices);
	}

	public final boolean getChoice(String prompt) {
		return reader.getChoice(prompt);
	}

	public abstract void log(Object text);

	public Player getPlayer() {
		return player;
	}
	
	public void checkTiming(PlayerController p2, PhaseTiming timing){
		/*for (AbilityInterface action : unresolvedActions) {
			action.execute(this, p2);
		}
		unresolvedActions.clear();*/
		if (map.containsKey(player.getPhase())){
			List<TimedAction> actions = map.get(player.getPhase());
			List<TimedAction> filteredActions = new ArrayList<>();
			for (AbilityInterface action : actions) {
				
			}
			int size = actions.size();
			for (int i = 0; i < size; i++){
				AbilityInterface choice = this.getChoice("Choose action to resolve", actions);
				choice.execute(this, p2);
				actions.remove(choice);
			}
			
		}
	}
	
	
	public void addToUnresolved(TimedAction action){
		addToUnresolved(player.getPhase(), action);
//		unresolvedActions.add(action);
	}
	
	public void addToUnresolved(PlayerPhase phase, TimedAction action){
		List<TimedAction> actions;
		if (map.containsKey(phase)){
			actions = map.get(phase);
		} else {
			actions = new ArrayList<>();
			map.put(phase, actions);
		}
		actions.add(action);
//		unresolvedActions.add(action);
	}

	public abstract void displayStage();

	public abstract void displayHand();

	public abstract void displayWaitingRoom();

	public abstract void displayDamageZone();

	public abstract void displayLevel();

	public abstract void displayStock();

	public Board getBoard() {
		return board;
	}
	
	//public abstract void handleException(NotEnoughStockException e);

	public final void setReader(ReadUserInput reader) {
		this.reader = reader;
	}

	public void deckOut() {
		log("deck out");
		gameOver();
	}

	public void level4() {
		log("level 4");
		gameOver();
	}
	
	private void gameOver(){
		isAlive = false;
		unresolvedActions.clear();
		gm.gameOver(this);
	}

	public boolean isAlive() {
		return isAlive;
	}
}
