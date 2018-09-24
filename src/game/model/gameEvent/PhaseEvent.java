package game.model.gameEvent;

import game.model.player.PhaseTiming;
import game.model.player.Player;
import game.model.player.PlayerPhaseTiming;

public class PhaseEvent extends GameEvent {

	private PlayerPhaseTiming pt;
	
	public PhaseEvent(Player player, PhaseTiming timing) {
		super(player, EventType.PHASE);
		this.pt = new PlayerPhaseTiming(player.getPhase(), timing);
	}
	
	public PlayerPhaseTiming getPt() {
		return pt;
	}

	public String toString(){
		return getSourcePlayer().getName() + " " + pt.toString();
	}

}
