package game.model.ability.action;

import java.util.ArrayList;
import java.util.List;

import game.controller.PlayerController;
import game.model.ability.AbilityInterface;
import game.model.ability.action.condition.Condition;

public abstract class Action<T> implements AbilityInterface {
	private boolean isRequired = true;
	private String name;
	protected List<Condition<T>> conditions;
	private AbilityInterface nextAction = null;
	
	public Action(String name, boolean isRequired) {
		super();
		this.isRequired = isRequired;
		this.name = name;
		conditions = new ArrayList<Condition<T>>();
	}
	
	public Action(String name) {
		this(name, true);
	}
	
	protected abstract void executeAction(PlayerController p1, PlayerController p2);
	
	protected boolean gameOver(PlayerController p1, PlayerController p2){
		return (p1 != null && !p1.isAlive()) || (p2 != null && !p2.isAlive());
	}
	
	protected void executeChain(PlayerController p1, PlayerController p2){
		if (canActivate()) {
			executeAction(p1,p2);
			if (next() != null){
				next().execute(p1, p2);
			}
		} else {
			p1.log(failureMessage());
		}	
	}
	
	public String getName() {
		return name;
	}

	public boolean isRequired() {
		return isRequired;
	}
	
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	public void addCondition(Condition<T> c) {
		conditions.add(c);
	}

	public void setNextAction(AbilityInterface nextAction) {
		this.nextAction = nextAction;
	}
	
	public AbilityInterface next(){
		return nextAction;
	}
	
	public AbilityInterface last(){
		AbilityInterface last = this;
		while (last.next() != null){
			last = last.next();
		}
		return last;
	}
}
