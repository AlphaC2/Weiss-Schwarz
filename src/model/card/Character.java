package model.card;

import java.util.List;

import model.card.ability.Ability;

public final class Character extends Card {
	private String trait1;
	private String trait2;
	private int basePower;
	private int currentPower;
	private int soul;
	private List<Ability> abilities;
		
	public Character(String name, String cardID, String imagePath, int level, int cost, Colour colour, List<Trigger> triggers,
			Rarity rarity, String flavourText, String trait1, String trait2, int power, int soul,	List<Ability> abilities) {
		super(name, cardID, imagePath, level, cost, colour, triggers, rarity, flavourText);
		this.trait1 = trait1;
		this.trait2 = trait2;
		this.basePower = power;
		this.currentPower = power;
		this.soul = soul;
		this.abilities = abilities;
	}

	public String getTrait1() {
		return trait1;
	}

	public String getTrait2() {
		return trait2;
	}

	public int getBasePower() {
		return basePower;
	}

	public int getCurrentPower() {
		return currentPower;
	}
	
	protected void setCurrentPower(int newPower){
		this.currentPower = newPower;
	}

	protected void changePower(int change){
		this.currentPower += change;
	}
	
	public int getSoul() {
		return soul;
	}

	public List<Ability> getAbilities() {
		return abilities;
	}
	
	public Ability getAbility(int i) {
		return abilities.get(i);
	}
	
	@Override
	public String toString() {
		return super.toString() + " Character [trait1=" + trait1 + ", trait2=" + trait2 + ", basePower=" + basePower + ", currentPower="
				+ currentPower + ", soul=" + soul + ", abilities=" + abilities + "]";
	}


}
