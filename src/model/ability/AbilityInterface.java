package model.ability;

public interface AbilityInterface extends Activatable, Checkable {
	public AbilityInterface next();
	public AbilityInterface last();
	public void setNextAction(AbilityInterface next);
	public String failureMessage();
}
