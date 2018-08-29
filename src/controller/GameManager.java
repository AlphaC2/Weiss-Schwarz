package controller;

import io.ConsoleReadUserInput;
import model.card.Card;
import model.card.Character;
import model.board.Slot;
import model.player.Player;
import model.player.PlayerPhase;


public class GameManager {
	ConsoleReadUserInput reader;
	Player player1;
	Player player2;
	Player currentPlayer;
	Player currentOpponent;
	boolean alive = true;

	public GameManager(PlayerController p1, PlayerController p2) {
		super();
		reader = new ConsoleReadUserInput();
		player1 = p1.getPlayer();
		player2 = p2.getPlayer();
		currentPlayer = player1;
		currentOpponent = player2;
		player1.setOpponent(player2);
		player2.setOpponent(player1);
	}

	public void gameLoop() {
		player1.shuffleLibrary();
		player2.shuffleLibrary();
		player1.endPhase();
		player1.draw(4);
		player2.draw(5);

		while (alive) {
			System.out.println(currentPlayer.getName() + ":" + currentPlayer.getPhase() + "Phase");
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
				climax();
				break;

			case ATTACK:
				currentPlayer.attack();
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
		}
	}

	private void encore() {
		currentPlayer.encore();
		currentOpponent.encore();
	}

	private void mainPhase() {
		// Main Phase
		while (true) {
			currentPlayer.executeCommand();
			
			/*input = reader.getLine().toLowerCase().trim();
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
				currentPlayer.displayWaitingRoom();
				break;
				
			case "display damage":
				currentPlayer.displayDamage();
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
				
			case "end phase":
				return;
				
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
			*/
		}
	}

	

	private void playCharacter() {
		Card card = currentPlayer.chooseCardFromHand();
		Slot slot = currentPlayer.chooseSlot();
		currentPlayer.playCharacter(card, slot);
	}

	private void climax() {
		currentPlayer.playClimax();
	}

	private void endPhase() {
		currentPlayer.endPhase();
		if (currentPlayer.getPhase() == PlayerPhase.OPPONENTS_TURN) {
			if (currentPlayer == player1){
				currentPlayer = player2;
				currentOpponent = player1;
			}else if (currentPlayer == player2){
				currentPlayer = player1;
				currentOpponent = player2;
			}else
				System.out.println("ERROR");
			currentPlayer.endPhase();
			System.out.println("\n\n");
		}
	}

}
