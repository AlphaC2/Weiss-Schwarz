package model.card;

import java.util.List;

import controller.PlayerController;
import model.ability.Ability;
import model.ability.Activatable;

public class Climax extends Card implements Activatable{
	
	public Climax(String name, String cardID, String imagePath, Colour colour, List<Trigger> triggers,
			Rarity rarity, String flavourText, Ability ability) {
		super(name, cardID, imagePath, 0, 0, colour, triggers, rarity, flavourText);
	}

	@Override
	public String toShortString() {
		return super.toShortString() + " Climax [ability=" + getAbilities().get(0) + "]";
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2)  {
		getAbilities().get(0).execute(p1, p2);
	}

}
