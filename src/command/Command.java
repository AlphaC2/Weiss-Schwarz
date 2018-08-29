package command;

import controller.PlayerController;

public abstract class Command {
	
	private final String name;
	
	public Command(String name){
		this.name = name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public abstract void execute(PlayerController p1, PlayerController p2);
	

}
