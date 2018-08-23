package model.card.ability;

import java.util.List;

import model.card.Card;

public abstract class Ability implements Activatable{
	private List<Condition> condition;
	private List<Card> target;
	//private List<Action> actions;
	
	private int cost;
	
	
}
