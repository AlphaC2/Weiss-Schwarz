package model.ability;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.card.Card;

public abstract class Ability implements Activatable, Checkable {

	private Card source;
	private boolean self;
	protected List<AbilityInterface> actions = new ArrayList<>();

	protected Ability(Card source) {
		this.source = source;
	}
	
	protected Ability(Card source, boolean self) {
		this(source);
		this.self = self;
	}

	public final Card getSource() {
		return source;
	}
	
	public final boolean isSelf(){
		return self;
	}
	
	public abstract void setTargets(PlayerController p1, PlayerController p2);

	public final void addAction(AbilityInterface action) {
		actions.add(action);
	}

}
