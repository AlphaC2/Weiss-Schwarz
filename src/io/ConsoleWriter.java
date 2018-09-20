package io;

import model.board.Slot;
import model.board.Stage;

public class ConsoleWriter extends Writer {
	
	@Override
	public void log(Object text) {
		System.out.println(pc.getPlayer().getName() + ": " + text.toString());
	}
	
	@Override
	public void displayStage() {
		System.out.println(pc.getPlayer().getName() + " stage:");
		Stage stage = pc.getBoard().getStage();
		for (Slot slot : stage.getSlots()) {
			System.out.println(slot);
		}
	}

	@Override
	public void displayHand() {
		System.out.println(pc.getBoard().getHand());
	}

	@Override
	public void displayWaitingRoom() {
		System.out.println(pc.getBoard().getWaitingRoom());
	}

	@Override
	public void displayDamageZone() {
		System.out.println(pc.getBoard().getDamageZone());
	}
	

	@Override
	public void displayLevel() {
		System.out.println(pc.getBoard().getLevel());
	}

	@Override
	public void displayStock() {
		System.out.println(pc.getBoard().getStock());
	}
}
