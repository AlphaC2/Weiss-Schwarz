package game.model.card;

import java.util.ArrayList;
import java.util.List;

public class Character extends Card {
	private List<String> traits;
	private int basePower;
	private int currentPower;
	private int soul;
		
	public Character(String name, String cardID, String imagePath, int level, int cost, Colour colour, List<Trigger> triggers,
			Rarity rarity, String flavourText, String trait1, String trait2, int power, int soul) {
		super(name, cardID, imagePath, level, cost, colour, triggers, rarity, flavourText);
		traits = new ArrayList<>();
		traits.add(trait1);
		traits.add(trait2);
		this.basePower = power;
		this.currentPower = power;
		this.soul = soul;
	}

	public List<String> getTraits(){
		return new ArrayList<String>(traits);
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
	
	@Override
	public String toString() {
		if (!isFaceUp()){
			return "Face down card";
		}
		return super.toString() + " Character [traits=" + traitsString() + " basePower=" + basePower + ", currentPower="
				+ currentPower + ", soul=" + soul + ", abilities=" + getAbilities() + "]";
	}
	
	private String traitsString(){
		String result = "";
		for (String trait : traits) {
			result += trait + ", ";
		}
		return result;
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
