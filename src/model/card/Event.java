package model.card;

import java.util.List;

import model.card.ability.Ability;
import model.card.ability.Activatable;

public class Event extends Card implements Activatable{
	private Ability ability;

	public Event(String name, String cardID, String imagePath, int level, int cost, Colour colour, List<Trigger> triggers,
			Rarity rarity, String flavourText, Ability ability) {
		super(name, cardID, imagePath, level, cost, colour, triggers, rarity, flavourText);
		this.ability = ability;
	}

	@Override
	public void activate() {
		ability.activate();
	}
	

}
