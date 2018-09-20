package model.ability.continuous;

import java.util.List;

import controller.PlayerController;
import model.ability.Ability;
import model.ability.action.condition.Condition;
import model.card.Card;

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
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	
	@Override
	public boolean canActivate() {
		for (Condition condition : conditions) {
			if (!condition.check()){
				return false;
			}
		}
		return true;
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		if (canActivate()) {
			
		}
	}

	@Override
	public void setTargets(PlayerController p1, PlayerController p2) {
		// TODO Auto-generated method stub
		
	}
}
