package model.ability.continuous.mods;

public class mods {
	private boolean setLevel = false;
	private int levelAmount = 0;
	
	public void setSetLevel(boolean setLevel) {
		this.setLevel = setLevel;
	}

	public void setLevelAmount(int levelAmount) {
		this.levelAmount = levelAmount;
	}

	public int changeLevel(int level){
		return setLevel ? levelAmount : level + levelAmount;
	}
	
	
}