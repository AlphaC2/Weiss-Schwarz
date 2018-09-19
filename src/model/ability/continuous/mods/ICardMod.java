package model.ability.continuous.mods;

public interface ICardMod<T> {
	public abstract T apply(T base);
}
