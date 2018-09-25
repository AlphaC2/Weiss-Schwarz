package game.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.command.Discard;
import game.model.ability.Activatable;
import game.model.ability.action.DrawToHand;
import game.model.board.Board;
import game.model.board.Hand;
import game.model.player.Player;
import game.model.player.PlayerPhase;


public class GameManager {
	
	GameState gameState;
	PlayerController currentPlayer;
	boolean alive = true;
	private int id;
	private static int counter = 1;
	private static Map<Integer, GameState> map = new HashMap<>();

	public GameManager(PlayerController p1, PlayerController p2) {
		id = counter;
		gameState = new GameState(p1,p2);
		map.put(id, gameState);
		currentPlayer = p1;
		p1.setGM(this);
		p2.setGM(this);
		counter++;
	}
	
	public GameState getGameState(){
		return gameState;
	}
	
	public static GameState getGameState(int id){
		GameState gameState = map.get(id);
		return gameState;
	}
	
	public int getID(){
		return id;
	}
	
	private void setup(){
		gameState.getP1().getBoard().getLibrary().shuffle();
		gameState.getP2().getBoard().getLibrary().shuffle();
		gameState.getP1().getPlayer().endPhase();
		DrawToHand draw = new DrawToHand();
		for (int i = 0; i < 4; i++) {
			this.execute(draw, gameState.getP1().getPlayer());
		}
		
		for (int i = 0; i < 5; i++) {
			this.execute(draw, gameState.getP2().getPlayer());
		}
		
	}

	public void gameLoop() {
		setup();
		while (alive) {
			log(currentPlayer.getPlayer(),currentPlayer.getPlayer().getName() + ":" + currentPlayer.getPlayer().getPhase() + " Phase");
			currentPlayer.getPlayer().executeCommand();
		}
	}
	
	public void log(Player p, Object text){
		getController(p).log(text);
	}

	public void endTurn(Player player) {
		System.out.println("GM end turn");
		while(getController(player).getBoard().getHand().size() > Hand.MAX_HAND_SIZE){
			new Discard().execute(getController(player), getOpponent(getController(player)));
		}
		
		Board board = getController(player).getBoard();
		if (board.climaxZone != null){
			board.getWaitingRoom().add(board.climaxZone);
			board.climaxZone = null;
		}
		
		if (player.getPhase() == PlayerPhase.END){
			if (currentPlayer == gameState.getP1()){
				currentPlayer = gameState.getP2();
			}else if (currentPlayer == gameState.getP2()){
				currentPlayer = gameState.getP1();
			}else
				System.out.println("ERROR");
		}
	}

	public void execute(Activatable cmd, Player player) {
		cmd.execute(getController(player), getOpponent(getController(player)));
	}
	
	public PlayerController getController(Player player){
		if (gameState.getP1().getPlayer().equals(player)){
			return gameState.getP1();
		} else {
			return gameState.getP2();
		}
	}
	
	private PlayerController getOpponent(PlayerController player){
		if (gameState.getP1().equals(player)){
			return gameState.getP2();
		} else {
			return gameState.getP1();
		}
	}

	public <T> T getChoice(Player player, String prompt, List<T> choices) {
		return getController(player).getChoice(prompt, choices);
	}

	public boolean getChoice(Player player, String prompt) {
		return getController(player).getChoice(prompt);
	}

	public void gameOver(PlayerController pc) {
		this.alive = false;
		pc.log("is loser");
		getOpponent(pc).log("is winner");
	}

	

}
