package game.model.ability.action.condition;

public abstract class Condition<T> {
	private String name;
	protected T target;
	
	protected Condition(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public abstract boolean check();

	public void setTarget(T target) {
		this.target = target;
	}
	
	public String getTargetClassName(){
		return target.getClass().toString();
	}

	@Override
	public String toString() {
		return "Condition [name=" + name + "]";
	}
	
	
}
