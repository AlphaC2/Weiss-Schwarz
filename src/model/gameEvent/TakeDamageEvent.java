package model.gameEvent;

import model.player.Player;

public class TakeDamageEvent extends GameEvent {
	
	private int amount;

	public TakeDamageEvent(Player player, int amount) {
		super(player, EventType.TOOK_DAMAGE);
		this.amount = amount;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public String toString(){
		return getSourcePlayer().getName() + " took " + amount + " damage";
	}

}
