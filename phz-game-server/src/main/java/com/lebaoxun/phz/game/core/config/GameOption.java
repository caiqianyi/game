package com.lebaoxun.phz.game.core.config;

public enum GameOption {
	
	LFQ("六胡抢",101);
	
	private Integer option;
	private String name;
	
	private GameOption(String name,Integer option) {
		// TODO Auto-generated constructor stub
	}
	
	public Integer getOption() {
		return option;
	}
	public String getName() {
		return name;
	}
}
