package model.card.ability;

import model.player.Player;
import model.player.PlayerPhase;

public class Conditions{
	private Player player;
	private Player opponent;
	
	public Conditions(Player player, Player opponent){
		this.player = player;
		this.opponent = opponent;
	}
	
	public class playerTurn extends Condition{
		@Override
		public boolean check() {
			return player.getPhase() != PlayerPhase.OPPONENTS_TURN;
		}
	}
	
	public class opponentTurn extends Condition{
		@Override
		public boolean check() {
			return player.getPhase() == PlayerPhase.OPPONENTS_TURN;
		}
	}
	
	public class RecollectionTotal extends Condition {
		int total;
		public RecollectionTotal(int n){
			total = n;
		}
		
		@Override
		public boolean check() {
			return player.memorySize() > total;
		}
	}
	
	
	
}
