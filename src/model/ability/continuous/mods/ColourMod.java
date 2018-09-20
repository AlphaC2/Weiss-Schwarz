package model.ability.continuous.mods;

import model.card.Colour;

public class ColourMod extends CardMod<Colour>{
	
	private Colour colour;
	
	public ColourMod(Colour colour) {
		super(ModType.COLOUR);
		this.colour = colour;
	}

	@Override
	public final Colour apply(Colour base) {
		if (isActive()){
			return colour;
		}
		return base;
	}


}
