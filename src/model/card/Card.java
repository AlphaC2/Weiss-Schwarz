package model.card;

import java.util.ArrayList;
import java.util.List;

import model.ability.Ability;
import model.ability.auto.AutoAbility;
import model.ability.continuous.ContinuousAbility;

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
		return abilities;
	}

	public int getLevel() {
		return level;
	}

	public List<Trigger> getTrigger() {
		List<Trigger> result = new ArrayList<Trigger>(triggers);
		return result;
	}

	public int getCost() {
		return cost;
	}

	public Colour getColour() {
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
		if (!visible){
			return "Face down card";
		}
		
		String abilityText = "";
		for (Ability ability : abilities) {
			abilityText+= ability.toString() + System.lineSeparator() ;
		}
		return "Card [name=" + name + ", cardID=" + cardID + ", imagePath=" + imagePath + ", level=" + level + ", cost="
				+ cost + ", colour=" + colour + ", triggers=" + triggers + ", rarity=" + rarity + ", flavourText="
				+ flavourText + ", visible=" + visible + "]" + System.lineSeparator() + abilityText;
	}

	public String toShortString() {
		if (!visible){
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

}
