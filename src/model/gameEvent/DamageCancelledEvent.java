package model.gameEvent;

import model.card.Climax;
import model.player.Player;

public class DamageCancelledEvent extends GameEvent {

	private Climax climax;
	
	public DamageCancelledEvent(Player player, Climax climax) {
		super(player, EventType.CANCEL_DAMAGE);
		this.climax = climax;
	}

	public Climax getClimax() {
		return climax;
	}
	
	@Override
	public String toString(){
		return getSourcePlayer().getName() + " cancelled damage with " + climax.toShortString(); 
	}
	
}
