package command;

import controller.PlayerController;
import model.board.Board;
import model.exceptions.NotEnoughStockException;

public class PayStock extends Command {
	private int amount;
	public PayStock(int amount) {
		super("Pay_Stock");
		this.amount = amount;
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) throws NotEnoughStockException{
		Board board = p1.getBoard();
		if (board.getStock().size() < amount){
			throw new NotEnoughStockException();
		}
		for (int i = 0; i < amount; i++) {
			board.getWaitingRoom().add( board.getStock().pay() );
		}
	}

}
