package controller;

import java.util.List;

import command.*;
import io.ConsoleReadUserInput;
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
	
	private void init(){
		player1.getPlayer().shuffleLibrary();
		player2.getPlayer().shuffleLibrary();
		new EndPhase().execute(player1,null);
		player1.getPlayer().getBoard().draw(4);
		player2.getPlayer().getBoard().draw(5);
	}

	public void gameLoop() {
		init();

		while (alive) {
			log(currentPlayer,currentPlayer.getName() + ":" + currentPlayer.getPhase() + " Phase");
			currentPlayer.executeCommand();
		
		/*while (alive) {
			
			switch (currentPlayer.getPhase()) {
			case STAND:
				currentPlayer.standAll();

			case DRAW:
				currentPlayer.draw();
				break;

			case CLOCK:
				currentPlayer.clock();
				break;

			case MAIN:
				mainPhase();
				break;

			case CLIMAX:
				//TODO
				break;

			case ATTACK:
//				currentPlayer.attack();
				break;

			case ENCORE:
				encore();
				break;

			case END:
				break;

			default:
				// System.out.println("Phase:" + currentPlayer.getPhase());
				break;
			}
			endPhase();
	*/
		}
	}
	
	public void log(Player p, Object text){
		getController(p).log(text);
	}

	private void encore() {
		currentPlayer.encore();
		getOpponent(currentPlayer).getPlayer().encore();
	}

	/*
	private void mainPhase() {
		// Main Phase
		while (currentPlayer.getPhase() == PlayerPhase.MAIN) {
			currentPlayer.executeCommand();
			
			input = reader.getLine().toLowerCase().trim();
			switch (input) {
			// Display
			case "display phase":
				System.out.println(currentPlayer.getPhase());
				break;
				
			case "display library":
				System.out.println(currentPlayer.getLibrarySize());
				break;
				
			case "display waitingroom":
				currentPlayer.displayWaitingRoom();
				break;

			case "display stage":
				currentPlayer.displayStage();
				break;
				
			case "display level":
				currentPlayer.displayLevel();
				break;
				
			case "display stock":
				currentPlayer.displayStock();
				break;
				
			// Always available actions
			case "play character":
				playCharacter();
				break;
				
			case "quit":
				break;
				
			case "draw":
				currentPlayer.draw();
				currentPlayer.displayHand();
				break;

			// Other Actions
			case "discard":
				Card c = currentPlayer.chooseCardFromHand();
					currentPlayer.discard(c);
				break;

			case "shuffle":
				currentPlayer.shuffleLibrary();
				break;

			default:
				System.out.println("Invalid Command");
				break;
			}
			
		}
	}*/

	public void endTurn(Player player) {
		if (player.getPhase() == PlayerPhase.OPPONENTS_TURN) {
			if (currentPlayer == player1.getPlayer()){
				currentPlayer = player2.getPlayer();
			}else if (currentPlayer == player2.getPlayer()){
				currentPlayer = player1.getPlayer();
			}else
				System.out.println("ERROR");
//			currentPlayer.endPhase();
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
