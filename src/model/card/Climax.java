package model.card;

import java.util.List;

import controller.PlayerController;
import model.ability.Activatable;
import model.ability.action.Action;

public class Climax extends Card implements Activatable{
	private Action ability;
	
	public Climax(String name, String cardID, String imagePath, Colour colour, List<Trigger> triggers,
			Rarity rarity, String flavourText, Action ability) {
		super(name, cardID, imagePath, 0, 0, colour, triggers, rarity, flavourText);
		this.ability = ability;
	}

	@Override
	public String toString() {
		return super.toString() + " Climax [ability=" + ability + "]";
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		ability.execute(p1, p2);
	}

}
