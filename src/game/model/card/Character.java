package game.model.card;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Character extends Card implements Cloneable{
	private Set<String> traits;
	private int basePower;
	private int currentPower;
	private int soul;
		
	public Character(String name, String cardID, String imagePath, int level, int cost, Colour colour, List<Trigger> triggers,
			Rarity rarity, String flavourText, String trait1, String trait2, int power, int soul) {
		super(name, cardID, imagePath, level, cost, colour, triggers, rarity, flavourText);
		traits = new HashSet<>();
		traits.add(trait1);
		traits.add(trait2);
		this.basePower = power;
		this.currentPower = power;
		this.soul = soul;
	}

	public List<String> getTraits(){
		return new ArrayList<String>(traits);
	}
	
	public int getPower(boolean base) {
		return basePower;
	}

	public int getPower() {
		return currentPower;
	}
	
	public int getSoul() {
		return soul;
	}
	
	private String traitsString(){
		String result = "";
		for (String trait : traits) {
			result += trait + ", ";
		}
		return result;
	}
	
	@Override
	public String toString() {
		if (!isFaceUp()){
			return "Face down card";
		}
		return super.toString() + " Character [traits=" + traitsString() + " basePower=" + basePower + ", currentPower="
				+ currentPower + ", soul=" + soul + ", abilities=" + getAbilities() + "]";
	}
	
	@Override
	public String toBaseString() {
		if (!isFaceUp()){
			return "Face down card";
		}
		return super.toBaseString() + " Character [traits=" + traitsString() + " basePower=" + basePower + ", currentPower="
		+ currentPower + ", soul=" + soul + ", abilities=" + getAbilities() + "]";
	}
	
	@Override
	public String toShortString(){
		if (!isFaceUp()){
			return "Face down card";
		}
		return super.toShortString() + " Character [traits=" + traitsString() + " currentPower="
				+ currentPower + ", soul=" + soul + "]"; 
	}

}
