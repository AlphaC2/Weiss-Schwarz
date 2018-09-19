package model.ability.continuous;

import java.util.List;

import model.ability.ConditionalAbility;
import model.ability.condition.Condition;
import model.card.Card;
import model.player.PlayerPhase;

@SuppressWarnings("rawtypes")
public class ContinuousAbility extends ConditionalAbility{

	private PlayerPhase expirePhase;
	private List<Condition> conditions;
	private boolean enabled = false;
	
	protected ContinuousAbility(Card source) {
		super(source);
	}
	
	protected ContinuousAbility(Card source, boolean self) {
		super(source,self);
	}

	public PlayerPhase getExpirePhase() {
		return expirePhase;
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
			if ( !condition.check()){
				return false;
			}
		}
		return super.canActivate();
	}

}
