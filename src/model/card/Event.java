package model.card;

import java.util.List;

import controller.PlayerController;
import model.ability.Ability;
import model.ability.Activatable;

public class Event extends Card implements Activatable{

	public Event(String name, String cardID, String imagePath, int level, int cost, Colour colour, List<Trigger> triggers,
			Rarity rarity, String flavourText, Ability ability) {
		super(name, cardID, imagePath, level, cost, colour, triggers, rarity, flavourText);
	}
	
	@Override
	public String toShortString() {
		return super.toShortString() + " Event [ability=" + getAbilities().get(0) + "]";
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2)  {
		getAbilities().get(0).execute(p1, p2);
	}
	

}
