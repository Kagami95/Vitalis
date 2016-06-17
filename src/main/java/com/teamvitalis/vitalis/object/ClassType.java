package com.teamvitalis.vitalis.object;

public enum ClassType {
	
	MANCER("Mancer"),
	MECHANIST("Mechanist");
	
	private String name;
	
	ClassType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
