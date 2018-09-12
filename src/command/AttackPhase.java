package command;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.ability.action.TakeDamage;
import model.board.AttackType;
import model.board.Board;
import model.board.Slot;
import model.board.SlotType;
import model.card.Card;
import model.card.Character;
import model.card.Trigger;
import model.exceptions.EmptyLibraryException;
import model.player.Player;

public class AttackPhase extends Command {

	public AttackPhase() {
		super("Attack");
	}
	
	@Override
	public void execute(PlayerController p1, PlayerController p2)  {
		Slot attacking = null;
		Slot defending = null;
	
		Player player = p1.getPlayer();
		Board board = p1.getBoard();
		
		List<Character> attackingChars = board.getStage().getAttacking();
		// Beginning of Attack Phase
		p1.log(player.getPhase());
		player.nextStep();
		
		while (attackingChars.size() > 0) {
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
			attacking.rest();
			SlotType across = SlotType.getAcross(attacking.getSlotType());
			defending = p2.getBoard().getStage().getSlot(across);
			AttackType attackType;
			if (defending.getCharacter() == null){
				attackType = AttackType.DIRECT_ATTACK;
			}else{
				List<AttackType> choices = new ArrayList<>();
				choices.add(AttackType.FRONT_ATTACK);
				choices.add(AttackType.SIDE_ATTACK);
				attackType = p1.getChoice("Front or Side attack?", choices);
			}
			player.nextStep();

			// Trigger
			p1.log(player.getPhase());
			Card trigger = null;
			try {
				trigger = board.getLibrary().draw();
			} catch (EmptyLibraryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Triggerd:" + trigger);
			List<Trigger> triggers = trigger.getTrigger();
			for (Trigger t : triggers) {
				//TODO
			}
			board.getStock().add(trigger);
			player.nextStep();

			// Counter
			p1.log(player.getPhase());
			player.nextStep();
			
			// Damage
			p1.log(player.getPhase());
			int amount = attacking.getCharacter().getSoul();
			if (attackType == AttackType.SIDE_ATTACK){
				amount -= defending.getCharacter().getLevel();
				amount = Math.max(amount, 0);
			}else if (attackType == AttackType.DIRECT_ATTACK){
				amount++;
			}
			p1.log("Deal " + amount + " damage to opponent");
			new TakeDamage(amount).execute(p2, p1);
			player.nextStep();

			// End of Attack
			p1.log(player.getPhase());
			if (attackType == AttackType.FRONT_ATTACK) {
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
	}

}
