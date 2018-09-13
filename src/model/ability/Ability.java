package model.ability;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.card.Card;

public abstract class Ability implements Activatable, Checkable {
	
	private Card source;
	private int max = Integer.MAX_VALUE;
	private int used = 0;
	
	protected Ability(Card source){
		this.source = source;
	}
	
	public int getMax() {
		return max;
	}

	public int getUsed() {
		return used;
	}

	public Card getSource(){
		return source;
	}
	
	List<AbilityInterface> actions = new ArrayList<>();
	
	public void addAction(AbilityInterface action){
		actions.add(action);
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2){
		for (AbilityInterface action: actions) {
			action.execute(p1, p2);
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
		for (AbilityInterface action : actions) {
			if (action.isRequired() && !checkChain(action)){
				return false;
			}
		}
		return true;
	}

}
