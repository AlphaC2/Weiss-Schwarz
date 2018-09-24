package game.model.ability.mods;

public class NumberMod extends CardMod<Integer>{
	
	private int amount;
	private boolean setFlag;
	
	public NumberMod(ModType type, int amount){
		this(type,amount,false);
	}
	
	public NumberMod(ModType type, int amount, boolean setFlag){
		super(type);
		this.amount = amount;
		this.setFlag = setFlag;
	}

	@Override
	public Integer apply(Integer base) {
		if (!isActive()){
			return base;
		}
		int value = setFlag ? amount : base + amount;
		return value < 0 ? 0 : value;
	}

}
