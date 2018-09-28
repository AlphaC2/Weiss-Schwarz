package game.model.ability.continuous;

import java.util.List;

import game.controller.PlayerController;
import game.model.ability.Ability;
import game.model.ability.action.condition.Condition;
import game.model.card.Card;

@SuppressWarnings("rawtypes")
public class ContinuousAbility extends Ability{
	private boolean enabled = false;
	private List<Condition> conditions;
	
	protected ContinuousAbility(Card source) {
		super(source);
	}
	
	protected ContinuousAbility(Card source, boolean self) {
		super(source,self);
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	
	@Override
	public boolean canActivate() {
		for (Condition condition : conditions) {
			if (!condition.check()){
				enabled = false;
				return enabled;
			}
		}
		enabled = true;
		return enabled;
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		if (canActivate()) {
			
		}
	}

	@Override
	public void setTargets(PlayerController p1, PlayerController p2) {
		for (Condition condition : conditions) {

		}
	}
}
