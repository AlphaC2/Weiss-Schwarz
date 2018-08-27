package model.card;

import java.util.ArrayList;
import java.util.List;

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
	
	public Card(String name, String cardID, String imagePath, int level, int cost, Colour colour, List<Trigger> passedTriggers,
			Rarity rarity, String flavourText) {
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
	
	public void flipFaceDown(){
		visible = false;
	}
	
	public void flipFaceUp(){
		visible = true;
	}
	
	public boolean isFaceUp(){
		return visible;
	}

	@Override
	public String toString() {
		return "Card [name=" + name + ", cardID=" + cardID + ", imagePath=" + imagePath + ", level=" + level + ", cost="
				+ cost + ", colour=" + colour + ", triggers=" + triggers + ", rarity=" + rarity + ", flavourText="
				+ flavourText + ", visible=" + visible + "]";
	}
	
	public String toShortString(){
		return "Card [name=" + name + ", cardID=" + cardID + "]";
	}
	
}
