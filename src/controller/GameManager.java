package controller;

import java.util.List;

import command.*;
import io.ConsoleReadUserInput;
import model.ability.Activatable;
import model.ability.action.DrawToHand;
import model.board.Hand;
import model.player.Player;
import model.player.PlayerPhase;


public class GameManager {
	
	private static GameManager instance;
	
	ConsoleReadUserInput reader;
	PlayerController player1;
	PlayerController player2;
	Player currentPlayer;
	boolean alive = true;

	public static GameManager getInstance(){
		if (instance == null){
			instance = new GameManager();
		}
		return instance;
	}
	
	public void init(PlayerController p1, PlayerController p2) {
		reader = new ConsoleReadUserInput();
		player1 = p1;
		player2 = p2;
		currentPlayer = player1.getPlayer();
	}
	
	private void setup(){
		player1.getBoard().getLibrary().shuffle();
		player2.getBoard().getLibrary().shuffle();
		player1.getPlayer().endPhase();
		DrawToHand draw = new DrawToHand();
		for (int i = 0; i < 4; i++) {
			this.execute(draw, player1.getPlayer());
		}
		
		for (int i = 0; i < 5; i++) {
			this.execute(draw, player2.getPlayer());
		}
		
	}

	public void gameLoop() {
		setup();
		while (alive) {
			log(currentPlayer,currentPlayer.getName() + ":" + currentPlayer.getPhase() + " Phase");
			currentPlayer.executeCommand();
		}
	}
	
	public void log(Player p, Object text){
		getController(p).log(text);
	}

	public void endTurn(Player player) {
		while(getController(player).getBoard().getHand().size() > Hand.MAX_HAND_SIZE){
			new Discard().execute(getController(currentPlayer),	getOpponent(currentPlayer));
		}
		
		if (player.getPhase() == PlayerPhase.OPPONENTS_TURN){
			if (currentPlayer == player1.getPlayer()){
				currentPlayer = player2.getPlayer();
			}else if (currentPlayer == player2.getPlayer()){
				currentPlayer = player1.getPlayer();
			}else
				System.out.println("ERROR");
		}
	}

	public void execute(Activatable cmd, Player player) {
		try {
			cmd.execute(getController(player), getOpponent(player));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private PlayerController getController(Player player){
		if (player1.getPlayer().equals(player)){
			return player1;
		} else {
			return player2;
		}
	}
	
	private PlayerController getOpponent(Player player){
		if (player1.getPlayer().equals(player)){
			return player2;
		} else {
			return player1;
		}
	}

	public <T> T getChoice(Player player, String prompt, List<T> choices) {
		return getController(player).getChoice(prompt, choices);
	}

	public boolean getChoice(Player player, String prompt) {
		return getController(player).getChoice(prompt);
	}

}
