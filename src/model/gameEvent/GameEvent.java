package model.gameEvent;

import model.player.Player;

public abstract class GameEvent {

	private Player sourcePlayer;
	private EventType type;
	
	public GameEvent(Player player, EventType type){
		this.sourcePlayer = player;
		this.type = type;
	}
	
	public final Player getSourcePlayer(){
		return sourcePlayer;
	}
	
	public final EventType getType(){
		return type;
	}
	
}
