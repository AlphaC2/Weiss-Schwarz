package game.model.ability.mods;

import java.util.ArrayList;
import java.util.List;

import game.model.ability.Ability;

public class AbilityMod extends CardMod<List<Ability>>{

	private Ability ability;
	
	public AbilityMod(Ability ability) {
		super(ModType.ABILITY);
		this.ability = ability;
	}

	@Override
	public List<Ability> apply(List<Ability> base) {
		if (!isActive()){
			return base;
		}
		List<Ability> newList = new ArrayList<Ability>(base);
		newList.add(ability);
		return newList;
	}


}
