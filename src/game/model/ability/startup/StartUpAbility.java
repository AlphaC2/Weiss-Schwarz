package game.model.ability.startup;

import game.controller.PlayerController;
import game.model.ability.Ability;
import game.model.ability.AbilityInterface;
import game.model.ability.action.Action;
import game.model.card.Card;

public abstract class StartUpAbility extends Ability {

	private int max = Integer.MAX_VALUE;
	protected int used = 0;
	private AbilityInterface cost;

	protected StartUpAbility(Card source) {
		super(source);
	}

	protected StartUpAbility(Card source, boolean self) {
		super(source, self);
	}

	public void reset() {
		used = 0;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getUsed() {
		return used;
	}
	
	@SuppressWarnings("rawtypes")
	public void setTargets(PlayerController p1, PlayerController p2){
		Action  current = (Action) cost;
		while(current != null){
			current.setValidTargets(p1, p2);
			current = (Action) current.next();
		}
		
		for (AbilityInterface ability : actions) {
			Action action = (Action) ability;
			action.setValidTargets(p1, p2);
		}
	}

	public void addCost(AbilityInterface newCost) {
		if (cost == null) {
			cost = newCost;
		} else {
			cost.last().setNextAction(newCost);
		}
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		if (canActivate()) {
			if (cost != null) {
				cost.execute(p1, p2);
			}
			for (AbilityInterface action : actions) {
				if (action.isRequired() || p1.getChoice("Activate?")){
					action.execute(p1, p2);
				} 
			}
			used++;
		}
	}

	private boolean checkChain(AbilityInterface chain) {
		while (chain != null) {
			if (!chain.canActivate()) {
				return false;
			}
			chain = chain.next();
		}
		return true;
	}

	@Override
	public boolean canActivate() {
		return checkChain(cost) && used < max;
	}

}
