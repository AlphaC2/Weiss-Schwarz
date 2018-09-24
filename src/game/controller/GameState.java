package game.controller;

public class GameState {

	private PlayerController p1;
	private PlayerController p2;
	
	public GameState(PlayerController p1, PlayerController p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public GameState toRestricted(){
		return new GameState(p1,p2.toRestricted());
	}
}
