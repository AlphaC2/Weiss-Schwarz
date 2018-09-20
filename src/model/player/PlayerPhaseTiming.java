package model.player;

public class PlayerPhaseTiming implements Comparable<PlayerPhaseTiming>{
	
	private PlayerPhase phase;
	private PhaseTiming timing;
	
	public PlayerPhaseTiming(PlayerPhase phase, PhaseTiming timing){
		this.phase = phase;
		this.timing = timing;
	}

	@Override
	public int compareTo(PlayerPhaseTiming other) {
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
		return phase + " " + timing;
	}
	
	

}
