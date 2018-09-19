package model.ability;

import model.card.Card;

public class ConditionalAbility extends Ability{
	
	private boolean self;

	protected ConditionalAbility(Card source) {
		super(source);
	}
	
	protected ConditionalAbility(Card source, boolean self) {
		super(source);
		this.self = self;
	}
	
	public boolean isSelf(){
		return self;
	}

}
