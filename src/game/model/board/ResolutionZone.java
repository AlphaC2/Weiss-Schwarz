package game.model.board;

public class ResolutionZone extends SearchableZone{

	public ResolutionZone() {
		super("Resolution Zone", true);
	}

	@Override
	protected ResolutionZone newInstance() {
		return new ResolutionZone();
	}

}
