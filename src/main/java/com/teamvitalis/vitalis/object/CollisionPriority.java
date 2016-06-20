package com.teamvitalis.vitalis.object;

public enum CollisionPriority {

	LOWEST(1),
	LOW(2),
	NORMAL(3),
	HIGH(4),
	HIGHEST(5);
	
	private int level;
	
	private CollisionPriority(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
}
