package game.command;

import java.util.ArrayList;
import java.util.List;

import game.controller.PlayerController;
import game.model.ability.action.concrete.TakeDamage;
import game.model.ability.action.concrete.TriggerAction;
import game.model.board.AttackType;
import game.model.board.Board;
import game.model.board.Slot;
import game.model.board.SlotType;
import game.model.card.Character;
import game.model.player.Player;

public class AttackPhase extends Command {

	public AttackPhase() {
		super("Attack");
	}

	Slot attacking = null;
	Slot defending = null;
	Player player;
	Board board;
	AttackType attackType;
	List<Character> attackingChars;

	private void chooseAttacker(PlayerController p1, PlayerController p2) {
		Player player = p1.getPlayer();
		Board board = p1.getBoard();

		// Attack Declaration
		p1.log(player.getPhase());

		p1.displayStage();
		p2.displayStage();
		boolean attack = p1.getChoice("Declare an attack?");
		if (!attack) {
			attacking = null;
			return;
		}

		Character c = p1.getChoice("Choose a character to attack with", attackingChars);
		attacking = board.getStage().getSlot(c);
		attacking.rest();
		SlotType across = SlotType.getAcross(attacking.getSlotType());
		defending = p2.getBoard().getStage().getSlot(across);
		if (defending.getCharacter() == null) {
			attackType = AttackType.DIRECT_ATTACK;
		} else {
			List<AttackType> choices = new ArrayList<>();
			choices.add(AttackType.FRONT_ATTACK);
			choices.add(AttackType.SIDE_ATTACK);
			attackType = p1.getChoice("Front or Side attack?", choices);
		}
		player.nextStep();

	}
	
	private void damage(PlayerController p1, PlayerController p2){
		// Damage
		p1.log(player.getPhase());
		int amount = attacking.getCharacter().getSoul();
		if (attackType == AttackType.SIDE_ATTACK) {
			amount -= defending.getCharacter().getLevel();
			amount = Math.max(amount, 0);
		} else if (attackType == AttackType.DIRECT_ATTACK) {
			amount++;
		}
		p1.log("Deal " + amount + " damage to opponent");
		new TakeDamage(amount).execute(p2, p1);
		player.nextStep();

		// End of Attack
		p1.log(player.getPhase());
		if (attackType == AttackType.FRONT_ATTACK) {
			if (attacking.getCharacter().getPower() > defending.getCharacter().getPower()) {
				defending.reverse();
			} else if (attacking.getCharacter().getPower() < defending.getCharacter().getPower()) {
				attacking.reverse();
			} else if (attacking.getCharacter().getPower() == defending.getCharacter().getPower()) {
				defending.reverse();
				attacking.reverse();
			} else {
				throw new IllegalStateException("End of Attack step, should be unreachable code");
			}
		}
		player.nextStep();
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		player = p1.getPlayer();
		board = p1.getBoard();
		attackingChars = board.getStage().getAttacking();
		// Beginning of Attack Phase
		p1.log(player.getPhase());
		player.nextStep();
		
		while (attackingChars.size() > 0 && p2.isAlive()) {
			chooseAttacker(p1,p2);
			if (attacking == null){
				return;
			}
			// Trigger
			new TriggerAction(attacking).execute(p1, p2);
			// Counter
			p1.log(player.getPhase());
			player.nextStep();
			// damage
			damage(p1,p2);
			attackingChars = board.getStage().getAttacking();
		}
	}
}
