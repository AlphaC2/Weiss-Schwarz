package model.ability.mods;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.card.Card;
import model.card.Character;

public enum ModType {
	COLOUR, POWER, LEVEL, SOUL, TRAIT, COST, ABILITY;
	
	public static Set<ModType> cardTypes(Card card){
		Set<ModType> list = new HashSet<>();
		if (card instanceof Character){
			list.addAll(Arrays.asList(ModType.values()));
			return list;
		}
		list.add(LEVEL);
		list.add(COST);
		list.add(ABILITY);
		return list;
	}
	
}
