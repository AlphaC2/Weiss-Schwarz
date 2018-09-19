package model.ability.continuous;

import java.util.List;

import controller.PlayerController;
import model.ability.ConditionalAbility;
import model.ability.condition.Condition;
import model.ability.continuous.mods.CardMod;
import model.card.Card;

@SuppressWarnings("rawtypes")
public class ContinuousAbility extends ConditionalAbility{

	private List<Condition> conditions;
	private List<CardMod> mods;
	private boolean enabled = false;
	
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
			if ( !condition.check()){
				return false;
			}
		}
		return super.canActivate();
	}
	
	@Override
	public void setTargets(PlayerController p1, PlayerController p2){
		
	}
	
	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		if (canActivate()) {
			for (CardMod mod : mods) {
				
			}
		}
	}

}
