package model.ability.condition;

import model.board.Board;

public abstract class Condition {
	private String name;
	
	Condition(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public abstract boolean check(Board p1, Board p2);
}
