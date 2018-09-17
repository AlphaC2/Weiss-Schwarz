package model.ability;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.card.Card;

public abstract class Ability implements Activatable, Checkable {
	
	private Card source;
	private int max = Integer.MAX_VALUE;
	private int used = 0;
	private AbilityInterface cost;

	List<AbilityInterface> actions = new ArrayList<>();
	
	protected Ability(Card source){
		this.source = source;
	}
	
	public void reset(){
		used = 0;
	}
	
	public int getMax() {
		return max;
	}
	
	public void setMax(int max) {
		this.max = max;
	}

	public int getUsed() {
		return used;
	}

	public Card getSource(){
		return source;
	}
	

	public void addCost(AbilityInterface newCost){
		if (cost == null){
			cost = newCost;
		} else {
			cost.last().setNextAction(newCost);
		}
	}
	
	public void addAction(AbilityInterface action){
		actions.add(action);
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2){
		if (canActivate()){
			if (cost != null){
				cost.execute(p1, p2);
			}
			for (AbilityInterface action: actions) {
				action.execute(p1, p2);
			}
			used++;
		}
	}
	
	private boolean checkChain(AbilityInterface chain){
		while(chain != null){
			if (! chain.canActivate() ) return false;
			chain = chain.next();
		}
		return true;
	}

	@Override
	public boolean canActivate() {
		return checkChain(cost) && used < max;
	}

}
