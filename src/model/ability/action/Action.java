package model.ability.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import controller.PlayerController;
import model.ability.condition.Condition;
import model.card.Activatable;

public abstract class Action<T> implements Activatable {
	private List<Condition<T>> conditions;
	protected List<T> targets;
	private String name;

	Action(String name) {
		this.name = name;
		targets = new ArrayList<>();
		conditions = new ArrayList<Condition<T>>();
	}

	protected abstract void setTargets(PlayerController p1, PlayerController p2);

	public void addCondition(Condition<T> c) {
		conditions.add(c);
	}

	public String getName() {
		return name;
	}

	private boolean canActivate() {
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
		return targets.size() > 0;
	}

	@Override
	public final void execute(PlayerController p1, PlayerController p2) {
		setTargets(p1, p2);
		if (canActivate()) {
			executeAction(p1,p2);
		} else {
			p1.log(failureMessage());
		}

	}

	protected abstract void executeAction(PlayerController p1, PlayerController p2);

	protected abstract String failureMessage();
}
