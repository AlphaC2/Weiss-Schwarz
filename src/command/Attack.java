package command;

import java.util.List;

import controller.PlayerController;
import model.board.Board;
import model.board.SlotType;
import model.card.Character;
import model.player.Player;

public class Attack extends Command {

	public Attack() {
		super("Attack");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		SlotType s = null;
		SlotType across = null;
		Character attacking = null;
		Character defending = null;
		boolean declared;
	
		Player player = p1.getPlayer();
		Board board = player.getBoard();
		
		// Beginning of Attack Phase
		player.nextStep();
		while (board.getStanding().size() > 0) {
			declared = false;
			while (!declared) {
				// Attack Declaration
				p1.displayStage();
				p2.displayStage();
				boolean attack = p1.getChoice("Declare an attack");
				if (!attack) {
					return;
				}
				
				Character c = p1.getChoice("Choose a character to attack with", board.getStanding());
				s = board.getSlot(c).getSlotType();
				declared = player.declareAttack(s);
			}
			p1.log(player.getPhase());
			across = SlotType.getAcross(s);
			player.nextStep();
			attacking = player.getCharacter(s);
			defending = p2.getPlayer().getCharacter(across);
			player.nextStep();

			// Trigger
			player.trigger();
			p1.log(player.getPhase());
			player.nextStep();

			// Counter
			p1.log(player.getPhase());
			player.nextStep();

			// Damage
			p1.log(player.getPhase());
			int amount = player.getSoul(s);
			if (defending == null)
				amount++;
			p1.log("Deal " + amount + " damage to opponent");
			p2.getPlayer().takeDamage(amount);
			player.nextStep();

			// End of Attack
			p1.log(player.getPhase());
			if (defending != null) {
				if (attacking.getCurrentPower() > defending.getCurrentPower()) {
					defending.reverse();
				} else if (attacking.getCurrentPower() < defending.getCurrentPower()) {
					attacking.reverse();
				} else if (attacking.getCurrentPower() == defending.getCurrentPower()) {
					defending.reverse();
					attacking.reverse();
				} else {
					throw new IllegalStateException("End of Attack step, should be unreachable code");
				}
			}

			// Attack
		}
		player.endPhase();
	}

}
