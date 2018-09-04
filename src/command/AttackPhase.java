package command;



import java.util.List;

import controller.PlayerController;
import model.board.Board;
import model.board.Slot;
import model.board.SlotType;
import model.card.Character;
import model.player.Player;

public class AttackPhase extends Command {

	public AttackPhase() {
		super("Attack");
	}
	
	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		Slot attacking = null;
		Slot defending = null;
		boolean declared;
	
		Player player = p1.getPlayer();
		Board board = p1.getBoard();
		
		List<Character> attackingChars = board.getStage().getAttacking();
		player.nextStep();
		
		while (attackingChars.size() > 0) {
			declared = false;

			while (!declared) {
				// Attack Declaration
				p1.log(player.getPhase());
				
				p1.displayStage();
				p2.displayStage();
				boolean attack = p1.getChoice("Declare an attack?");
				if (!attack) {
					return;
				}
				
				Character c = p1.getChoice("Choose a character to attack with", attackingChars);
				attacking = board.getStage().getSlot(c);
				
				declared = board.declareAttack(attacking);
				
			}
			player.nextStep();
			// Beginning of Attack Phase
			p1.log(player.getPhase());
			SlotType across = SlotType.getAcross(attacking.getSlotType());
			defending = p2.getBoard().getStage().getSlot(across);

			// Trigger
			p1.log(player.getPhase());
			board.trigger();
			player.nextStep();

			// Counter
			p1.log(player.getPhase());
			player.nextStep();
			
			// Damage
			p1.log(player.getPhase());
			int amount = attacking.getCharacter().getSoul();
			if (defending.getCharacter() == null)
				amount++;
			p1.log("Deal " + amount + " damage to opponent");
			new TakeDamage(amount).execute(p2, null);
			player.nextStep();

			// End of Attack
			p1.log(player.getPhase());
			if (defending.getCharacter() != null) {
				//Front Attack
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
			} 
			player.nextStep();
			
			attackingChars = board.getStage().getAttacking();
		}
		player.endPhase();
	}

}
