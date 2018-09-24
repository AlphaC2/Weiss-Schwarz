package game.model.board;

public class Hand extends SearchableZone {
	
	public static final int MAX_HAND_SIZE = 7;

	Hand() {
		super("Hand",true);
	}

	@Override
	protected Hand newInstance() {
		return new Hand();
	}

}
