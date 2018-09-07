package model.card;

import java.util.List;

import controller.PlayerController;
import model.ability.Activatable;
import model.ability.action.Action;

public class Event extends Card implements Activatable{
	private Action ability;

	public Event(String name, String cardID, String imagePath, int level, int cost, Colour colour, List<Trigger> triggers,
			Rarity rarity, String flavourText, Action ability) {
		super(name, cardID, imagePath, level, cost, colour, triggers, rarity, flavourText);
		this.ability = ability;
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		ability.execute(p1, p2);
	}
	

}
