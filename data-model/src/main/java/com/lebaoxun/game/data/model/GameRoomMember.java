package com.lebaoxun.game.data.model;

import java.io.Serializable;

public class GameRoomMember implements Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4873711446797037166L;
	private String nickname;
	private String icon;
	private int flag = 0;//1=庄家
	private boolean isCreate = false;
	private Integer score = 0;
	private Integer userId;
	private char[] cards;//每局的牌数
	private char[] alreadyCards;//已落地牌，key为牌型
	private int handleFlag = 0;//是否出牌摸牌状态 0=已出牌或者未轮到他出牌，1=出牌，2=摸牌。其他每个游戏自定义
	private Integer roundScore = 0;//当前局的分数，判断是否可以胡牌
	
	public GameRoomMember(Integer userId,Integer flag,boolean isCreate,String nickname,String icon) {
		// TODO Auto-generated constructor stub
		this.userId = userId;
		this.flag = flag;
		this.isCreate = isCreate;
		this.nickname = nickname;
		this.icon = icon;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public boolean isCreate() {
		return isCreate;
	}
	public void setCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoundScore() {
		return roundScore;
	}
	public void setRoundScore(Integer roundScore) {
		this.roundScore = roundScore;
	}

	public int getHandleFlag() {
		return handleFlag;
	}

	public void setHandleFlag(int handleFlag) {
		this.handleFlag = handleFlag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public char[] getAlreadyCards() {
		return alreadyCards;
	}

	public void setAlreadyCards(char[] alreadyCards) {
		this.alreadyCards = alreadyCards;
	}

	public char[] getCards() {
		return cards;
	}

	public void setCards(char[] cards) {
		this.cards = cards;
	}
	
	public Object clone(){
        try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
	
	/**
	 * 重置游戏数据
	 */
	public GameRoomMember reset(){
		this.isCreate = false;
		this.score = 0;
		this.cards = null;//每局的牌数
		this.alreadyCards = null;//已落地牌，key为牌型
		this.handleFlag = 0;//是否出牌摸牌状态 true=已出牌或者未轮到他出牌，false=轮到出牌，且未出牌
		this.roundScore = 0;//当前局的分数，判断是否可以胡牌
		return this;
	}
}
