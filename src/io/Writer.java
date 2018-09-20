package io;

import controller.PlayerController;

public abstract class Writer {
	protected PlayerController pc;

	public final void setPC(PlayerController pc){
		this.pc = pc;
	}
	
	public abstract void displayStage();
	public abstract void displayHand();
	public abstract void displayWaitingRoom();
	public abstract void displayDamageZone();
	public abstract void displayLevel();
	public abstract void displayStock();
	public abstract void log(Object text);

}
