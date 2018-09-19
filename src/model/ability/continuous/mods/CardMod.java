package model.ability.continuous.mods;

public abstract class CardMod<T> implements ICardMod<T>{
	private ModType type;
	
	CardMod(ModType type){
		this.type = type;
	}
	
	public ModType getType(){
		return type;
	}
}
