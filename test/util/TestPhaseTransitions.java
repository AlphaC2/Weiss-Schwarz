package util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.player.PlayerPhase;

public class TestPhaseTransitions {
	private List<PlayerPhase> validPhases;
	private List<PlayerPhase> validSteps;
	private List<PlayerPhase> invalidPhases;
	private List<PlayerPhase> invalidSteps;
	
	@Before
	public void init(){
		validPhases = new ArrayList<>();
		validSteps = new ArrayList<>();
		invalidPhases = new ArrayList<>();
		invalidSteps = new ArrayList<>();
		
		validPhases.add(PlayerPhase.STAND);
		validPhases.add(PlayerPhase.DRAW);
		validPhases.add(PlayerPhase.CLOCK);
		validPhases.add(PlayerPhase.MAIN);
		validPhases.add(PlayerPhase.CLIMAX);
		validPhases.add(PlayerPhase.ATTACK);
		validPhases.add(PlayerPhase.ENCORE);
		validPhases.add(PlayerPhase.END);
		validPhases.add(PlayerPhase.OPPONENTS_TURN);
		
		validSteps.add(PlayerPhase.ATTACK);
		validSteps.add(PlayerPhase.ATTACK_DECLARATION);
		validSteps.add(PlayerPhase.TRIGGER);
		validSteps.add(PlayerPhase.COUNTER);
		validSteps.add(PlayerPhase.DAMAGE);
		validSteps.add(PlayerPhase.END_OF_ATTACK);
		
		invalidPhases.addAll(Arrays.asList(PlayerPhase.values()));
		invalidPhases.removeAll(validPhases);
		invalidSteps.addAll(Arrays.asList(PlayerPhase.values()));
		invalidSteps.removeAll(validSteps);
	}
	
	@Test
	public void checkPhases(){
		for (PlayerPhase phase : validPhases) {
			assertEquals(expectedNextPhase(phase), PlayerPhase.nextPhase(phase));
		}
	}
	
	@Test
	public void checkSteps(){
		for (PlayerPhase phase : validSteps) {
			assertEquals(expectedNextStep(phase), PlayerPhase.nextAttackStep(phase));
		}
	}
	
	@Test
	public void invalidPhaseTransitions(){
		for (PlayerPhase phase : invalidPhases) {
			assertThrows(IllegalArgumentException.class, () -> PlayerPhase.nextPhase(phase));			
		}
	}
	
	@Test
	public void invalidStepTransitions(){
		for (PlayerPhase phase : invalidSteps) {
			assertThrows(IllegalArgumentException.class, () -> PlayerPhase.nextAttackStep(phase));
		}
	}
	
	private PlayerPhase expectedNextPhase(PlayerPhase current){
		switch(current){
		case STAND: return PlayerPhase.DRAW;
		case DRAW: return PlayerPhase.CLOCK;
		case CLOCK: return PlayerPhase.MAIN;
		case MAIN: return PlayerPhase.CLIMAX;
		case CLIMAX: return PlayerPhase.ATTACK;
		case ATTACK: return PlayerPhase.ENCORE;
		case ATTACK_DECLARATION: throw new IllegalArgumentException(current.toString());
		case TRIGGER: throw new IllegalArgumentException(current.toString());
		case COUNTER: throw new IllegalArgumentException(current.toString());
		case DAMAGE: throw new IllegalArgumentException(current.toString());
		case END_OF_ATTACK: throw new IllegalArgumentException(current.toString());
		case ENCORE: return PlayerPhase.END;
		case END: return PlayerPhase.OPPONENTS_TURN;
		case OPPONENTS_TURN: return PlayerPhase.STAND;
		default: throw new IllegalArgumentException(current.toString());
		}
	}
	
	private PlayerPhase expectedNextStep(PlayerPhase current){
			switch(current){
			case STAND: throw new IllegalArgumentException(current.toString());
			case DRAW: throw new IllegalArgumentException(current.toString());
			case CLOCK: throw new IllegalArgumentException(current.toString());
			case MAIN: throw new IllegalArgumentException(current.toString());
			case CLIMAX: throw new IllegalArgumentException(current.toString());
			case ATTACK: return PlayerPhase.ATTACK_DECLARATION;
			case ATTACK_DECLARATION: return PlayerPhase.TRIGGER;
			case TRIGGER: return PlayerPhase.COUNTER;
			case COUNTER: return PlayerPhase.DAMAGE;
			case DAMAGE: return PlayerPhase.END_OF_ATTACK;
			case END_OF_ATTACK: return PlayerPhase.ATTACK_DECLARATION;
			case ENCORE: throw new IllegalArgumentException(current.toString());
			case END: throw new IllegalArgumentException(current.toString());
			case OPPONENTS_TURN: throw new IllegalArgumentException(current.toString());
			default: throw new IllegalArgumentException(current.toString());
			}
	}
}
