package model.ability.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import controller.PlayerController;
import model.ability.AbilityInterface;
import model.ability.condition.Condition;

public abstract class Action<T> implements AbilityInterface {
	private List<Condition<T>> conditions;
	protected List<T> targets;
	private String name;
	private AbilityInterface nextAction = null;
	private boolean isRequired = false;

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	@Override
	public void setNextAction(AbilityInterface nextAction) {
		this.nextAction = nextAction;
	}

	Action(String name) {
		this.name = name;
		targets = new ArrayList<>();
		conditions = new ArrayList<Condition<T>>();
	}
	
	Action(String name, boolean required) {
		this(name);
		isRequired = required;
	}
	
	
	protected abstract void setTargets(PlayerController p1, PlayerController p2);

	public void addCondition(Condition<T> c) {
		conditions.add(c);
	}

	public String getName() {
		return name;
	}
	
	@Override
	public AbilityInterface next(){
		return nextAction;
	}
	
	@Override
	public AbilityInterface last(){
		AbilityInterface last = this;
		while (last.next() != null){
			last = last.next();
		}
		return last;
	}
	
	public void setValidTargets(PlayerController p1, PlayerController p2){
		setTargets(p1,p2);
		Iterator<T> ite = targets.iterator();
		while (ite.hasNext()) {
			T target = ite.next();
			boolean targetIsValid = true;
			for (Condition<T> condition : conditions) {
				condition.setTarget(target);
				if (!condition.check()) {
					targetIsValid = false;
					break;
				}
			}
			if (!targetIsValid) {
				ite.remove();
			}

		}
	}

	@Override
	public boolean canActivate() {
			return targets.size() > 0;
	}

	@Override
	public final void execute(PlayerController p1, PlayerController p2) {
		setValidTargets(p1, p2);
		if (canActivate()) {
			executeAction(p1,p2);
			if (nextAction != null){
				nextAction.execute(p1, p2);
			}
		} else {
			p1.log(failureMessage());
		}
		targets.clear();
	}

	protected abstract void executeAction(PlayerController p1, PlayerController p2);

	public String getTargetClassName(){
		if (targets.size() == 0){
			throw new RuntimeException();
		}
		return targets.get(0).getClass().toString();
	}
	
	
}
