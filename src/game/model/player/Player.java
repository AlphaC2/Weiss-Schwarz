package game.model.player;

import java.util.ArrayList;
import java.util.List;

import game.command.AttackPhase;
import game.command.Clock;
import game.command.Command;
import game.command.DisplayDamage;
import game.command.DisplayHand;
import game.command.DisplayLevel;
import game.command.DisplayStage;
import game.command.DisplayWaitingRoom;
import game.command.Draw;
import game.command.Encore;
import game.command.EndPhase;
import game.command.PlayCharacter;
import game.command.PlayClimax;
import game.command.StandPhase;
import game.command.SwapCharacters;
import game.controller.GameManager;
import game.model.ability.action.CheckTiming;

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
		CheckTiming check = new CheckTiming(PhaseTiming.END);
		gm.execute(check, this);
		switch (phase) {
		case STAND:
			commands.add(new Draw());
			break;
		case DRAW:
			commands.add(new Clock());
			break;
		case CLOCK:
			commands.add(new DisplayHand());
			commands.add(new DisplayDamage());
			commands.add(new DisplayWaitingRoom());
			commands.add(new DisplayStage());
			commands.add(new DisplayLevel());
			commands.add(new PlayCharacter());
			commands.add(new SwapCharacters());
			commands.add(new EndPhase());
			break;
		case MAIN:
			commands.add(new PlayClimax());
			break;
		case CLIMAX:
			commands.add(new AttackPhase());
			break;
		case ATTACK:
			break;
		case ATTACK_DECLARATION:
			commands.add(new Encore());
			break;
		case ENCORE:
			break;

		case END:
			gm.endTurn(this);
			break;
			
		case OPPONENTS_TURN:
			commands.add(new StandPhase());
			break;
		default:
			break;
		}
		phase = PlayerPhase.nextPhase(phase);
		gm.log(this, System.lineSeparator());
		check = new CheckTiming(PhaseTiming.START);
		gm.execute(check, this);
	}

	public void nextStep() {
		phase = PlayerPhase.nextAttackStep(phase);
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
