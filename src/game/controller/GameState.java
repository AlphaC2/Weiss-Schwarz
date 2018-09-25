package game.controller;

import java.util.ArrayList;
import java.util.List;

import game.model.player.Player;

public class GameState {
	private GameStatus status;
	private String prompt;
	private List<Object> choices;
	private Object choice;	
	private Player sourcePlayer;
	private PlayerController p1;
	private PlayerController p2;

	public GameState(PlayerController p1, PlayerController p2){
		this.p1 = p1;
		this.p2 = p2;
		status = GameStatus.INITIALISED;
	}
	
	public GameState toRestricted(){
		GameState newState = new GameState(p1.toRestricted(true),p2.toRestricted(false));
		newState.status = status;
		newState.choice = choice;
		newState.sourcePlayer = sourcePlayer;
		newState.choices = choices;
		newState.prompt = prompt; 
		return newState;
				
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
		this.prompt = prompt; 
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

	public GameStatus getStatus(){
		return status;
	}
	
	public String getPrompt() {
		return prompt;
	}
	
	public List<Object> getChoices(){
		return choices;
	}

	public Object getChoice() {
		return choice;
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
	
	@Override
	public String toString(){
		return status + sourcePlayer.getName();
	}
}
