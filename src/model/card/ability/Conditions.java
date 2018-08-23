package model.card.ability;

import model.board.Board;
import model.board.PlayerPhase;

public class Conditions{
	private Board player;
	private Board opponent;
	
	public Conditions(Board player, Board opponent){
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
			return player.getMemory().size() > total;
		}
	}
	
	
	
}
