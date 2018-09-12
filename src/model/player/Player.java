package model.player;

import java.util.ArrayList;
import java.util.List;

import command.*;
import controller.GameManager;

public class Player {
	GameManager gm;
	PlayerPhase phase;
	String name;
	List<Command> commands = new ArrayList<>();

	public Player(String name) {
		this.name = name;
		phase = PlayerPhase.OPPONENTS_TURN;
	}

	public void setGM(GameManager gm){
		this.gm = gm;
	}
	
	public void endPhase() {
		commands.clear();
		switch (phase) {
		case STAND:
			phase = PlayerPhase.DRAW;
			commands.add(new Draw());
			break;
		case DRAW:
			phase = PlayerPhase.CLOCK;
			commands.add(new Clock());
			break;
		case CLOCK:
			phase = PlayerPhase.MAIN;
			commands.add(new DisplayHand());
			commands.add(new DisplayDamage());
			commands.add(new DisplayWaitingRoom());
			commands.add(new DisplayStage());
			commands.add(new PlayCharacter());
			commands.add(new SwapCharacters());
			commands.add(new EndPhase());
			break;
		case MAIN:
			commands.add(new PlayClimax());
			phase = PlayerPhase.CLIMAX;
			break;
		case CLIMAX:
			commands.add(new AttackPhase());
			phase = PlayerPhase.ATTACK;
			break;
		case ATTACK:
			phase = PlayerPhase.ENCORE;
			break;
		case ATTACK_DECLARATION:
			phase = PlayerPhase.ENCORE;
			commands.add(new Encore());
			break;
		case ENCORE:
			phase = PlayerPhase.END;
			break;

		case END:
			phase = PlayerPhase.OPPONENTS_TURN;
			gm.endTurn(this);
			break;
			
		case OPPONENTS_TURN:
			phase = PlayerPhase.STAND;
			commands.add(new StandPhase());
			break;
			
		default:
			break;
		}
		gm.log(this, System.lineSeparator());
	}

	public void nextStep() {
		switch (phase) {
		case ATTACK:
			phase = PlayerPhase.ATTACK_DECLARATION;
			break;
		case ATTACK_DECLARATION:
			phase = PlayerPhase.TRIGGER;
			break;
		case TRIGGER:
			phase = PlayerPhase.COUNTER;
			break;
		case COUNTER:
			phase = PlayerPhase.DAMAGE;
			break;
		case DAMAGE:
			phase = PlayerPhase.END_OF_ATTACK;
			break;
		case END_OF_ATTACK:
			phase = PlayerPhase.ATTACK_DECLARATION;
			break;
		default:
			break;
		}
	}

	public PlayerPhase getPhase() {
		return phase;
	}

	public String getName() {
		return name;
	}

	public void executeCommand() {
		if (commands.size() == 0){
			endPhase();
			return;
		}
		Command cmd = null;
		if (commands.size() == 1){
			cmd = commands.get(0);
			gm.execute(cmd,this);
			endPhase();
		}else{
			cmd = gm.getChoice(this,"Enter command", commands);
			gm.execute(cmd,this);
		}
	}

}
