package controller;

import io.ReadUserInput;
import model.board.Slot;
import model.player.Player;
import model.player.PlayerPhase;

public class GameManager {
	ReadUserInput reader;
	Player player1;
	Player player2;
	Player currentPlayer;
	Player currentOpponent;
	boolean alive = true;

	public GameManager(Player p1, Player p2) {
		super();
		reader = new ReadUserInput();
		player1 = p1;
		player2 = p2;
		currentPlayer = player1;
		currentOpponent = player2;
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
				clock();
				break;

			case MAIN:
				mainPhase();
				break;

			case CLIMAX:
				climax();
				break;

			case ATTACK:
				attack();
				break;

			case ENCORE:
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

	private void mainPhase() {
		String input;
		// Main Phase
		while (true) {
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
				currentPlayer.displayWaitingRoom();
				break;
				
			case "display damage":
				currentPlayer.displayDamage();
				break;
			
			case "display stage":
				currentPlayer.displayStage();
				break;
				
			// Always available actions
			case "play character":
				playCharacter();
				break;
				
			case "end phase":
				return;
				
			case "quit":
				break;

			// Phase Specific Actions
			case "attack":
				attack();
				break;
				
			case "draw":
				currentPlayer.draw();
				currentPlayer.displayHand();
				break;

			// Other Actions
			case "discard":
				int index = currentPlayer.chooseCardFromHand();
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

	private void attack() {
		String input;
		Slot s = null;
		Boolean declared;
		//Beginning of Attack Phase
		currentPlayer.nextStep();
		while(true){
			declared = false;
			while(!declared){
				//Attack Declaration
				currentPlayer.displayStage();
				System.out.println("Declare an attack? (Y/N)");
				input = reader.getLine().toLowerCase().trim();
				if (!input.trim().toLowerCase().equals("y")){
					return;
				}
				System.out.println("Chose a character to attack with");
				s = Slot.getName(reader.getInt());
				declared = currentPlayer.declareAttack(s);
			}
			System.out.println(currentPlayer.getPhase());
			currentPlayer.nextStep();

			//Trigger
			currentPlayer.trigger();
			System.out.println(currentPlayer.getPhase());
			currentPlayer.nextStep();

			//Counter
			System.out.println(currentPlayer.getPhase());
			currentPlayer.nextStep();

			//Damage
			System.out.println(currentPlayer.getPhase());
			int amount = currentPlayer.getSoul(s);
			System.out.println("Deal " + amount + " damage to opponent");
			currentOpponent.takeDamage(	amount );
			currentPlayer.nextStep();

			//End of Attack
			System.out.println(currentPlayer.getPhase());
			currentPlayer.nextStep();

			//Attack
		}
	}

	private void playCharacter() {
		int cardIndex = currentPlayer.chooseCardFromHand();
		Slot slot = currentPlayer.chooseSlot();
		currentPlayer.playCharacter(cardIndex, slot);
	}

	private void climax() {
		currentPlayer.displayHand();
		System.out.println("Play a Climax? (Y/N)");
		String input = reader.getLine();
		if (input.trim().toLowerCase().equals("y")) {
			int index = currentPlayer.chooseCardFromHand();
			currentPlayer.playClimax(index);
			return;
		}
	}

	private void clock() {
		currentPlayer.displayHand();
		System.out.println("Clock? (Y/N)");
		String input = reader.getLine();
		if (input.trim().toLowerCase().equals("y")) {
			int index = currentPlayer.chooseCardFromHand();
			currentPlayer.clock(index);
			currentPlayer.draw(2);
			return;
		}
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
