package command;



import java.util.List;

import controller.PlayerController;
import model.board.Board;
import model.board.Slot;
import model.board.SlotType;
import model.card.Character;
import model.player.Player;

public class Attack extends Command {

	public Attack() {
		super("Attack");
	}
	
	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		Slot attacking = null;
		Slot defending = null;
		boolean declared;
	
		Player player = p1.getPlayer();
		Board board = p1.getBoard();
		
		// Beginning of Attack Phase
		player.nextStep();
		List<Character> attackingChars = board.getStage().getAttacking();
		while (attackingChars.size() > 0) {
			declared = false;
			while (!declared) {
				// Attack Declaration
				p1.displayStage();
				p2.displayStage();
				boolean attack = p1.getChoice("Declare an attack");
				if (!attack) {
					return;
				}
				
				Character c = p1.getChoice("Choose a character to attack with", attackingChars);
				attacking = board.getSlot(c);
				declared = board.declareAttack(attacking);
			}
			p1.log(player.getPhase());
			SlotType across = SlotType.getAcross(attacking.getSlotType());
			player.nextStep();
			defending = p2.getBoard().getStage().getSlot(across);
			player.nextStep();

			// Trigger
			board.trigger();
			p1.log(player.getPhase());
			player.nextStep();

			// Counter
			p1.log(player.getPhase());
			player.nextStep();

			// Damage
			p1.log(player.getPhase());
			int amount = attacking.getCharacter().getSoul();
			if (defending == null)
				amount++;
			p1.log("Deal " + amount + " damage to opponent");
			for (int i = 0; i <amount; i++) {
				Command c = new TakeDamage();
				c.execute(p2, null);
			}
			player.nextStep();

			// End of Attack
			p1.log(player.getPhase());
			if (defending != null) {
				if (attacking.getCharacter().getCurrentPower() > defending.getCharacter().getCurrentPower()) {
					defending.reverse();
				} else if (attacking.getCharacter().getCurrentPower() < defending.getCharacter().getCurrentPower()) {
					attacking.reverse();
				} else if (attacking.getCharacter().getCurrentPower() == defending.getCharacter().getCurrentPower()) {
					defending.reverse();
					attacking.reverse();
				} else {
					throw new IllegalStateException("End of Attack step, should be unreachable code");
				}
			} else {
				//TODO
			}

			// Attack
		}
	}

}
