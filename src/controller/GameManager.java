package controller;

import java.util.List;

import command.*;
import io.ConsoleReadUserInput;
import model.board.Hand;
import model.card.Card;
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
		
		Card c;
		for (int i = 0; i < 4; i++) {
			c = player1.getBoard().getLibrary().draw();
			player1.getBoard().getHand().add(c);
		}
		
		for (int i = 0; i < 5; i++) {
			c = player2.getBoard().getLibrary().draw();
			player2.getBoard().getHand().add(c);
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

	public void execute(Command cmd, Player player) {
		cmd.execute(getController(player), getOpponent(player));
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
