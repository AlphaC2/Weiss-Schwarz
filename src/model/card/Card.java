package model.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.ability.Ability;
import model.ability.auto.AutoAbility;
import model.ability.continuous.ContinuousAbility;
import model.ability.continuous.mods.*;
import model.player.PlayerPhaseTiming;

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
	private Map<ModType, List<CardMod>> map = new HashMap<>();

	public Card(String name, String cardID, String imagePath, int level, int cost, Colour colour,
			List<Trigger> passedTriggers, Rarity rarity, String flavourText) {
		super();
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
		abilities = new ArrayList<>();
	}

	public List<Ability> getAbilities() {
		List<Ability> abilities = this.abilities;
		for (CardMod mod : map.get(ModType.ABILITY)) {
			abilities = ((AbilityMod) mod).apply(abilities);
		}
		return abilities;
	}

	public int getLevel() {
		Integer level = this.level;
		for (CardMod mod : map.get(ModType.LEVEL)) {
			level = ((NumberMod) mod).apply(level);
		}
		return level;
	}

	public List<Trigger> getTrigger() {
		List<Trigger> result = new ArrayList<Trigger>(triggers);
		return result;
	}

	public int getCost() {
		Integer cost = this.cost;
		for (CardMod mod : map.get(ModType.COST)) {
			cost = ((NumberMod) mod).apply(cost);
		}
		return cost;
	}

	public Colour getColour() {
		Colour colour = this.colour;
		for (CardMod mod : map.get(ModType.COLOUR)) {
			colour = ((ColourMod) mod).apply(colour);
		}
		return colour;
	}

	public String getName() {
		return name;
	}

	public String getCardID() {
		return cardID;
	}

	public String getImagePath() {
		return imagePath;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public String getFlavourText() {
		return flavourText;
	}

	public void flipFaceDown() {
		visible = false;
	}

	public void flipFaceUp() {
		visible = true;
	}

	public boolean isFaceUp() {
		return visible;
	}

	@Override
	public String toString() {
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

	public void addAbility(Ability a) {
		abilities.add(a);
	}

	public List<AutoAbility> getAutoAbilities() {
		List<AutoAbility> list = new ArrayList<>();
		for (Ability ability : getAbilities()) {
			if (ability instanceof AutoAbility) {
				list.add((AutoAbility) ability);
			}
		}
		return list;
	}

	public List<ContinuousAbility> getContinuousAbilities() {
		List<ContinuousAbility> list = new ArrayList<>();
		for (Ability ability : getAbilities()) {
			if (ability instanceof ContinuousAbility) {
				list.add((ContinuousAbility) ability);
			}
		}
		return list;
	}

	public void addMod(List<CardMod> newMods) {
		for (CardMod mod : newMods) {
			List<CardMod> list = null;
			if (!map.containsKey(mod.getType())){
				list = new ArrayList<>();
				map.put(mod.getType(), list);
			}
			list.add(mod);
		}
	}

	public void removeExpiredMods(PlayerPhaseTiming pt) {
		for (Map.Entry<ModType, List<CardMod>> entry : map.entrySet()) {
			Iterator<CardMod> ite = entry.getValue().iterator();
			while (ite.hasNext()) {
				CardMod mod = ite.next();
				if (mod.isExpired(pt)) {
					ite.remove();
				}
			}
		}
	}
}
