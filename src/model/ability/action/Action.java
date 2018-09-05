package model.ability.action;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.ability.condition.Condition;
import model.card.Activatable;

public abstract class Action implements Activatable {
	private List<Condition> conditions;
	private String name;
	
	Action(String name){
		this.name = name;
		conditions = new ArrayList<Condition>();
	}
	
	public void addCondition(Condition c){
		conditions.add(c);
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public final void execute(PlayerController p1, PlayerController p2) {
		boolean canActivate = true;
		for (Condition condition : conditions) {
			if (!condition.check(p1.getBoard(), p2.getBoard())){
				canActivate = false;
				break;
			}
		}
		
		if(canActivate){
			executeAction(p1, p2);
		}else{
			p1.log(failureMessage());
		}
		
	}
	
	protected abstract void executeAction(PlayerController p1, PlayerController p2);
	protected abstract String failureMessage();
}
