package game.controller;

import java.time.LocalDateTime;
import java.util.List;

import game.command.Discard;
import game.model.ability.Activatable;
import game.model.ability.action.concrete.DrawToHand;
import game.model.board.Board;
import game.model.board.Hand;
import game.model.player.Player;
import game.model.player.PlayerPhase;

public class GameManager implements Runnable {
	GameState gameState;
	PlayerController currentPlayer;
	private boolean alive = true;
	private Thread thread;
	private LocalDateTime lastAction;
	
	public GameManager(PlayerController p1, PlayerController p2) {
		gameState = new GameState(p1,p2);
		currentPlayer = p1;
		p1.setGM(this);
		p2.setGM(this);
		thread = new Thread(this);
		lastAction = LocalDateTime.now();
	}
	
	public GameState getGameState(){
		return gameState;
	}
	
	private void setup(){
		gameState.getP1().readDeck();
		gameState.getP2().readDeck();
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
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.out.println("Stopping thread");
				return;
			}
//			System.out.println("Updated:" + Duration.between(lastAction, LocalDateTime.now()).getSeconds());
			lastAction = LocalDateTime.now();
			log(currentPlayer.getPlayer(),currentPlayer.getPlayer().getName() + ":" + currentPlayer.getPlayer().getPhase() + " Phase");
			currentPlayer.getPlayer().executeCommand();
		}
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
		if(getController(player).isAlive()){
			cmd.execute(getController(player), getOpponent(getController(player)));
		}
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
	
	public Thread getThread(){
		return thread;
	}
	
	public LocalDateTime getLastAction() {
		return lastAction;
	}

	public void log(Player p, Object text){
		getController(p).log(text);
	}

	public boolean isAlive(){
		return alive;
	}
	@Override
	public void run() {
		gameLoop();
		System.out.println("Ended execution of game ");
	}
	
	@Override
	public void finalize(){
		System.out.println("GM DEALLOCATED");
	}

}
