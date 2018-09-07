package model.ability;

public interface AbilityInterface extends Activatable, Checkable {
	public boolean isRequired();
	public AbilityInterface next();
	public String failureMessage();
}
