package game.controller;

import java.util.ArrayList;
import java.util.List;

import game.model.player.Player;

public class GameState {

	private PlayerController p1;
	private PlayerController p2;
	private Player sourcePlayer;
	private GameStatus status;
	private List<Object> choices;
	private Object choice;

	
	public GameState(PlayerController p1, PlayerController p2){
		this.p1 = p1;
		this.p2 = p2;
		status = GameStatus.INITIALISED;
	}
	
	public void pause(PlayerController playerController, String prompt) {
		List<Boolean> choices = new ArrayList<>();
		choices.add(new Boolean(true));
		choices.add(new Boolean(false));
		pause(playerController, prompt, choices);
	}
	
	public <T> void pause(PlayerController playerController, String prompt, List<T> choices) {
		status = GameStatus.INTERUPTED;
		sourcePlayer = playerController.getPlayer();
		this.choices = new ArrayList<>(choices);
	}
	
	public void resume(int index) {
		status = GameStatus.PLAYING;
		if (index < choices.size()){
			choice = choices.get(index);
		}
	}
	
	public void finish(PlayerController playerController){
		status = GameStatus.FINISHED;
		sourcePlayer = playerController.getPlayer();
	}

	public Player getSourcePlayer() {
		return sourcePlayer;
	}

	public PlayerController getP1() {
		return p1;
	}

	public PlayerController getP2() {
		return p2;
	}
	
	public GameStatus getStatus(){
		return status;
	}
	
	public List<Object> getChoices(){
		return choices;
	}

	public Object getChoice() {
		return choice;
	}
	
	@Override
	public String toString(){
		return status + sourcePlayer.getName();
	}


}
