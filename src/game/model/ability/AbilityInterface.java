package game.model.ability;

public interface AbilityInterface extends Activatable, Checkable {
	public boolean isRequired();
	public AbilityInterface next();
	public AbilityInterface last();
	public void setNextAction(AbilityInterface next);
	public String failureMessage();
}
