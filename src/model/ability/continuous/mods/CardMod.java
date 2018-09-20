package model.ability.continuous.mods;

import model.ability.continuous.ContinuousAbility;
import model.player.PlayerPhaseTiming;

public abstract class CardMod<T> implements ICardMod<T>{
	private ModType type;
	private ContinuousAbility ability;
	private PlayerPhaseTiming phaseTiming;
	
	CardMod(ModType type){
		this.type = type;
	}
	
	public void setExpiration(PlayerPhaseTiming pt){
		this.phaseTiming = pt;
	}
	
	public boolean isExpired(PlayerPhaseTiming e){
		return phaseTiming.compareTo(e) >= 0;
	}
	
	public void setAbility(ContinuousAbility ability) {
		this.ability = ability;
	}

	protected boolean isActive(){
		return ability.isEnabled();
	}
	
	public ModType getType(){
		return type;
	}
}
