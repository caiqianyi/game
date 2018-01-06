package com.lebaoxun.phz.game.core;

public class Item {
	
	private int card = 0;	// 是哪张牌
	private int i = 0;		// 第几种拆法
	private int j = 0;     // 正在进行第几种拆法
	private int huxi = 0;	// 胡息是多少
	
	public int getCard() {
		return card;
	}
	public void setCard(int card) {
		this.card = card;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	public int getHuxi() {
		return huxi;
	}
	public void setHuxi(int huxi) {
		this.huxi = huxi;
	}
	public void memset(int val){
		this.card = val;
		this.i = val;
		this.j = val;
		this.huxi = val;
	}
}
