package game.model.ability.mods;

public interface ICardMod<T> {
	public abstract T apply(T base);
}
