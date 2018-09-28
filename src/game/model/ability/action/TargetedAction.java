package game.model.ability.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.controller.PlayerController;
import game.model.ability.action.condition.Condition;

public abstract class TargetedAction<T> extends Action<T>{
	protected List<T> targets;

	protected TargetedAction(String name) {
		super(name);
		targets = new ArrayList<>();
	}
	
	protected TargetedAction(String name, boolean required) {
		this(name);
		setRequired(required);
	}
	
	protected abstract void setTargets(PlayerController p1, PlayerController p2);
	
	public void setValidTargets(PlayerController p1, PlayerController p2){
		targets.clear();
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

	public String getTargetClassName(){
		if (targets.size() == 0){
			throw new RuntimeException();
		}
		return targets.get(0).getClass().toString();
	}

	@Override
	public boolean canActivate() {
			return targets.size() > 0;
	}

	@Override
	public final void execute(PlayerController p1, PlayerController p2) {
		if (gameOver(p1, p2)){
			return;
		}
		setValidTargets(p1, p2);
		executeChain(p1, p2);
		targets.clear();
	}
}
