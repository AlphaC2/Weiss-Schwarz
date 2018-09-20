package model.ability.mods;

public class NumberMod extends CardMod<Integer>{
	
	private int amount;
	private boolean setFlag;
	
	NumberMod(ModType type, int amount){
		this(type,amount,false);
	}
	
	NumberMod(ModType type, int amount, boolean setFlag){
		super(type);
		this.amount = amount;
		this.setFlag = setFlag;
	}

	@Override
	public Integer apply(Integer base) {
		if (!isActive()){
			return base;
		}
		return setFlag ? amount : base + amount;
	}

}
