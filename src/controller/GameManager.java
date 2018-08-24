package controller;

import io.ReadUserInput;
import model.board.PlayerPhase;
import model.player.Player;

public class GameManager {
	ReadUserInput reader;
	Player player1;
	Player player2;
	Player currentPlayer;
	boolean alive = true;

	public GameManager(Player p1, Player p2) {
		super();
		reader = new ReadUserInput();
		player1 = p1;
		player2 = p2;
		currentPlayer = player1;
	}

	public void gameLoop() {
		player1.shuffleLibrary();
		player2.shuffleLibrary();
		player1.endPhase();
		player1.draw(4);
		player2.draw(5);
		
		while (alive) {
			System.out.println(currentPlayer.getName() + ":"+ currentPlayer.getPhase() + "Phase");
			switch (currentPlayer.getPhase()) {
			case STAND:
				currentPlayer.getBoard().standAll();

			case DRAW:
				currentPlayer.draw();
				break;

			case CLOCK:
				clock();
				break;

			case MAIN:
				mainPhase();
				break;
				
			case CLIMAX:
				//System.out.println("CLIMAX");
				climax();
				break;
				
			case ATTACK:
				break;
				
			case ENCORE:
				break;
				
			case END:
				break;
				
			default:
				//System.out.println("Phase:" + currentPlayer.getPhase());
				
				break;
			}
			endPhase();
		}
	}

	private void mainPhase() {
		String input; 
		// Main Phase
		while(true){
			input = reader.getLine().toLowerCase().trim();
			switch (input) {
			// Display
			case "display hand":
				currentPlayer.displayHand();
				break;
			case "display phase":
				System.out.println(currentPlayer.getPhase());
				break;
			case "display library":
				System.out.println(currentPlayer.getLibrarySize());
				break;
			case "display waitingroom":
				System.out.println(currentPlayer.getWaitingRoomSize());
				break;
			case "display damage":
				currentPlayer.displayDamage();
				break;
				
			// Always available actions
			case "end phase":
				endPhase();
				System.out.println(currentPlayer.getPhase());
				return;
			case "quit":
				break;
	
			// Phase Specific Actions
			case "draw":
				currentPlayer.draw();
				currentPlayer.displayHand();
				break;
	
			// Other Actions
			case "discard":
				int index = chooseCardFromHand();
				if (index != -1) {
					currentPlayer.discard(index);
				}
				break;
	
			case "shuffle":
				currentPlayer.shuffleLibrary();
				break;
	
			default:
				System.out.println("Invalid Command");
				break;
			}
		}
	}

	private void climax() {
		currentPlayer.displayHand();
		System.out.println("Play a Climax? (Y/N)");
		String input = reader.getLine();
		if (input.trim().toLowerCase().equals("y")) {
			int index = chooseCardFromHand();
			if (index != -1) {
				currentPlayer.playClimax(index);
				
				return;
			}
		}

	}

	private void clock() {
		currentPlayer.displayHand();
		System.out.println("Clock? (Y/N)");
		String input = reader.getLine();
		if (input.trim().toLowerCase().equals("y")) {
			int index = chooseCardFromHand();
			if (index != -1) {
				currentPlayer.discard(index);
				currentPlayer.draw(2);
				return;
			}
		}
	}

	private void endPhase() {
		currentPlayer.endPhase();
		if (currentPlayer.getPhase() == PlayerPhase.OPPONENTS_TURN) {
			if (currentPlayer == player1)
				currentPlayer = player2;
			else if (currentPlayer == player2)
				currentPlayer = player1;
			else
				System.out.println("ERROR");
			currentPlayer.endPhase();
		}
	}

	private int chooseCardFromHand() {
		currentPlayer.displayHand();
		System.out.println("Which card?");
		int i = reader.getInt();
		//System.out.println("IPNUT:" + i);
		if (i >= 0 && i < currentPlayer.getHandSize()) {
			return i;
		} else {
			return -1;
			// System.out.println("Invalid Choice");
		}
	}

}
