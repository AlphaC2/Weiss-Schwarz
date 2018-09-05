package command;

import controller.PlayerController;
import model.board.Board;

public class PayStock extends Command {
	private int amount;
	public PayStock(int amount) {
		super("Pay_Stock");
		this.amount = amount;
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2){
		Board board = p1.getBoard();
		if (board.getStock().size() < amount){
			p1.log("NOT ENOUGH STOCK");
		}
		for (int i = 0; i < amount; i++) {
			board.getWaitingRoom().add( board.getStock().pay() );
		}
	}

}
