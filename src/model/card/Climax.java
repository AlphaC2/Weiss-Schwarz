package model.card;

import java.util.List;

import model.card.ability.Ability;

public class Climax extends Card implements Activatable{
	private Ability ability;
	
	public Climax(String name, String cardID, String imagePath, Colour colour, List<Trigger> triggers,
			Rarity rarity, String flavourText, Ability ability) {
		super(name, cardID, imagePath, 0, 0, colour, triggers, rarity, flavourText);
		this.ability = ability;
	}

	@Override
	public String toString() {
		return super.toString() + " Climax [ability=" + ability + "]";
	}

	@Override
	public void activate() {
		ability.activate();
	}
	
	

}
