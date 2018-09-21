package model.player;

public class PlayerPhaseTiming implements Comparable<PlayerPhaseTiming>{
	
	private PlayerPhase phase;
	private PhaseTiming timing;
	private boolean self;
		
	public PlayerPhaseTiming(PlayerPhase phase, PhaseTiming timing){
		this(true, phase, timing);
	}
	
	public PlayerPhaseTiming(boolean self, PlayerPhase phase, PhaseTiming timing){
		this.self = self;
		this.phase = phase;
		this.timing = timing;
	}

	@Override
	public int compareTo(PlayerPhaseTiming other) {
		if(self && !other.self) {
			return -1;
		}
		
		if(!self && other.self){
			return 1;
		}
		
		
		if (phase.compareTo(other.phase) == 0){
			return timing.compareTo(other.timing);
		}		
		return phase.compareTo(other.phase);
	}

	public PlayerPhase getPhase() {
		return phase;
	}

	public PhaseTiming getTiming() {
		return timing;
	}

	@Override
	public String toString() {
		return (self ? "your " : "opponent's ") + phase + " " + timing;
	}
	
	

}
