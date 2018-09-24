package game.model.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import game.model.ability.Ability;
import game.model.ability.auto.AutoAbility;
import game.model.ability.continuous.ContinuousAbility;
import game.model.ability.mods.*;
import game.model.player.PlayerPhaseTiming;

@SuppressWarnings("rawtypes")
public abstract class Card {
	private String name;
	private String cardID;
	private String imagePath;
	private int level;
	private int cost;
	private Colour colour;
	private List<Trigger> triggers;
	private Rarity rarity;
	private String flavourText;
	private boolean visible;
	private List<Ability> abilities;
	private Map<ModType, List<CardMod>> map;
	
	private void init(){
		map = new HashMap<>();
		abilities = new ArrayList<>();
		for (ModType modType : ModType.cardTypes(this)) {
			map.put(modType, new ArrayList<CardMod>());
		}
	}

	public Card(String name, String cardID, String imagePath, int level, int cost, Colour colour,
			List<Trigger> passedTriggers, Rarity rarity, String flavourText) {
		super();
		init();
		this.name = name;
		this.cardID = cardID;
		this.imagePath = imagePath;
		this.level = level;
		this.cost = cost;
		this.colour = colour;
		triggers = new ArrayList<Trigger>();
		for (Trigger trigger : passedTriggers) {
			triggers.add(trigger);
		}
		this.rarity = rarity;
		this.flavourText = flavourText;
		this.visible = true;
	}

	public final List<Ability> getAbilities() {
		List<Ability> abilities = this.abilities;
		for (CardMod mod : map.get(ModType.ABILITY)) {
			abilities = ((AbilityMod) mod).apply(abilities);
		}
		return abilities;
	}

	public final int getLevel() {
		Integer level = this.level;
		for (CardMod mod : map.get(ModType.LEVEL)) {
			level = ((NumberMod) mod).apply(level);
		}
		return level;
	}

	public final List<Trigger> getTrigger() {
		List<Trigger> result = new ArrayList<Trigger>(triggers);
		return result;
	}

	public final int getCost() {
		Integer cost = this.cost;
		for (CardMod mod : map.get(ModType.COST)) {
			cost = ((NumberMod) mod).apply(cost);
		}
		return cost;
	}

	public final Colour getColour() {
		Colour colour = this.colour;
//		System.out.println("MAP:" + map);
//		System.out.println("COLOUR:" + map.get(ModType.COLOUR));
		for (CardMod mod : map.get(ModType.COLOUR)) {
			colour = ((ColourMod) mod).apply(colour);
		}
		return colour;
	}

	public final String getName() {
		return name;
	}

	public final String getCardID() {
		return cardID;
	}

	public final String getImagePath() {
		return imagePath;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public final String getFlavourText() {
		return flavourText;
	}

	public final void flipFaceDown() {
		visible = false;
	}

	public final void flipFaceUp() {
		visible = true;
	}

	public final boolean isFaceUp() {
		return visible;
	}
	

	@Override
	public String toString() {
		return "Card [name=" + name + ", cardID=" + cardID + ", imagePath=" + imagePath + ", level=" + level + ", cost="
				+ cost + ", colour=" + colour + ", triggers=" + triggers + ", rarity=" + rarity + ", flavourText="
				+ flavourText + ", visible=" + visible + ", abilities=" + abilities + ", map=" + map + "]";
	}

	public String toBaseString() {
		if (!visible) {
			return "Face down card";
		}

		String abilityText = "";
		for (Ability ability : abilities) {
			abilityText += ability.toString() + System.lineSeparator();
		}
		
		return "Card [name=" + name + ", cardID=" + cardID + ", imagePath=" + imagePath + ", level=" + level + ", cost="
				+ cost + ", colour=" + colour + ", triggers=" + triggers + ", rarity=" + rarity + ", flavourText="
				+ flavourText + ", visible=" + visible + "]" + System.lineSeparator() + abilityText;
	}

	public String toShortString() {
		if (!visible) {
			return "Face down card";
		}
		return "Card [name=" + name + ", cardID=" + cardID + ", level=" + level + ", cost=" + cost + ", colour="
				+ colour + "]";
	}

	public final void addAbility(Ability a) {
		abilities.add(a);
	}

	public final List<AutoAbility> getAutoAbilities() {
		List<AutoAbility> list = new ArrayList<>();
		for (Ability ability : getAbilities()) {
			if (ability instanceof AutoAbility) {
				list.add((AutoAbility) ability);
			}
		}
		return list;
	}

	public final List<ContinuousAbility> getContinuousAbilities() {
		List<ContinuousAbility> list = new ArrayList<>();
		for (Ability ability : getAbilities()) {
			if (ability instanceof ContinuousAbility) {
				list.add((ContinuousAbility) ability);
			}
		}
		return list;
	}

	public final void addMod(List<CardMod> newMods) {
		for (CardMod mod : newMods) {
			map.get(mod.getType()).add(mod);
		}
	}

	public final void removeExpiredMods(PlayerPhaseTiming pt) {
		
		for (Map.Entry<ModType, List<CardMod>> entry : map.entrySet()) {
			Iterator<CardMod> ite = entry.getValue().iterator();
			while (ite.hasNext()) {
				CardMod mod = ite.next();
				if (mod.isExpired(pt)) {
					System.out.println("Removing " + mod.getType() +" mod on " + pt);
					ite.remove();
				}
			}
		}
	}
}
