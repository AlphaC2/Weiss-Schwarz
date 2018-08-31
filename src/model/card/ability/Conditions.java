package model.card.ability;

import controller.PlayerController;
import model.player.PlayerPhase;

public class Conditions{
	private PlayerController player;
	private PlayerController opponent;
	
	public Conditions(PlayerController player, PlayerController opponent){
		this.player = player;
		this.opponent = opponent;
	}
	
	public class playerTurn extends Condition{
		@Override
		public boolean check() {
			return player.getPlayer().getPhase() != PlayerPhase.OPPONENTS_TURN;
		}
	}
	
	public class opponentTurn extends Condition{
		@Override
		public boolean check() {
			return player.getPlayer().getPhase() == PlayerPhase.OPPONENTS_TURN;
		}
	}
	
	public class RecollectionTotal extends Condition {
		int total;
		public RecollectionTotal(int n){
			total = n;
		}
		
		@Override
		public boolean check() {
			return player.getBoard().memorySize() > total;
		}
	}
	
	
	
}
