package game.model.ability.action;

import java.util.ArrayList;
import java.util.List;

import game.controller.PlayerController;
import game.model.ability.continuous.ContinuousAbility;
import game.model.ability.mods.CardMod;
import game.model.card.Card;
import game.model.player.PlayerPhaseTiming;

@SuppressWarnings("rawtypes")
public class GiveModToHand extends Action<Card>{

	private List<CardMod> mods;

	public GiveModToHand(CardMod mod, ContinuousAbility ability) {
		super("Give mod to hand");
		mod.setAbility(ability);
		mods = new ArrayList<>();
		mods.add(mod);
	}
	
	public GiveModToHand(CardMod mod, PlayerPhaseTiming pt) {
		super("Give mod to hand");
		mod.setExpiration(pt);
		mods = new ArrayList<>();
		mods.add(mod);
	}
	
	public GiveModToHand(List<CardMod> mods, ContinuousAbility ability) {
		super("Give mod to hand");
		for (CardMod mod : mods) {
			mod.setAbility(ability);
		}
		this.mods = mods;
	}
	
	public GiveModToHand(List<CardMod> mods, PlayerPhaseTiming pt) {
		super("Give mod to hand");
		for (CardMod mod : mods) {
			mod.setExpiration(pt);
		}
		this.mods = mods;
	}

	@Override
	public String failureMessage() {
		return "Can't apply mods";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.addAll(p1.getBoard().getHand().getCards());
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		for (Card card : targets) {
			card.addMod(mods);
		}
	}

}
