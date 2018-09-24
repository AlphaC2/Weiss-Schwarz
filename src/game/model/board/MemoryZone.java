package game.model.board;

public class MemoryZone extends SearchableZone{
	
	MemoryZone(){
		super("Memory",true);
	}

	@Override
	protected MemoryZone newInstance() {
		return new MemoryZone();
	}

}
