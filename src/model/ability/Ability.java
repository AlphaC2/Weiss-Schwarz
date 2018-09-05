package model.ability;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.ability.action.Action;
import model.ability.condition.Condition;
import model.card.Activatable;

public abstract class Ability implements Activatable {
	private List<Condition> conditions;
	private List<Action> actions;
	private String name;
	
	Ability(String name){
		this.name = name;
		conditions = new ArrayList<Condition>();
		actions = new ArrayList<Action>();
	}
	
	public void addCondition(Condition c){
		conditions.add(c);
	}
	
	public void addAction(Action a){
		actions.add(a);
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		boolean canActivate = true;
		for (Condition condition : conditions) {
			canActivate = canActivate  && condition.check(p1.getBoard(), p2.getBoard());
		}
		
		if(canActivate){
			for (Action action : actions) {
				action.execute(p1, p2);
			}
		}
		
	}

}
